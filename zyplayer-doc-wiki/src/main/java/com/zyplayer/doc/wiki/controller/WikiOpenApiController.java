package com.zyplayer.doc.wiki.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zyplayer.doc.core.json.DocResponseJson;
import com.zyplayer.doc.core.json.ResponseJson;
import com.zyplayer.doc.data.repository.manage.entity.WikiPage;
import com.zyplayer.doc.data.repository.manage.entity.WikiPageContent;
import com.zyplayer.doc.data.repository.manage.entity.WikiPageFile;
import com.zyplayer.doc.data.repository.manage.entity.WikiSpace;
import com.zyplayer.doc.data.repository.manage.mapper.WikiPageContentMapper;
import com.zyplayer.doc.data.repository.manage.param.SearchByEsParam;
import com.zyplayer.doc.data.repository.manage.vo.SpaceNewsVo;
import com.zyplayer.doc.data.service.manage.WikiPageContentService;
import com.zyplayer.doc.data.service.manage.WikiPageFileService;
import com.zyplayer.doc.data.service.manage.WikiPageService;
import com.zyplayer.doc.data.service.manage.WikiSpaceService;
import com.zyplayer.doc.wiki.controller.vo.WikiPageContentVo;
import com.zyplayer.doc.wiki.controller.vo.WikiPageVo;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 文档控制器
 *
 * @author 暮光：城中城
 * @since 2019年2月17日
 */
@RestController
@RequestMapping("/zyplayer-doc-wiki/open-api")
public class WikiOpenApiController {
	private static Logger logger = LoggerFactory.getLogger(WikiOpenApiController.class);
	
	@Resource
	WikiPageService wikiPageService;
	@Resource
	WikiSpaceService wikiSpaceService;
	@Resource
	WikiPageContentService wikiPageContentService;
	@Resource
	WikiPageFileService wikiPageFileService;
	@Resource
	WikiPageContentMapper wikiPageContentMapper;
	@Resource
	Mapper mapper;
	
	@PostMapping("/space/info")
	public ResponseJson<WikiSpace> spaceInfo(String space) {
		WikiSpace wikiSpace = this.getWikiSpace(space);
		if (wikiSpace == null) {
			return DocResponseJson.warn("未找到该文档");
		}
		return DocResponseJson.ok(wikiSpace);
	}
	
	@PostMapping("/page/news")
	public ResponseJson<List<WikiPageVo>> news(SearchByEsParam param, String space) {
		WikiSpace wikiSpace = this.getWikiSpace(space);
		if (wikiSpace == null) {
			return DocResponseJson.warn("未找到该文档");
		}
		String keywords = param.getKeywords();
		if (StringUtils.isNotBlank(keywords)) {
			param.setKeywords("%" + keywords + "%");
		}
		// 分页查询
		param.setSpaceIds(Collections.singletonList(wikiSpace.getId()));
		PageHelper.startPage(param.getPageNum(), param.getPageSize(), true);
		List<SpaceNewsVo> spaceNewsVoList = wikiPageContentMapper.getNewsList(param);
		PageInfo<SpaceNewsVo> pageListPageInfo = new PageInfo<>(spaceNewsVoList);
		if (CollectionUtils.isNotEmpty(spaceNewsVoList)) {
			spaceNewsVoList.forEach(val -> {
				val.setSpace(wikiSpace.getUuid());
				val.setSpaceName(wikiSpace.getName());
				String preview = val.getPreviewContent();
				if (preview != null) {
					if (preview.length() > 200) {
						preview = preview.substring(0, 200);
					}
					if (keywords != null) {
						preview = preview.replace(keywords, "<span style=\"color:red\">" + keywords + "</span>");
					}
				}
				val.setPreviewContent(preview);
				String pageTitle = val.getPageTitle();
				if (pageTitle != null && keywords != null) {
					pageTitle = pageTitle.replace(keywords, "<span style=\"color:red\">" + keywords + "</span>");
				}
				val.setPageTitle(pageTitle);
			});
		}
		return DocResponseJson.ok(pageListPageInfo);
	}
	
	@PostMapping("/page/list")
	public ResponseJson<List<WikiPageVo>> list(String space) {
		WikiSpace wikiSpace = this.getWikiSpace(space);
		if (wikiSpace == null) {
			return DocResponseJson.warn("未找到该文档");
		}
		QueryWrapper<WikiPage> wrapper = new QueryWrapper<>();
		wrapper.eq("del_flag", 0);
		wrapper.eq("space_id", wikiSpace.getId());
		List<WikiPage> wikiPageList = wikiPageService.list(wrapper);
		if (CollectionUtils.isEmpty(wikiPageList)) {
			return DocResponseJson.ok();
		}
		Map<Long, List<WikiPageVo>> listMap = wikiPageList.stream().map(val -> mapper.map(val, WikiPageVo.class)).collect(Collectors.groupingBy(WikiPageVo::getParentId));
		List<WikiPageVo> nodePageList = listMap.get(0L);
		if (CollectionUtils.isNotEmpty(nodePageList)) {
			nodePageList = nodePageList.stream().sorted(Comparator.comparingInt(WikiPage::getSeqNo)).collect(Collectors.toList());
			this.setChildren(listMap, nodePageList);
		}
		return DocResponseJson.ok(nodePageList);
	}
	
	@PostMapping("/page/detail")
	public ResponseJson<WikiPageContentVo> detail(String space, Long pageId) {
		WikiSpace wikiSpace = this.getWikiSpace(space);
		if (wikiSpace == null) {
			return DocResponseJson.warn("未找到该文档");
		}
		WikiPage wikiPageSel = wikiPageService.getById(pageId);
		// 不存在或不属于该空间
		if (wikiPageSel == null || !Objects.equals(wikiPageSel.getSpaceId(), wikiSpace.getId())) {
			return DocResponseJson.warn("未找到该文档");
		}
		UpdateWrapper<WikiPageContent> wrapper = new UpdateWrapper<>();
		wrapper.eq("page_id", pageId);
		WikiPageContent pageContent = wikiPageContentService.getOne(wrapper);
		UpdateWrapper<WikiPageFile> wrapperFile = new UpdateWrapper<>();
		wrapperFile.eq("page_id", pageId);
		wrapperFile.eq("del_flag", 0);
		List<WikiPageFile> pageFiles = wikiPageFileService.list(wrapperFile);
		for (WikiPageFile pageFile : pageFiles) {
			pageFile.setFileUrl("zyplayer-doc-wiki/common/file?uuid=" + pageFile.getUuid());
		}
		// 高并发下会有覆盖问题，但不重要~
		Integer viewNum =  Optional.ofNullable(wikiPageSel.getViewNum()).orElse(0);
		WikiPage wikiPageUp = new WikiPage();
		wikiPageUp.setId(wikiPageSel.getId());
		wikiPageUp.setViewNum(viewNum + 1);
		wikiPageService.updateById(wikiPageUp);
		// 修改返回值里的查看数+1
		wikiPageSel.setViewNum(viewNum + 1);
		WikiPageContentVo vo = new WikiPageContentVo();
		vo.setWikiPage(wikiPageSel);
		vo.setPageContent(pageContent);
		vo.setFileList(pageFiles);
		return DocResponseJson.ok(vo);
	}
	
	private void setChildren(Map<Long, List<WikiPageVo>> listMap, List<WikiPageVo> nodePageList) {
		if (nodePageList == null || listMap == null) {
			return;
		}
		for (WikiPageVo page : nodePageList) {
			List<WikiPageVo> wikiPageVos = listMap.get(page.getId());
			if (CollectionUtils.isNotEmpty(wikiPageVos)) {
				wikiPageVos = wikiPageVos.stream().sorted(Comparator.comparingInt(WikiPage::getSeqNo)).collect(Collectors.toList());
				page.setChildren(wikiPageVos);
				this.setChildren(listMap, wikiPageVos);
			}
		}
	}
	
	/**
	 * 获取空间信息
	 * @param space
	 * @return
	 */
	private WikiSpace getWikiSpace(String space) {
		QueryWrapper<WikiSpace> wrapperSpace = new QueryWrapper<>();
		wrapperSpace.eq("uuid", space);
		wrapperSpace.eq("del_flag", 0);
		WikiSpace wikiSpace = wikiSpaceService.getOne(wrapperSpace);
		// 不存在或未开放
		if (wikiSpace == null || wikiSpace.getOpenDoc() != 1) {
			return null;
		}
		return wikiSpace;
	}
}

