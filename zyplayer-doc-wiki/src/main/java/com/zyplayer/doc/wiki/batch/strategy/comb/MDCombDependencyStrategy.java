package com.zyplayer.doc.wiki.batch.strategy.comb;

import cn.hutool.core.io.FileUtil;
import com.zyplayer.doc.wiki.batch.entry.DocEntry;
import com.zyplayer.doc.wiki.batch.entry.MediaEntry;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.ArrayList;
/**
 * MD格式内容分析策略
 *
 * @author Sh1yu
 * @since 2023年7月13日
 */
@Component
public class MDCombDependencyStrategy implements ICombDependencyStrategy {
    private final static String LEFT_TAG = "![";
    private final static String RIGHT_TAG_REG = "]\\(";
    private final static String RIGHT_TAG = "](";

    @Override
    public void comb(ArrayList<DocEntry> docs, File file) {
        DocEntry docEntry = new DocEntry();
        docEntry.setName(FileUtil.getName(file));
        String info = FileUtil.readUtf8String(file);
        docEntry.setContext(info);
        while (info.contains(LEFT_TAG) && info.contains(RIGHT_TAG)) {
            String window = info;
            while(window.contains(LEFT_TAG)){
                int leftOffset = window.indexOf(LEFT_TAG)+2;
                window= window.substring(leftOffset);
                int rightOffset = window.indexOf(")")+1;
                window = window.substring(0,rightOffset);
            }
            String [] splitWindow = window.split(RIGHT_TAG_REG);
            MediaEntry mediaEntry = new MediaEntry();
            mediaEntry.setOldFileName(splitWindow[0]);
            String realWindow = splitWindow[1].replace(")","");
            int pos = realWindow.lastIndexOf(File.separator);
            String oldFileLink = realWindow;
            if (pos>0) {
                realWindow= realWindow.substring(pos+1);
            }
            mediaEntry.setOldFileLink(file.getAbsolutePath().replace(file.getName(),"")+realWindow,oldFileLink);
            info = info.substring(info.indexOf(window));
            docEntry.addMedia(mediaEntry);
        }
        docs.add(docEntry);
    }

    @Override
    public String getCondition() {
        return "md";
    }
}
