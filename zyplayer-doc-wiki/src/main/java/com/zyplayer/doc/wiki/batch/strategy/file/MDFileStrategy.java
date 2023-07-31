package com.zyplayer.doc.wiki.batch.strategy.file;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.io.FileUtil;
import com.zyplayer.doc.data.repository.manage.entity.WikiPage;
import com.zyplayer.doc.data.repository.manage.entity.WikiPageFile;
import com.zyplayer.doc.data.service.manage.WikiPageService;
import com.zyplayer.doc.wiki.service.WikiPageFileServiceEx;
import com.zyplayer.doc.wiki.service.WikiPageUploadService;
import lombok.RequiredArgsConstructor;
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
    private final WikiPageFileServiceEx wikiPageFileServiceEx;

    @Override
    public String file(String uploadPath, WikiPageFile wikiPageFile, MultipartFile file)throws IOException{
        Long pageID = wikiPageFile.getPageId();
        WikiPage page = wikiPageService.getById(pageID);
        WikiPage wikiPage = new WikiPage();
        wikiPage.setName(file.getOriginalFilename().substring(0,file.getOriginalFilename().indexOf(".")));
        Long spaceId = wikiPageFile.getId();
        Long id = wikiPageFile.getPageId();
        if (null != page) {
            spaceId = page.getSpaceId();
            id = page.getId();
        }
        wikiPage.setSpaceId(spaceId);
        wikiPage.setParentId(id);
        wikiPage.setEditorType(2);
        String context ="";
        wikipageUploadService.update(wikiPage,context,context);
        wikiPageFile.setPageId(wikiPage.getId());
        wikiPageFileServiceEx.uploadFile(wikiPageFile,file,0);
        String path = uploadPath + "/" + DateTime.now().toString("yyyy/MM/dd") + "/"+wikiPageFile.getUuid()+wikiPageFile.getFileName().substring(wikiPageFile.getFileName().indexOf("."));
        context = FileUtil.readUtf8String(path);
        wikipageUploadService.update(wikiPage,context,context);
        return null;
    }

    @Override
    public String getCondition() {
        return "md";
    }
}
