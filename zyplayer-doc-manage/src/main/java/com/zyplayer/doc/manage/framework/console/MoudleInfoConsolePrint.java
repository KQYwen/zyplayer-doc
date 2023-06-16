package com.zyplayer.doc.manage.framework.console;

import com.zyplayer.doc.manage.framework.config.ZyplayerMoudleKeeper;
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
public class MoudleInfoConsolePrint implements IConsolePrint {
    @Resource
    ZyplayerMoudleKeeper moudleKeeper;

    @Override
    public void buildPrintInfo(StringBuffer printInfo) throws Exception {
        printInfo.append("\n\n\t\t\t\t↓zyplayer-doc模块的启动情况\n")
                .append("\t\t\t\t------------------------\n");
        HashMap<String, Boolean> moudleInfos = moudleKeeper.getMoudleInfo();
        for (Map.Entry<String, Boolean> moudleInfo : moudleInfos.entrySet()) {
            printInfo.append(getPerfectPosString(moudleInfo.getKey()))
                    .append("模块启动情况为：")
                    .append(false == moudleInfo.getValue() ? "未启动\n" : "启动成功\n");
        }
    }


    private String getPerfectPosString(String beforeString) {
        final int pointLeft = 19;
        String afterOptin = beforeString.replace("enable", "");
        int length = afterOptin.length();
        int rightOffset = pointLeft - length;
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < rightOffset; i++) {
            stringBuffer.append(" ");
        }
        stringBuffer.append(afterOptin);
        return stringBuffer.toString();
    }

    @Override
    public int getOrder() {
        return 1;
    }
}
