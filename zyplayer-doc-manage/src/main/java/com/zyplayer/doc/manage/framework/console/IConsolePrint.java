package com.zyplayer.doc.manage.framework.console;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import java.net.UnknownHostException;

/**
 * 启动后打印信息接口
 *
 * @author Sh1yu
 * @since 2023年6月16日
 */
public interface IConsolePrint extends Ordered {
    public void buildPrintInfo(StringBuffer printInfo) throws Exception;
}
