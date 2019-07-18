package com.zyplayer.doc.wiki.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zyplayer.doc.core.annotation.AuthMan;
import com.zyplayer.doc.core.json.DocResponseJson;
import com.zyplayer.doc.core.json.ResponseJson;
import com.zyplayer.doc.data.config.security.DocUserDetails;
import com.zyplayer.doc.data.config.security.DocUserUtil;
import com.zyplayer.doc.data.repository.manage.entity.*;
import com.zyplayer.doc.data.repository.manage.mapper.WikiPageContentMapper;
import com.zyplayer.doc.data.repository.manage.mapper.WikiPageMapper;
import com.zyplayer.doc.data.repository.manage.param.SearchByEsParam;
import com.zyplayer.doc.data.repository.manage.vo.SpaceNewsVo;
import com.zyplayer.doc.data.service.elasticsearch.entity.EsWikiPage;
import com.zyplayer.doc.data.service.elasticsearch.service.EsWikiPageService;
import com.zyplayer.doc.data.service.elasticsearch.support.EsPage;
import com.zyplayer.doc.data.service.manage.*;
import com.zyplayer.doc.data.utils.CacheUtil;
import com.zyplayer.doc.wiki.controller.vo.WikiPageContentVo;
import com.zyplayer.doc.wiki.controller.vo.WikiPageVo;
import com.zyplayer.doc.wiki.framework.consts.SpaceType;
import com.zyplayer.doc.wiki.framework.consts.WikiAuthType;
import com.zyplayer.doc.wiki.framework.consts.WikiCachePrefix;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.dozer.Mapper;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
@AuthMan
@RestController
@RequestMapping("/zyplayer-doc-wiki/page")
public class WikiPageController {
	private static Logger logger = LoggerFactory.getLogger(WikiPageController.class);
	
	@Resource
	WikiPageService wikiPageService;
	@Resource
	WikiPageContentService wikiPageContentService;
	@Resource
	WikiPageContentMapper wikiPageContentMapper;
	@Resource
	WikiPageFileService wikiPageFileService;
	@Resource
	WikiPageZanService wikiPageZanService;
	@Resource
	WikiSpaceService wikiSpaceService;
	@Resource
	WikiPageMapper wikiPageMapper;
	@Resource
	Mapper mapper;
	@Autowired(required = false)
	EsWikiPageService esWikiPageService;
	
	@PostMapping("/list")
	public ResponseJson<List<WikiPageVo>> list(WikiPage wikiPage) {
		DocUserDetails currentUser = DocUserUtil.getCurrentUser();
		WikiSpace wikiSpaceSel = wikiSpaceService.getById(wikiPage.getSpaceId());
		// 私人空间
		if (SpaceType.isOthersPrivate(wikiSpaceSel.getType(), currentUser.getUserId(), wikiSpaceSel.getCreateUserId())) {
			return DocResponseJson.warn("您没有权限查看该空间的文章列表！");
		}
		UpdateWrapper<WikiPage> wrapper = new UpdateWrapper<>();
		wrapper.eq("del_flag", 0);
		wrapper.eq("space_id", wikiPage.getSpaceId());
		wrapper.eq(wikiPage.getParentId() != null, "parent_id", wikiPage.getParentId());
		List<WikiPage> wikiPageList = wikiPageService.list(wrapper);
		Map<Long, List<WikiPageVo>> listMap = wikiPageList.stream().map(val -> mapper.map(val, WikiPageVo.class)).collect(Collectors.groupingBy(WikiPageVo::getParentId));
		List<WikiPageVo> nodePageList;
		if (wikiPage.getParentId() == null) {
			nodePageList = listMap.get(0L);
			if (CollectionUtils.isNotEmpty(nodePageList)) {
				nodePageList = nodePageList.stream().sorted(Comparator.comparingInt(WikiPage::getSeqNo)).collect(Collectors.toList());
				this.setChildren(listMap, nodePageList, "");
			}
		} else {
			nodePageList = listMap.get(wikiPage.getParentId());
			if (CollectionUtils.isNotEmpty(nodePageList)) {
				nodePageList = nodePageList.stream().sorted(Comparator.comparingInt(WikiPage::getSeqNo)).collect(Collectors.toList());
			}
		}
		return DocResponseJson.ok(nodePageList);
	}
	
	@PostMapping("/detail")
	public ResponseJson<WikiPageContentVo> detail(WikiPage wikiPage) {
		DocUserDetails currentUser = DocUserUtil.getCurrentUser();
		WikiPage wikiPageSel = wikiPageService.getById(wikiPage.getId());
		WikiSpace wikiSpaceSel = wikiSpaceService.getById(wikiPageSel.getSpaceId());
		// 私人空间
		if (SpaceType.isOthersPrivate(wikiSpaceSel.getType(), currentUser.getUserId(), wikiSpaceSel.getCreateUserId())) {
			return DocResponseJson.warn("您没有权限查看该空间的文章详情！");
		}
		UpdateWrapper<WikiPageContent> wrapper = new UpdateWrapper<>();
		wrapper.eq("page_id", wikiPage.getId());
		WikiPageContent pageContent = wikiPageContentService.getOne(wrapper);
		
		UpdateWrapper<WikiPageFile> wrapperFile = new UpdateWrapper<>();
		wrapperFile.eq("page_id", wikiPage.getId());
		wrapperFile.eq("del_flag", 0);
		List<WikiPageFile> pageFiles = wikiPageFileService.list(wrapperFile);
		for (WikiPageFile pageFile : pageFiles) {
			pageFile.setFileUrl("zyplayer-doc-wiki/common/file?uuid=" + pageFile.getUuid());
		}
		UpdateWrapper<WikiPageZan> wrapperZan = new UpdateWrapper<>();
		wrapperZan.eq("page_id", wikiPage.getId());
		wrapperZan.eq("create_user_id", currentUser.getUserId());
		wrapperZan.eq("yn", 1);
		WikiPageZan pageZan = wikiPageZanService.getOne(wrapperZan);
		WikiPageContentVo vo = new WikiPageContentVo();
		vo.setWikiPage(wikiPageSel);
		vo.setPageContent(pageContent);
		vo.setFileList(pageFiles);
		vo.setSelfZan((pageZan != null) ? 1 : 0);
		// 高并发下会有覆盖问题，但不重要~
		Integer viewNum =  Optional.ofNullable(wikiPageSel.getViewNum()).orElse(0);
		WikiPage wikiPageUp = new WikiPage();
		wikiPageUp.setId(wikiPageSel.getId());
		wikiPageUp.setViewNum(viewNum + 1);
		wikiPageService.updateById(wikiPageUp);
		// 修改返回值里的查看数+1
		wikiPageSel.setViewNum(viewNum + 1);
		return DocResponseJson.ok(vo);
	}
	
	@PostMapping("/changeParent")
	public ResponseJson<Object> changeParent(WikiPage wikiPage, Integer beforeSeq, Integer afterSeq) {
		DocUserDetails currentUser = DocUserUtil.getCurrentUser();
		Long id = wikiPage.getId();
		WikiPage wikiPageSel = wikiPageService.getById(id);
		if (wikiPageSel == null || Objects.equals(wikiPageSel.getEditType(), 1)) {
			return DocResponseJson.warn("当前页面不允许编辑！");
		}
		WikiSpace wikiSpaceSel = wikiSpaceService.getById(wikiPageSel.getSpaceId());
		// 私人空间不允许调用接口获取文章
		if (SpaceType.isOthersPrivate(wikiSpaceSel.getType(), currentUser.getUserId(), wikiSpaceSel.getCreateUserId())) {
			return DocResponseJson.warn("您没有权限修改该空间的文章！");
		}
		// 空间不是自己的，也没有权限
		if (SpaceType.isOthersPersonal(wikiSpaceSel.getType(), currentUser.getUserId(), wikiSpaceSel.getCreateUserId())) {
			boolean pageAuth = DocUserUtil.havePageAuth(WikiAuthType.EDIT_PAGE.getName(), id);
			if (!pageAuth) {
				return DocResponseJson.warn("您没有修改该文章的权限！");
			}
		}
		WikiPage wikiPageUp = new WikiPage();
		wikiPageUp.setId(wikiPage.getId());
		wikiPageUp.setParentId(wikiPage.getParentId());
		wikiPageUp.setUpdateTime(new Date());
		wikiPageUp.setUpdateUserId(currentUser.getUserId());
		wikiPageUp.setUpdateUserName(currentUser.getUsername());
		wikiPageService.changeParent(wikiPage, beforeSeq, afterSeq);
		return DocResponseJson.ok();
	}
	
	@PostMapping("/delete")
	public ResponseJson<Object> delete(Long pageId) {
		DocUserDetails currentUser = DocUserUtil.getCurrentUser();
		WikiPage wikiPageSel = wikiPageService.getById(pageId);
		if (wikiPageSel == null || Objects.equals(wikiPageSel.getEditType(), 1)) {
			return DocResponseJson.warn("当前页面不允许编辑！");
		}
		WikiSpace wikiSpaceSel = wikiSpaceService.getById(wikiPageSel.getSpaceId());
		// 私人空间不允许调用接口获取文章
		if (SpaceType.isOthersPrivate(wikiSpaceSel.getType(), currentUser.getUserId(), wikiSpaceSel.getCreateUserId())) {
			return DocResponseJson.warn("您没有权限修改该空间的文章！");
		}
		// 空间不是自己的，也没有权限
		if (SpaceType.isOthersPersonal(wikiSpaceSel.getType(), currentUser.getUserId(), wikiSpaceSel.getCreateUserId())) {
			boolean pageAuth = DocUserUtil.havePageAuth(WikiAuthType.DELETE_PAGE.getName(), pageId);
			if (!pageAuth) {
				return DocResponseJson.warn("您没有删除该文章的权限！");
			}
		}
		// 执行删除
		WikiPage wikiPage = new WikiPage();
		wikiPage.setId(pageId);
		wikiPage.setDelFlag(1);
		wikiPage.setUpdateTime(new Date());
		wikiPage.setUpdateUserId(currentUser.getUserId());
		wikiPage.setUpdateUserName(currentUser.getUsername());
		wikiPageService.deletePage(wikiPage);
		return DocResponseJson.ok();
	}
	
	@PostMapping("/update")
	public ResponseJson<Object> update(WikiPage wikiPage, String content, String preview) {
		DocUserDetails currentUser = DocUserUtil.getCurrentUser();
		WikiPageContent pageContent = new WikiPageContent();
		pageContent.setContent(content);
		pageContent.setPreview(preview);
		// 数据库是varchar(16000)，所以如果不开启es的话搜索超过16000的文章就搜不到~，es存preview不截断
		if (StringUtils.isNotBlank(preview) && preview.length() > 16000) {
			pageContent.setPreview(preview.substring(0, 16000));
		}
		if (StringUtils.isBlank(wikiPage.getName())) {
			return DocResponseJson.warn("标题不能为空！");
		}
		Long pageId = wikiPage.getId();
		if (pageId != null && pageId > 0) {
			WikiPage wikiPageSel = wikiPageService.getById(pageId);
			if (wikiPageSel == null || Objects.equals(wikiPageSel.getEditType(), 1)) {
				return DocResponseJson.warn("当前页面不允许编辑！");
			}
			WikiSpace wikiSpaceSel = wikiSpaceService.getById(wikiPageSel.getSpaceId());
			// 私人空间不允许调用接口获取文章
			if (SpaceType.isOthersPrivate(wikiSpaceSel.getType(), currentUser.getUserId(), wikiSpaceSel.getCreateUserId())) {
				return DocResponseJson.warn("您没有权限修改该空间的文章！");
			}
			// 空间不是自己的，也没有权限
			if (SpaceType.isOthersPersonal(wikiSpaceSel.getType(), currentUser.getUserId(), wikiSpaceSel.getCreateUserId())) {
				boolean pageAuth = DocUserUtil.havePageAuth(WikiAuthType.EDIT_PAGE.getName(), pageId);
				if (!pageAuth) {
					return DocResponseJson.warn("您没有修改该文章的权限！");
				}
			}
			wikiPage.setSpaceId(null);
			wikiPage.setEditType(null);
			wikiPage.setUpdateTime(new Date());
			wikiPage.setUpdateUserId(currentUser.getUserId());
			wikiPage.setUpdateUserName(currentUser.getUsername());
			wikiPageService.updateById(wikiPage);
			// 详情
			pageContent.setUpdateTime(new Date());
			pageContent.setUpdateUserId(currentUser.getUserId());
			pageContent.setUpdateUserName(currentUser.getUsername());
			UpdateWrapper<WikiPageContent> wrapper = new UpdateWrapper<>();
			wrapper.eq("page_id", pageId);
			wikiPageContentService.update(pageContent, wrapper);
		} else {
			WikiSpace wikiSpaceSel = wikiSpaceService.getById(wikiPage.getSpaceId());
			// 空间不是自己的
			if (SpaceType.isOthersPrivate(wikiSpaceSel.getType(), currentUser.getUserId(), wikiSpaceSel.getCreateUserId())) {
				return DocResponseJson.warn("您没有权限新增该空间的文章！");
			}
			// 空间不是自己的
			if (SpaceType.isOthersPersonal(wikiSpaceSel.getType(), currentUser.getUserId(), wikiSpaceSel.getCreateUserId())) {
				return DocResponseJson.warn("您没有权限新增该空间的文章！");
			}
			Integer lastSeq = wikiPageMapper.getLastSeq(wikiPage.getParentId());
			lastSeq = Optional.ofNullable(lastSeq).orElse(0);
			wikiPage.setSeqNo(lastSeq + 1);
			wikiPage.setCreateTime(new Date());
			wikiPage.setUpdateTime(new Date());
			wikiPage.setCreateUserId(currentUser.getUserId());
			wikiPage.setCreateUserName(currentUser.getUsername());
			wikiPageService.save(wikiPage);
			// 详情
			pageContent.setPageId(wikiPage.getId());
			pageContent.setCreateTime(new Date());
			pageContent.setCreateUserId(currentUser.getUserId());
			pageContent.setCreateUserName(currentUser.getUsername());
			wikiPageContentService.save(pageContent);
		}
		// 保存到es
		if (esWikiPageService.isOpen()) {
			WikiPage wikiPageSel = wikiPageService.getById(wikiPage.getId());
			EsWikiPage esWikiPage = mapper.map(wikiPageSel, EsWikiPage.class);
			esWikiPage.setContent(content);
			esWikiPage.setPreview(preview);
			esWikiPageService.upsert(esWikiPage);
		} else {
			logger.warn("未开启elasticsearch服务，建议开启");
		}
		return DocResponseJson.ok(wikiPage);
	}
	
	@PostMapping("/unlock")
	public ResponseJson<Object> unlock(Long pageId) {
		String lockKey = WikiCachePrefix.WIKI_LOCK_PAGE + pageId;
		DocUserDetails pageLockUser = CacheUtil.get(lockKey);
		if (pageLockUser != null) {
			DocUserDetails currentUser = DocUserUtil.getCurrentUser();
			if (Objects.equals(pageLockUser.getUserId(), currentUser.getUserId())) {
				CacheUtil.remove(lockKey);
			}
		}
		return DocResponseJson.ok();
	}
	
	@PostMapping("/lock")
	public ResponseJson<Object> editLock(Long pageId) {
		DocUserDetails currentUser = DocUserUtil.getCurrentUser();
		String lockKey = WikiCachePrefix.WIKI_LOCK_PAGE + pageId;
		DocUserDetails pageLockUser = CacheUtil.get(lockKey);
		if (pageLockUser != null) {
			if (!Objects.equals(pageLockUser.getUserId(), currentUser.getUserId())) {
				return DocResponseJson.warn("当前页面正在被：" + pageLockUser.getUsername() + " 编辑");
			}
		}
		CacheUtil.put(lockKey, new DocUserDetails(currentUser.getUserId(), currentUser.getUsername()));
		return DocResponseJson.ok();
	}
	
	@PostMapping("/searchByEs")
	public ResponseJson<Object> searchByEs(SearchByEsParam param) {
		if (esWikiPageService.isOpen()) {
			Map<Long, WikiSpace> wikiSpaceMap = this.getCanVisitWikiSpace(param.getSpaceId());
			if (wikiSpaceMap.isEmpty()) {
				return DocResponseJson.ok();
			}
			String[] fields = {
					"id", "preview", "createTime", "updateTime", "createUserId", "createUserName",
					"updateUserId", "updateUserName", "spaceId", "name", "zanNum", "viewNum",
			};
			BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
			if (StringUtils.isNotBlank(param.getKeywords())) {
				boolQueryBuilder.must(QueryBuilders.multiMatchQuery(param.getKeywords(), "name", "preview"));
			}
			boolQueryBuilder.must(QueryBuilders.termQuery("delFlag", "0"));
			boolQueryBuilder.must(QueryBuilders.termsQuery("spaceId", wikiSpaceMap.keySet().toArray()));
			EsPage<EsWikiPage> wikiPageEsPage = esWikiPageService.getDataByQuery(boolQueryBuilder, fields, param.getStartIndex(), param.getPageSize());
			if (wikiPageEsPage == null || wikiPageEsPage.getTotal() == null) {
				return DocResponseJson.ok();
			}
			// 组装数据
			List<EsWikiPage> esWikiPageList = wikiPageEsPage.getData();
			List<SpaceNewsVo> pageVoList = new LinkedList<>();
			esWikiPageList.forEach(val -> {
				String preview = val.getPreview();
				if (preview != null && preview.length() > 200) {
					preview = preview.substring(0, 200);
				}
				SpaceNewsVo spaceNewsVo = mapper.map(val, SpaceNewsVo.class);
				spaceNewsVo.setSpaceName(wikiSpaceMap.get(val.getSpaceId()).getName());
				spaceNewsVo.setPreviewContent(preview);
				spaceNewsVo.setPageTitle(val.getName());
				spaceNewsVo.setPageId(val.getId());
				pageVoList.add(spaceNewsVo);
			});
			// 组装数据返回
			PageInfo<SpaceNewsVo> pageInfo = new PageInfo<>();
			pageInfo.setTotal(wikiPageEsPage.getTotal());
			pageInfo.setList(pageVoList);
			return DocResponseJson.ok(pageInfo);
		} else {
			logger.warn("未开启elasticsearch服务，使用数据库查询匹配，建议开启");
			param.setNewsType(1);
			return this.news(param);
		}
	}
	
	@PostMapping("/news")
	public ResponseJson<Object> news(SearchByEsParam param) {
		// 空间不是自己的
		Map<Long, WikiSpace> wikiSpaceMap = this.getCanVisitWikiSpace(param.getSpaceId());
		if (wikiSpaceMap.isEmpty()) {
			return DocResponseJson.ok();
		}
		param.setSpaceIds(new ArrayList<>(wikiSpaceMap.keySet()));
		String keywords = param.getKeywords();
		if (StringUtils.isNotBlank(keywords)) {
			param.setKeywords("%" + keywords + "%");
		}
		// 分页查询
		PageHelper.startPage(param.getPageNum(), param.getPageSize(), true);
		List<SpaceNewsVo> spaceNewsVoList = wikiPageContentMapper.getNewsList(param);
		PageInfo<SpaceNewsVo> pageListPageInfo = new PageInfo<>(spaceNewsVoList);
		if (spaceNewsVoList == null || spaceNewsVoList.isEmpty()) {
			return DocResponseJson.ok(pageListPageInfo);
		}
		spaceNewsVoList.forEach(val -> {
			val.setSpaceName(wikiSpaceMap.get(val.getSpaceId()).getName());
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
		return DocResponseJson.ok(pageListPageInfo);
	}
	
	private Map<Long, WikiSpace> getCanVisitWikiSpace(Long spaceId) {
		DocUserDetails currentUser = DocUserUtil.getCurrentUser();
		List<WikiSpace> spaceList;
		// 空间不是自己的
		if (spaceId == null || spaceId <= 0) {
			QueryWrapper<WikiSpace> queryWrapper = new QueryWrapper<>();
			queryWrapper.eq("del_flag", 0);
			queryWrapper.ne("type", SpaceType.privateSpace);
			spaceList = wikiSpaceService.list(queryWrapper);
		} else {
			WikiSpace wikiSpaceSel = wikiSpaceService.getById(spaceId);
			if (SpaceType.isOthersPrivate(wikiSpaceSel.getType(), currentUser.getUserId(), wikiSpaceSel.getCreateUserId())) {
				return Collections.emptyMap();
			}
			spaceList = Collections.singletonList(wikiSpaceSel);
		}
		return spaceList.stream().collect(Collectors.toMap(WikiSpace::getId, val -> val));
	}
	
	private void setChildren(Map<Long, List<WikiPageVo>> listMap, List<WikiPageVo> nodePageList, String path) {
		if (nodePageList == null || listMap == null) {
			return;
		}
		for (WikiPageVo page : nodePageList) {
			String nowPath = path + "/" + page.getName();
			page.setPath(nowPath);
			List<WikiPageVo> wikiPageVos = listMap.get(page.getId());
			if (CollectionUtils.isNotEmpty(wikiPageVos)) {
				wikiPageVos = wikiPageVos.stream().sorted(Comparator.comparingInt(WikiPage::getSeqNo)).collect(Collectors.toList());
				page.setChildren(wikiPageVos);
				this.setChildren(listMap, wikiPageVos, nowPath);
			}
		}
	}
}

