package com.zyplayer.doc.db.framework.utils;

import cn.hutool.core.util.IdUtil;
import com.alibaba.excel.util.DateUtils;
import com.zyplayer.doc.data.repository.manage.entity.BackupLog;
import com.zyplayer.doc.data.utils.CleanInputCache;
import com.zyplayer.doc.db.framework.db.vo.BackupJobVO;
import com.zyplayer.doc.db.framework.db.vo.BackupRespVO;
import org.springframework.scheduling.annotation.Async;
import java.io.*;
import java.util.Date;

/**
 * 数据库备份恢复工具类
 *
 * @author diantu
 * @since 2023年2月8日
 */
public class DatabaseBackupUtils {

    /**
     * 项目路径
     */
    public static final String PROJECT_PATH = System.getProperty("user.dir");

    /**
     * 当前系统类型
     */
    public static final String OS_NAME = System.getProperty("os.name");

    /**
     * 编码格式
     */
    public static final String CHAR_SET = OS_NAME.startsWith("Win") ? "GBK" : "UTF-8";

    /**
     * 异步执行备份任务，更新备份记录
     *
     * @param jobVO     备份参数
     * @param backupLog 备份记录
     */
    @Async
    public void saveBackUp(BackupJobVO jobVO, BackupLog backupLog) {
        // 执行备份
        BackupRespVO respVO = doBackup(jobVO);
        backupLog.setStatus(respVO.isSuccess() ? (jobVO.getIsUpload() ? 1 : 2) : -1);
        backupLog.setMsg(respVO.getMsg());
        // 备份成功
        if (respVO.isSuccess()) {
            // 文件相对路径
            backupLog.setFilePath(respVO.getFile().getPath().replace(PROJECT_PATH + File.separator, ""));
            backupLog.setFileSize(respVO.getFile().length());
        }
        // 备份失败
        if (!respVO.isSuccess()) {
            if (null != respVO.getFile()) {
                respVO.getFile().delete();
            }
        }
        // 计算耗时
        backupLog.setEndTime(new Date());
        backupLog.setSpendTime(backupLog.getEndTime().getTime() - backupLog.getStartTime().getTime());
        //@TODO 存入备份记录信息
        // 备份文件上传至文件服务器
        if (!jobVO.getIsUpload()) {
        }
        //@TODO 备份文件上传至文件服务器
    }

    /**
     * 执行备份命令(mysql)
     */
    public static BackupRespVO doBackup(BackupJobVO jobVO) {
        // 返回对象
        BackupRespVO respVO = new BackupRespVO();
        try {
            // 当前年月日yyyyMMdd
            String date = DateUtils.format(new Date(),"yyyyMMdd");
            // 文件目录
            String path = PROJECT_PATH + File.separator + "static" + File.separator + date + File.separator;
            // 文件名
            String fileName = IdUtil.fastSimpleUUID() + ".sql" + (jobVO.getIsCompress() ? ".gz" : "");
            // 创建文件
            File file = new File(path, fileName);
            // 路径不存在，则新建
            if (!file.getParentFile().exists()) {
                boolean flag = file.getParentFile().mkdirs();
                if (!flag) {
                    respVO.setMsg("文件夹创建失败");
                    return respVO;
                }
            }
            respVO.setFile(file);
            // shell 命令脚本
            String[] commands = createBackupCommandForMysql(jobVO, path, fileName);

            ProcessBuilder processBuilder = new ProcessBuilder();
            Process process = processBuilder.command(commands).start();
            //Process process = Runtime.getRuntime().exec(commands);
            // 创建一个线程类来不停地来读出Process调用脚本的输出数据，防止缓冲区被缓冲数据塞满而线程阻塞
            new CleanInputCache(process.getInputStream(), "info", CHAR_SET, null).start();
            // 错误信息
            StringBuilder msg = new StringBuilder("【" + jobVO.getDatabaseName() + "】备份失败，原因：");
            new CleanInputCache(process.getErrorStream(), "error", CHAR_SET, msg).start();
            // 备份成功
            if (process.waitFor() == 0) {
                respVO.setMsg("备份成功");
            }
            // 备份失败
            else {
                respVO.setMsg(msg.toString());
            }
        } catch (Exception e) {
            respVO.setMsg("【" + jobVO.getDatabaseName() + "】备份失败，原因：" + e.getMessage());
        }
        return respVO;
    }

    /**
     * 创建命令头(只支持Windows平台和Linux平台)
     */
    public static String[] createBaseCommand() {
        // shell 命令
        String[] commands = new String[3];
        if (OS_NAME.startsWith("Win")) {
            commands[0] = "cmd.exe";
            commands[1] = "/c";
        } else {
            commands[0] = "/bin/sh";
            commands[1] = "-c";
        }
        return commands;
    }

    /**
     * 拼接备份命令(mysql)
     *
     * @param jobVO    备份参数
     * @param path     备份文件目录
     * @param fileName 备份文件名
     */
    public static String[] createBackupCommandForMysql(BackupJobVO jobVO, String path, String fileName) {
        String[] commands = createBaseCommand();
        // 拼接命令
        StringBuilder mysqldump = new StringBuilder();
        //需配置mysql安装路径bin目录环境变量
        mysqldump.append("mysqldump");
        mysqldump.append(" --opt");

        // 用户，密码
        mysqldump.append(" --user=").append(jobVO.getUsername());
        mysqldump.append(" --password=\"").append(jobVO.getPassword()).append("\"");

        // ip，端口
        mysqldump.append(" --host=").append(jobVO.getHost());
        mysqldump.append(" --port=").append(jobVO.getPort());

        // 使用的连接协议，包括：tcp, socket, pipe, memory
        mysqldump.append(" --protocol=tcp");

        // 设置默认字符集，默认值为utf8
        mysqldump.append(" --default-character-set=utf8");
        // 在导出数据之前提交一个BEGIN SQL语句，BEGIN 不会阻塞任何应用程序且能保证导出时数据库的一致性状态
        mysqldump.append(" --single-transaction=TRUE");

        // 导出存储过程以及自定义函数
        mysqldump.append(" --routines");
        // 导出事件
        mysqldump.append(" --events");

        // 只备份表结构
        if (null != jobVO.getDataType()) {
            if (0 == jobVO.getDataType()) {
                mysqldump.append(" --no-data");
            }
            // 只备份表数据
            else if (1 == jobVO.getDataType()) {
                mysqldump.append(" --no-create-info");
            }
        }

        // 数据库名
        mysqldump.append(" ").append(jobVO.getDatabaseName());

        // 数据表名
        if (null != jobVO.getTables() && 0 < jobVO.getTables().length) {
            for (String item : jobVO.getTables()) {
                mysqldump.append(" ").append(item);
            }
        }

        // 保存文件路径
        if (jobVO.getIsCompress()) {
            // gzip压缩
            mysqldump.append(" | gzip");
        }
        mysqldump.append(" > ").append(path).append(fileName);

        commands[2] = mysqldump.toString();
        return commands;
    }

    /**
     * 拼接备份命令(oracle)
     * @param jobVO    备份参数
     * @param path     备份文件目录
     * @param fileName 备份文件名
     */
    public static String[] createBackupCommandForOracle(BackupJobVO jobVO, String path, String fileName) {

        String[] commands = createBaseCommand();
        // 拼接命令
        StringBuilder oracledump = new StringBuilder();
        //需配置mysql安装路径bin目录环境变量
        oracledump.append("expdp ");

        // 用户，密码
        oracledump.append(jobVO.getUsername());
        oracledump.append("/").append(jobVO.getPassword());

        //数据库名
        oracledump.append("@").append(jobVO.getDatabaseName());
        //@TODO 待完善

        return commands;
    }

}
