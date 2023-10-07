package com.zyplayer.doc.wiki.batch;

import cn.hutool.core.io.FileUtil;
import com.zyplayer.doc.core.json.DocResponseJson;
import com.zyplayer.doc.data.repository.manage.entity.WikiPageFile;
import com.zyplayer.doc.wiki.batch.entry.DocEntry;
import com.zyplayer.doc.wiki.batch.strategy.ConditionalStrategySelector;
import com.zyplayer.doc.wiki.batch.strategy.comb.ICombDependencyStrategy;
import com.zyplayer.doc.wiki.batch.strategy.file.IFileStrategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.util.ArrayList;

/**
 * 文档批量导入功能
 *
 * @author Sh1yu
 * @since 2023年7月13日
 */
@Component
@Slf4j
public class BatchDocImportManager {

    @Value("${zyplayer.doc.wiki.upload-path:}")
    private String uploadPath;
    @Resource
    ConditionalStrategySelector conditionalStrategySelector;

    /**
     * 文档批量导入策略选择
     *
     * @author Sh1yu
     * @since 2023年7月13日
     */
    public DocResponseJson<Object> importBatchDoc(WikiPageFile wikiPageFile, MultipartFile file) {
        String suffix = FileUtil.getSuffix(file.getOriginalFilename());
        try {
            IFileStrategy strategy = conditionalStrategySelector.getStrategy(suffix, IFileStrategy.class);
            if (null == strategy) {
                if (log.isInfoEnabled()) {
                    log.info("暂时不支持{}格式文件的导入", suffix);
                }
                return DocResponseJson.warn("暂时不支持" + suffix + "格式文件的导入");
            }
            if (log.isInfoEnabled()) {
                log.info("文档导入的格式为{}，选择的策略为{}", suffix, strategy.getClass().getName());
            }
            strategy.file(uploadPath, wikiPageFile, file);
        } catch (Exception e) {
            log.warn("导入文件发生错误：{}", e.getMessage());
            return DocResponseJson.warn("导入文件发生错误！");
        }
        return DocResponseJson.ok();
    }

    /**
     * 文档解析策略选择
     *
     * @author Sh1yu
     * @since 2023年7月13日
     */
    public ArrayList<DocEntry> combDependency(File[] files) {
        ArrayList<DocEntry> docs = new ArrayList<>();
        for (File file : files) {
            if (file.isDirectory()) {
                docs.addAll(combDependency(file.listFiles()));
            }
            if (file.isFile()) {
                String suffix = FileUtil.getSuffix(file);
                ICombDependencyStrategy strategy = conditionalStrategySelector.getStrategy(suffix, ICombDependencyStrategy.class);
                if (null == strategy) {
                    continue;
                }
                strategy.comb(docs, file);
            }
        }
        return docs;
    }
}
