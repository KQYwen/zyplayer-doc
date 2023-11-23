package com.zyplayer.doc.manage.framework.console;

import org.springframework.core.Ordered;

/**
 * 启动后打印信息接口
 *
 * @author Sh1yu
 * @since 2023年6月16日
 */
public interface IConsolePrint extends Ordered {
    void buildPrintInfo(StringBuilder printInfo) throws Exception;
}
