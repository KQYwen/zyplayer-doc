package com.zyplayer.doc.wiki.batch.entry;

import java.util.LinkedList;

/**
 * 文档实体
 *
 * @author Sh1yu
 * @since 20230713
 */
public class DocEntry {
    final LinkedList<MediaEntry> medias = new LinkedList<>();
    String context = "";
    String name = "";

    public LinkedList<MediaEntry> getMedias() {
        return medias;
    }

    public void addMedia(MediaEntry media) {
        this.medias.add(media);
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
