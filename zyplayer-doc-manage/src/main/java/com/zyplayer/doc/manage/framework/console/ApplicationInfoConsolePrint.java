package com.zyplayer.doc.manage.framework.console;

import com.zyplayer.doc.core.util.ZyplayerDocVersion;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.net.InetAddress;
import java.util.Optional;

/**
 * 启动后打印应用信息
 *
 * @author Sh1yu
 * @since 2023年6月16日
 */
@Component
public class ApplicationInfoConsolePrint implements IConsolePrint {
    @Resource
    Environment environment;

    @Override
    public void buildPrintInfo(StringBuilder printInfo) throws Exception {
        String contextPath = environment.getProperty("server.servlet.context-path");
        contextPath = Optional.ofNullable(contextPath).orElse("").replaceFirst("/", "");
        contextPath = (contextPath.isEmpty() || contextPath.endsWith("/")) ? contextPath : contextPath + "/";
        String hostAddress = InetAddress.getLocalHost().getHostAddress();
        String serverPort = environment.getProperty("server.port");
        String urlCtx = hostAddress + ":" + serverPort + "/" + contextPath;
        printInfo.append("\t     zyplayer-doc启动完成，当前版本：")
                .append(ZyplayerDocVersion.version)
                .append("\n\t访问地址：http://")
                .append(urlCtx);
    }

    @Override
    public int getOrder() {
        return -1;
    }
}
