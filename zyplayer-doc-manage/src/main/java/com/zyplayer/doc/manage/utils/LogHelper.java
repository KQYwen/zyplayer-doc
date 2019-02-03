package com.zyplayer.doc.manage.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 日志工具类
 */
public class LogHelper {
    // 通用日志，包括sql
    public static final Logger LOG_COMMON = LoggerFactory.getLogger("LOG_COMMON");
    // 错误的日志打印
    public static final Logger LOG_ERROR = LoggerFactory.getLogger("LOG_ERROR");
}
