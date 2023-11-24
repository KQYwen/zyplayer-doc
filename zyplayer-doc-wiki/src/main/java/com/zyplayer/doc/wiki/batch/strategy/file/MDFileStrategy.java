package com.zyplayer.doc.wiki.batch.strategy.file;

import cn.hutool.core.io.IoUtil;
import com.zyplayer.doc.data.repository.manage.entity.WikiPage;
import com.zyplayer.doc.data.repository.manage.entity.WikiPageFile;
import com.zyplayer.doc.data.service.manage.WikiPageService;
import com.zyplayer.doc.wiki.service.WikiPageUploadService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.Charsets;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * MD格式文件上传策略
 *
 * @author Sh1yu
 * @since 2023年7月13日
 */
@Component
@RequiredArgsConstructor
public class MDFileStrategy implements IFileStrategy {
	
	private final WikiPageService wikiPageService;
	private final WikiPageUploadService wikipageUploadService;
	
	@Override
	public void file(String uploadPath, WikiPageFile wikiPageFile, MultipartFile file) throws IOException {
		String fileName = StringUtils.defaultString(file.getOriginalFilename(), "新建文档");
		Long pageId = wikiPageFile.getPageId();
		WikiPage page = wikiPageService.getById(pageId);
		WikiPage wikiPage = new WikiPage();
		wikiPage.setName(fileName.substring(0, fileName.indexOf(".")));
		Long spaceId = wikiPageFile.getId();
		Long id = wikiPageFile.getPageId();
		if (null != page) {
			spaceId = page.getSpaceId();
			id = page.getId();
		}
		wikiPage.setSpaceId(spaceId);
		wikiPage.setParentId(id);
		wikiPage.setEditorType(2);
		String context = IoUtil.read(file.getInputStream(), Charsets.UTF_8);
		wikipageUploadService.update(wikiPage, context, context);
	}
	
	@Override
	public String getCondition() {
		return "md";
	}
}
