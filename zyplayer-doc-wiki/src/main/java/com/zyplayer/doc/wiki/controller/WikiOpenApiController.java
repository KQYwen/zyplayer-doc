package com.zyplayer.doc.wiki.controller;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.zyplayer.doc.core.json.DocResponseJson;
import com.zyplayer.doc.core.json.ResponseJson;
import com.zyplayer.doc.data.config.security.DocUserDetails;
import com.zyplayer.doc.data.config.security.DocUserUtil;
import com.zyplayer.doc.data.repository.manage.entity.WikiPage;
import com.zyplayer.doc.data.repository.manage.entity.WikiPageContent;
import com.zyplayer.doc.data.repository.manage.entity.WikiPageFile;
import com.zyplayer.doc.data.repository.manage.entity.WikiSpace;
import com.zyplayer.doc.data.service.manage.WikiPageContentService;
import com.zyplayer.doc.data.service.manage.WikiPageFileService;
import com.zyplayer.doc.data.service.manage.WikiPageService;
import com.zyplayer.doc.data.service.manage.WikiSpaceService;
import com.zyplayer.doc.wiki.controller.vo.WikiPageContentVo;
import com.zyplayer.doc.wiki.controller.vo.WikiPageVo;
import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Objects;
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
	Mapper mapper;
	
	@PostMapping("/space/info")
	public ResponseJson<WikiSpace> spaceInfo(String space) {
		UpdateWrapper<WikiSpace> wrapper = new UpdateWrapper<>();
		wrapper.eq("uuid", space);
		wrapper.eq("del_flag", 0);
		WikiSpace wikiSpace = wikiSpaceService.getOne(wrapper);
		// 不存在或未开放
		if (wikiSpace == null || wikiSpace.getOpenDoc() != 1) {
			return DocResponseJson.warn("未找到该文档");
		}
		return DocResponseJson.ok(wikiSpace);
	}
	
	@PostMapping("/page/list")
	public ResponseJson<List<WikiPageVo>> list(String space) {
		UpdateWrapper<WikiSpace> wrapperSpace = new UpdateWrapper<>();
		wrapperSpace.eq("uuid", space);
		WikiSpace wikiSpace = wikiSpaceService.getOne(wrapperSpace);
		// 不存在或未开放
		if (wikiSpace == null || wikiSpace.getOpenDoc() != 1) {
			return DocResponseJson.warn("未找到该文档");
		}
		UpdateWrapper<WikiPage> wrapper = new UpdateWrapper<>();
		wrapper.eq("del_flag", 0);
		wrapper.eq("space_id", wikiSpace.getId());
		List<WikiPage> authList = wikiPageService.list(wrapper);
		Map<Long, List<WikiPageVo>> listMap = authList.stream().map(val -> mapper.map(val, WikiPageVo.class)).collect(Collectors.groupingBy(WikiPageVo::getParentId));
		List<WikiPageVo> nodePageList = listMap.get(0L);
		this.setChildren(listMap, nodePageList);
		return DocResponseJson.ok(nodePageList);
	}
	
	@PostMapping("/page/detail")
	public ResponseJson<WikiPageContentVo> detail(String space, Long pageId) {
		UpdateWrapper<WikiSpace> wrapperSpace = new UpdateWrapper<>();
		wrapperSpace.eq("uuid", space);
		WikiSpace wikiSpace = wikiSpaceService.getOne(wrapperSpace);
		// 不存在或未开放
		if (wikiSpace == null || wikiSpace.getOpenDoc() != 1) {
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
			if (wikiPageVos != null && wikiPageVos.size() > 0) {
				page.setChildren(wikiPageVos);
				this.setChildren(listMap, wikiPageVos);
			}
		}
	}
}

