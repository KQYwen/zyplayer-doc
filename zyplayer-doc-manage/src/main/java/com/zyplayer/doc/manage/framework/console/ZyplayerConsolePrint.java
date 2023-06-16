package com.zyplayer.doc.manage.framework.console;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 程序启动后内容打印，新增打印内容只需要继承IConsolePrint
 * @author 暮光：城中城
 * @author Sh1yu
 * @since 2023年6月15日
 * @See IConsolePrint
 */

@Component
public class ZyplayerConsolePrint implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(ZyplayerConsolePrint.class);
    StringBuffer logInfoHolder = new StringBuffer();

    @Autowired
    ObjectProvider<List<IConsolePrint>> print;

    public void run(String... args) throws Exception {
        if (logger.isInfoEnabled()) {
            List<IConsolePrint> prints = print.getIfAvailable();
            if (prints.size() < 1) {
                return;
            }
            logInfoHolder.append("\n--------------------------------------------------------------\n\t");
            List<IConsolePrint> collect = prints.stream().sorted((a, b) -> {
                int aOrder = a.getOrder();
                int bOrder = b.getOrder();
                if (aOrder > bOrder) {
                    return 1;
                }
                if (aOrder < bOrder) {
                    return -1;
                }
                return 0;

            }).collect(Collectors.toList());
            for (IConsolePrint consolePrint : collect) {
                consolePrint.buildPrintInfo(logInfoHolder);
            }
            logInfoHolder.append("--------------------------------------------------------------\n\t");
            logger.info(logInfoHolder.toString());
        }
    }


}
