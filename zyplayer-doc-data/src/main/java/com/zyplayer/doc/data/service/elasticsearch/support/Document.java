package com.zyplayer.doc.data.service.elasticsearch.support;

import java.lang.annotation.*;

/**
 * es文档定义
 * @author 暮光：城中城
 * @since 2019-07-07
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface Document {
    String indexName();

    String indexType();
}
