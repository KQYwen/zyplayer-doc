package com.zyplayer.doc.db.framework.db.job;

import com.alibaba.fastjson.JSONObject;
import com.zyplayer.doc.data.repository.manage.entity.BackupLog;
import com.zyplayer.doc.db.framework.db.vo.BackupJobVO;
import com.zyplayer.doc.db.framework.utils.DatabaseBackupUtils;
import com.zyplayer.doc.db.framework.utils.QuartzManagerUtils;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 数据库备份定时任务执行
 *
 * @author diantu
 * @since 2023年2月8日
 */
public class BackupJob implements Job {

    @Autowired
    private DatabaseBackupUtils databaseBackupUtils;


    @Override
    public void execute(JobExecutionContext context) {
        // 解析参数
        BackupJobVO jobVO = JSONObject.parseObject(context.getJobDetail().getJobDataMap().getString(QuartzManagerUtils.PARAM_KEY), BackupJobVO.class);
        // TODO 保存备份记录
        BackupLog backupLog = new BackupLog();
        //开始备份
        databaseBackupUtils.saveBackUp(jobVO, backupLog);
    }
}
