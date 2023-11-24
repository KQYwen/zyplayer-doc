package com.zyplayer.doc.data.repository.manage.vo;

import lombok.Data;


/**
 * <p>
 *     标签信息的Vo
 * </p>
 *
 * @author Sh1yu
 * @since 2023-08-24
 */
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
