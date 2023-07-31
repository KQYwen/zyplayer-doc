package com.zyplayer.doc.wiki.batch.entry;

/**
 * 媒体实体
 *
 * @author Sh1yu
 * @since 20230713
 */
public class MediaEntry {
    String oldFileLink;
    String oldFileLinkName;
    String oldFileName;

    public String getOldFileLink() {
        return oldFileLink;
    }

    public void setOldFileLink(String oldFileLink,String oldFileLinkName) {
        this.oldFileLink = oldFileLink;
        this.oldFileLinkName = oldFileLinkName;
    }

    public String getOldFileLinkName() {
        return oldFileLinkName;
    }

    public void setOldFileLinkName(String oldFileLinkName) {
        this.oldFileLinkName = oldFileLinkName;
    }

    public String getOldFileName() {
        return oldFileName;
    }

    public void setOldFileName(String oldFileName) {
        this.oldFileName = oldFileName;
    }
}
