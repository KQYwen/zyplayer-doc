package com.zyplayer.doc.data.repository.manage.vo;

import lombok.Data;

import java.util.Date;

@Data
public class WikiTemplateTagVo {

    /**
     * 是否展示
     */
    private boolean show = true;

    /**
     * 标签名
     */
    private String tagName;
}
