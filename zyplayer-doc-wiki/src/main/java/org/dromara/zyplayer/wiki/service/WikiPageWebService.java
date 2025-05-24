package org.dromara.zyplayer.wiki.service;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.file.FileNameUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.URLUtil;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.system.SystemUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.collections4.CollectionUtils;
import org.dromara.zyplayer.core.json.DocResponseJson;
import org.dromara.zyplayer.core.json.ResponseJson;
import org.dromara.zyplayer.data.config.security.DocUserDetails;
import org.dromara.zyplayer.data.config.security.DocUserUtil;
import org.dromara.zyplayer.data.repository.manage.entity.WikiPage;
import org.dromara.zyplayer.data.repository.manage.entity.WikiPageTemplate;
import org.dromara.zyplayer.data.repository.manage.entity.WikiSpace;
import org.dromara.zyplayer.data.repository.manage.mapper.WikiPageMapper;
import org.dromara.zyplayer.data.service.manage.WikiPageService;
import org.dromara.zyplayer.data.service.manage.WikiPageTemplateService;
import org.dromara.zyplayer.data.service.manage.WikiSpaceService;
import org.dromara.zyplayer.data.service.params.PageDeleteParam;
import org.dromara.zyplayer.data.service.params.PageRecycleListParam;
import org.dromara.zyplayer.data.service.params.RecycleDeletePageParam;
import org.dromara.zyplayer.data.utils.HtmlUtils;
import org.dromara.zyplayer.wiki.controller.vo.WikiPageVo;
import org.dromara.zyplayer.wiki.framework.consts.SpaceType;
import org.apache.commons.lang3.StringUtils;
import org.docx4j.XmlUtils;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.AltChunkType;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Entities;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class WikiPageWebService {
	private static final Logger logger = LoggerFactory.getLogger(WikiPageWebService.class);
	
	@Resource
	WikiPageService wikiPageService;
	@Resource
	WikiSpaceService wikiSpaceService;
	@Resource
	WikiPageMapper wikiPageMapper;
	@Resource
	WikiPageTemplateService wikiPageTemplateService;
	
	public ResponseJson<Object> download(Long pageId, String content, HttpServletRequest request, HttpServletResponse response) {
		DocUserDetails currentUser = DocUserUtil.getCurrentUser();
		String requestURI = request.getRequestURL().toString();
		WikiPage wikiPageSel = wikiPageService.getById(pageId);
		// 页面已删除
		if (wikiPageSel == null || Objects.equals(wikiPageSel.getDelFlag(), 1)) {
			return DocResponseJson.warn("该页面不存在或已删除！");
		}
		WikiSpace wikiSpaceSel = wikiSpaceService.getById(wikiPageSel.getSpaceId());
		// 空间已删除
		if (wikiSpaceSel == null || Objects.equals(wikiSpaceSel.getDelFlag(), 1)) {
			return DocResponseJson.warn("该页面不存在或已删除！");
		}
		// 私人空间
		if (SpaceType.isOthersPrivate(wikiSpaceSel.getType(), currentUser.getUserId(), wikiSpaceSel.getCreateUserId())) {
			return DocResponseJson.warn("您没有权限查看该空间的文章详情！");
		}
		String srcFilePath = new File(SystemUtil.get("java.io.tmpdir"), IdUtil.objectId()).getPath();
		try {
			String fileName = URLEncoder.encode(wikiPageSel.getName(), "UTF-8").replaceAll("\\+", "%20");
			String domainUri = requestURI.substring(0, requestURI.indexOf("/zyplayer-doc-wiki") + 1);
			// 解析内容，并替换图片URL
			Document document = Jsoup.parse(content);
			document.outputSettings().syntax(Document.OutputSettings.Syntax.xml).escapeMode(Entities.EscapeMode.xhtml);
			Elements images = document.select("img");
			for (Element image : images) {
				String src = image.attr("src");
				if (StringUtils.startsWith(src, "zyplayer-doc-wiki/common/file?uuid=")) {
					src = domainUri + src;
				}
				try {
					HttpResponse execute = HttpUtil.createGet(src).timeout(5000).execute();
					String originFileName = HtmlUtils.extractFilename(execute.header("Content-Disposition"), "image.png");
					String extName = FileNameUtil.extName(originFileName);
					File srcFile = new File(srcFilePath, IdUtil.objectId() + "." + extName);
					FileUtil.writeFromStream(execute.bodyStream(), srcFile);
					image.attr("src", URLUtil.getURL(srcFile).toString());
				} catch (Exception e) {
					image.attr("src", "");
					logger.error("下载图片失败，忽略异常：{}", e.getMessage());
				}
			}
			content = "<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01 Strict//EN\" \"http://www.w3.org/TR/html4/strict.dtd\">\n" +
					"<html lang=\"zh\">\n" +
					"<head>\n" +
					"<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />\n" +
					"</head>\n" +
					"<body>" + document.body().html() + "</body>\n" +
					"</html>";
			// 写入流
			response.setCharacterEncoding("utf-8");
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".docx");
			ServletOutputStream outputStream = response.getOutputStream();
			WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage.createPackage();
			MainDocumentPart mdp = wordMLPackage.getMainDocumentPart();
			mdp.addAltChunk(AltChunkType.Xhtml, content.getBytes(StandardCharsets.UTF_8));
			mdp.convertAltChunks();
			XmlUtils.marshaltoString(wordMLPackage.getMainDocumentPart().getJaxbElement(), true, true);
			wordMLPackage.save(outputStream);
			outputStream.close();
			return DocResponseJson.ok();
		} catch (Exception e) {
			logger.error("下载文件失败", e);
		} finally {
			FileUtil.del(srcFilePath);
		}
		return DocResponseJson.warn("导出失败");
	}
	
	/**
	 * 获取空间已删除的页面
	 *
	 * @author 暮光：城中城
	 * @since 2023-04-29
	 */
	public ResponseJson<List<WikiPageVo>> recycleList(PageRecycleListParam param) {
		// 删除的页面
		WikiSpace wikiSpaceSel = wikiSpaceService.getById(param.getSpaceId());
		// 不是空间的协作者返回空列表 TODO 判断权限
		if (wikiSpaceSel == null) {
			return DocResponseJson.ok();
		}
		LambdaQueryWrapper<WikiPage> wrapper = new LambdaQueryWrapper<>();
		wrapper.eq(WikiPage::getDelFlag, 1);
		wrapper.eq(WikiPage::getSpaceId, param.getSpaceId());
		wrapper.orderByDesc(WikiPage::getUpdateTime).orderByDesc(WikiPage::getId);
		IPage<WikiPage> page = new Page<>(param.getPageNum(), 20, true);
		wikiPageService.page(page, wrapper);
		DocResponseJson<List<WikiPageVo>> responseJson = DocResponseJson.ok();
		responseJson.setTotal(page.getTotal());
		if (CollectionUtils.isNotEmpty(page.getRecords())) {
			responseJson.setData(page.getRecords().stream().map(WikiPageVo::new).collect(Collectors.toList()));
		}
		return responseJson;
	}
	
	public ResponseJson<Object> restore(String pageIds) {
		String[] pageIdArr = StringUtils.split(pageIds, ",");
		for (String pageId : pageIdArr) {
			DocResponseJson<Object> restoreRes = this.restore(Long.valueOf(pageId));
			if (!restoreRes.isOk()) {
				return restoreRes;
			}
		}
		return DocResponseJson.ok();
	}
	
	public DocResponseJson<Object> restore(Long pageId) {
		DocUserDetails currentUser = DocUserUtil.getCurrentUser();
		WikiPage wikiPageSel = wikiPageService.getById(pageId);
		WikiSpace wikiSpaceSel = wikiSpaceService.getById(wikiPageSel.getSpaceId());
		// 是否具有编辑权限 TODO
//		if (!userDataAuthService.canEditPage(wikiSpaceSel.getId(), pageId, wikiSpaceSel.getCreateUserId())) {
//			return DocResponseJson.warn(ErrorEnum.NO_PERMISSION_TO_RESTORE_ARTICLE);
//		}
		// 执行恢复操作
		WikiPage wikiPage = new WikiPage();
		wikiPage.setId(pageId);
		wikiPage.setDelFlag(0);
		wikiPage.setName(wikiPageSel.getName());
		wikiPage.setUpdateTime(new Date());
		wikiPage.setUpdateUserId(currentUser.getUserId());
		wikiPage.setUpdateUserName(currentUser.getUsername());
		wikiPageService.updateById(wikiPage);
		// 重置当前分支的所有节点seq值
		wikiPageMapper.updateChildrenSeq(wikiPageSel.getSpaceId(), wikiPageSel.getParentId());
		return DocResponseJson.ok();
	}
	
	public DocResponseJson<Object> delete(PageDeleteParam param) {
		DocUserDetails currentUser = DocUserUtil.getCurrentUser();
		WikiPage wikiPageSel = wikiPageService.getById(param.getPageId());
		// 删除权限判断 TODO
		WikiSpace wikiSpaceSel = wikiSpaceService.getById(wikiPageSel.getSpaceId());
//		String canDelete = wikiPageAuthService.canDelete(wikiSpaceSel, wikiPageSel.getEditType(), wikiPageSel.getId(), currentUser.getUserId());
//		if (canDelete != null) {
//			return DocResponseJson.warn(canDelete);
//		}
		// 执行删除
		WikiPage wikiPage = new WikiPage();
		wikiPage.setId(param.getPageId());
		wikiPage.setDelFlag(Optional.ofNullable(param.getDelFlag()).orElse(1));
		wikiPage.setName(wikiPageSel.getName());
		wikiPage.setUpdateTime(new Date());
		wikiPage.setUpdateUserId(currentUser.getUserId());
		wikiPage.setUpdateUserName(currentUser.getUsername());
		wikiPageService.deletePage(wikiPage);
		QueryWrapper<WikiPageTemplate> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("space_id", wikiPageSel.getSpaceId());
		queryWrapper.eq("page_id", wikiPageSel.getId());
		wikiPageTemplateService.remove(queryWrapper);
		return DocResponseJson.ok();
	}
	
	public ResponseJson<Object> recycleDelete(RecycleDeletePageParam param) {
		String[] pageIdArr = StringUtils.split(param.getPageIds(), ",");
		for (String pageId : pageIdArr) {
			PageDeleteParam deleteParam = new PageDeleteParam();
			deleteParam.setPageId(Long.valueOf(pageId));
			deleteParam.setSpaceId(param.getSpaceId());
			deleteParam.setDelFlag(2);
			DocResponseJson<Object> restoreRes = this.delete(deleteParam);
			if (!restoreRes.isOk()) {
				return restoreRes;
			}
		}
		return DocResponseJson.ok();
	}
}
