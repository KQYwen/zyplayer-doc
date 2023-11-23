package com.zyplayer.doc.manage.framework.console;

import com.zyplayer.doc.manage.framework.config.ZyplayerModuleKeeper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * 启动后打印模块信息
 *
 * @author Sh1yu
 * @since 2023年6月16日
 */
@Component
public class ModuleInfoConsolePrint implements IConsolePrint {
    @Resource
    ZyplayerModuleKeeper moduleKeeper;

    @Override
    public void buildPrintInfo(StringBuilder printInfo) {
        printInfo.append("\n\n\t\t\t\t↓zyplayer-doc模块的启动情况\n")
                .append("\t\t\t\t------------------------\n");
        HashMap<String, Boolean> moduleInfoMap = moduleKeeper.getmoduleInfo();
        for (Map.Entry<String, Boolean> moduleInfo : moduleInfoMap.entrySet()) {
            printInfo.append(getPerfectPosString(moduleInfo.getKey()))
                    .append("模块启动情况为：")
                    .append(moduleInfo.getValue() ? "启动成功\n" : "未启动\n");
        }
    }


    private String getPerfectPosString(String beforeOption) {
        final int rightOffsetMax = 19;
        String afterOption = beforeOption.replace("enable", "");
        int length = afterOption.length();
        int rightOffset = rightOffsetMax - length;
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < rightOffset; i++) {
            stringBuilder.append(" ");
        }
        stringBuilder.append(afterOption);
        return stringBuilder.toString();
    }

    @Override
    public int getOrder() {
        return 1;
    }
}
