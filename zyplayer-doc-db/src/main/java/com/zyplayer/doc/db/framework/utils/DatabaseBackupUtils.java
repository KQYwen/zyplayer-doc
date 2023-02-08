package com.zyplayer.doc.db.framework.utils;

import java.io.*;
import java.time.LocalDate;

/**
 * 数据库备份恢复工具类
 *
 * @author diantu
 * @since 2023年2月8日
 */
public class DatabaseBackupUtils {

    /**
     * MySQL数据库导出
     *
     * @param hostIP MySQL数据库所在服务器地址IP
     * @param userName 进入数据库所需要的用户名
     * @param password 进入数据库所需要的密码
     * @param savePath 数据库导出文件保存路径
     * @param fileName 数据库导出文件文件名
     * @param databaseName 要导出的数据库名
     * @return 返回true表示导出成功，否则返回false。
     */
    public static boolean exportDatabaseForMysql(String mysqldumpPath, String hostIP, String userName, String password, String savePath, String fileName, String databaseName) throws InterruptedException {
        File saveFile = new File(savePath);
        if (!saveFile.exists()) {// 如果目录不存在
            saveFile.mkdirs();// 创建文件夹
        }
        if(!savePath.endsWith(File.separator)){
            savePath = savePath + File.separator;
        }

        PrintWriter printWriter = null;
        BufferedReader bufferedReader = null;
        try {

            printWriter = new PrintWriter(new OutputStreamWriter(new FileOutputStream(savePath + fileName), "utf8"));
            Process process = Runtime.getRuntime().exec(" "+mysqldumpPath+"/mysqldump -h" + hostIP + " -u" + userName + " -p" + password + " " + databaseName);
            InputStreamReader inputStreamReader = new InputStreamReader(process.getInputStream(), "utf8");
            bufferedReader = new BufferedReader(inputStreamReader);
            String line;
            while((line = bufferedReader.readLine())!= null){
                printWriter.println(line);
            }
            printWriter.flush();
            if(process.waitFor() == 0){//0 表示线程正常终止。
                return true;
            }
        }catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
                if (printWriter != null) {
                    printWriter.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public static void backup(String savePath) {
        File file = new File(savePath);
        if (!file.exists()) {
            file.mkdirs();
        }
        String fileName = savePath + "/" + LocalDate.now() + ".sql";
        /** 默认使用linux*/
        //String cmdPrefix = "/bin/sh -c ";
        String c1 = "/bin/sh";
        String c2 = "-c";
        String os_name = System.getProperty("os.name");
        // 判断是否是windows系统
        if (os_name.toLowerCase().startsWith("win")){
            //cmdPrefix = "cmd /c ";
            c1 = "cmd";
            c2 = "/c";
        }
        //参考示例：# /usr/local/mysql/bin/mysqldump -uroot -p123456 -P3306 shuju > shuju.sql
        String cmd = "mysqldump" // mysqldump的绝对路径，配置环境变量，直接写mysqldump即可
                + " -h" + "127.0.0.1"      // 数据库端口号
                + " -P" + "3306"      // 数据库端口号
                + " -u" + "root"  // 数据库用户名
                + " -p" + "root" // 数据库密码
                + " " + "zyplayer_doc_manage"      // 数据库名
                + " > " + fileName;                   // 最终写入的文件路径
        try {
            System.out.println("第一个参数 " + c1);
            System.out.println("第二个参数 " + c2);
            System.out.println("具体命令 " + cmd);

            //log.error("数据库备份START" + LocalDateTime.now());
            /**
             * exec重载方法有一个参数的，window下执行正常，linux下无法完成备份。
             * 使用多参数重载方法都可以正常备份
             */
            Process process = Runtime.getRuntime().exec(new String[]{c1, c2, cmd});
            process.waitFor();
            //log.error("数据库备份END" + LocalDateTime.now());
        } catch (Exception e) {
            e.printStackTrace();
            //log.error("数据库备份失败:{}", e.getMessage());
        }
    }


    public static void main(String[] args){
        try {
            if (exportDatabaseForMysql("C:/Program Files/MySQL/MySQL Server 5.7/bin","127.0.0.1", "root", "root", "D:/backupDatabase", "2023-2-8.sql", "zyplayer_doc_manage")) {
                System.out.println("数据库成功备份！！！");
            } else {
                System.out.println("数据库备份失败！！！");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //backup("D:/backupDatabase");
    }
}
