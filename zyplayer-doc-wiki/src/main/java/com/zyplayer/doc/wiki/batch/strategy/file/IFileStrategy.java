package com.zyplayer.doc.wiki.batch.strategy.file;

import com.zyplayer.doc.data.repository.manage.entity.WikiPageFile;
import com.zyplayer.doc.wiki.batch.strategy.base.IConditionalStrategy;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * 条件控制文件归档策略接口
 *
 * @author Sh1yu
 * @since 20230717
 */
public interface IFileStrategy extends IConditionalStrategy {
    void file(String uploadPath, WikiPageFile wikiPageFile, MultipartFile file)throws IOException;
}
