package com.zyplayer.doc.manage.framework.console;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 程序启动后内容打印，新增打印内容只需要继承IConsolePrint
 *
 * @author 暮光：城中城
 * @author Sh1yu
 * @See IConsolePrint
 * @since 2023年6月15日
 */

@Component
public class ZyplayerConsolePrint implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(ZyplayerConsolePrint.class);
    final StringBuilder logInfoHolder = new StringBuilder();

    @Resource
    ObjectProvider<List<IConsolePrint>> consolePrintListProvider;

    public void run(String... args) throws Exception {
        if (!logger.isInfoEnabled()) {
            return;
        }
        List<IConsolePrint> consolePrintList = consolePrintListProvider.getIfAvailable();
        if (consolePrintList.isEmpty()) {
            return;
        }
        logInfoHolder.append("\n--------------------------------------------------------------\n\t");
        List<IConsolePrint> collect = consolePrintList.stream()
                .sorted(Comparator.comparingInt(IConsolePrint::getOrder))
                .collect(Collectors.toList());
        for (IConsolePrint consolePrint : collect) {
            consolePrint.buildPrintInfo(logInfoHolder);
        }
        logInfoHolder.append("--------------------------------------------------------------\n\t");
        logger.info(logInfoHolder.toString());
    }


}
