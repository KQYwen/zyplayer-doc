package com.zyplayer.doc.db.framework.utils;

import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * quartz工具类
 *
 * @author diantu
 * @since 2023年3月8日
 */
@Slf4j
@Component
public class QuartzManagerUtils {

    /**
     * 参数传递key
     */
    public static final String PARAM_KEY = "params";

    /**
     * 执行任务类名
     */
    public static final String CLASS_NAME = "com.zyplayer.doc.db.framework.db.job.BackupJob";

    /**
     * 程序调度器
     */
    @Autowired
    private Scheduler scheduler;




    /**
     * 添加定时任务
     */
    public void add(Integer id, String cronExpression, String param, Boolean status) {
        try {
            // 构建job信息
            JobDetail jobDetail = JobBuilder.newJob(getClass(CLASS_NAME).getClass()).withIdentity(getKey(id)).usingJobData(PARAM_KEY, param).build();
            // 表达式调度构建器(即任务执行的时间)
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(cronExpression);
            // 按新的cronExpression表达式构建一个新的trigger
            CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(getKey(id)).withSchedule(scheduleBuilder).build();
            // 创建定时任务
            scheduler.scheduleJob(jobDetail, trigger);
            // 停止
            if (!status) {
                stop(id);
            }
        } catch (Exception e) {
            log.error("添加定时任务失败：{}", e.getMessage());
        }
    }

    /**
     * 编辑定时任务
     */
    public void update(Integer id, String cronExpression, String param, Boolean status) {
        try {
            // 判断是否存在，存在先删除
            if (scheduler.checkExists(JobKey.jobKey(getKey(id)))) {
                scheduler.deleteJob(JobKey.jobKey(getKey(id)));
            }
            // 再创建
            add(id, cronExpression, param, status);
        } catch (Exception e) {
            log.error("修改定时任务失败：{}", e.getMessage());
        }
    }

    /**
     * 暂停任务
     */
    public void stop(Integer id) {
        try {
            scheduler.pauseJob(JobKey.jobKey(getKey(id)));
        } catch (SchedulerException e) {
            // 暂停定时任务失败
            log.error("暂停定时任务失败：{}", e.getMessage());
        }
    }

    /**
     * 恢复任务
     */
    public void start(Integer id) {
        try {
            scheduler.resumeJob(JobKey.jobKey(getKey(id)));
        } catch (SchedulerException e) {
            // 暂停定时任务失败
            log.error("启动定时任务失败：{}", e.getMessage());
        }
    }

    /**
     * 立即执行一次
     */
    public void run(Integer id) {
        try {
            scheduler.triggerJob(JobKey.jobKey(getKey(id)));
        } catch (SchedulerException e) {
            // 暂停定时任务失败
            log.error("执行定时任务失败：{}", e.getMessage());
        }
    }

    /**
     * 删除定时任务
     */
    public void delete(Integer id) {
        try {
            // 停止触发器
            scheduler.pauseTrigger(TriggerKey.triggerKey(getKey(id)));
            // 移除触发器
            scheduler.unscheduleJob(TriggerKey.triggerKey(getKey(id)));
            // 删除任务
            scheduler.deleteJob(JobKey.jobKey(getKey(id)));
        } catch (Exception e) {
            log.error("删除定时任务失败：{}", e.getMessage());
        }
    }

    /**
     * 根据类名获取类
     */
    private Job getClass(String className) throws Exception {
        Class<?> class1 = Class.forName(className);
        return (Job) class1.newInstance();
    }

    /**
     * 拼接key
     */
    public String getKey(Integer id) {
        return "dbBackUp-" + id;
    }
}
