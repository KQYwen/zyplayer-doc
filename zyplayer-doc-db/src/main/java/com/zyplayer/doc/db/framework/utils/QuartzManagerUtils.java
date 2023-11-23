package com.zyplayer.doc.db.framework.utils;

import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * quartz工具类
 *
 * @author diantu
 * @since 2023年3月8日
 */
public class QuartzManagerUtils {

    private static final Logger logger = LoggerFactory.getLogger(QuartzManagerUtils.class);

    /**
     * 参数传递key
     */
    public static final String PARAM_KEY = "params";

    /**
     * 执行任务类名
     */
    public static final String CLASS_NAME = "com.zyplayer.doc.db.framework.db.job.BackupJob";




    /**
     * 添加定时任务
     */
    public static void add(Scheduler scheduler,Long id, String cronExpression, String param, Boolean status) {
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
                stop(scheduler,id);
            }
        } catch (Exception e) {
            logger.error("添加定时任务失败：{}", e.getMessage());
        }
    }

    /**
     * 编辑定时任务
     */
    public void update(Scheduler scheduler,Long id, String cronExpression, String param, Boolean status) {
        try {
            // 判断是否存在，存在先删除
            if (scheduler.checkExists(JobKey.jobKey(getKey(id)))) {
                scheduler.deleteJob(JobKey.jobKey(getKey(id)));
            }
            // 再创建
            add(scheduler,id, cronExpression, param, status);
        } catch (Exception e) {
            logger.error("修改定时任务失败：{}", e.getMessage());
        }
    }

    /**
     * 暂停任务
     */
    public static void stop(Scheduler scheduler, Long id) {
        try {
            scheduler.pauseJob(JobKey.jobKey(getKey(id)));
        } catch (SchedulerException e) {
            // 暂停定时任务失败
            logger.error("暂停定时任务失败：{}", e.getMessage());
        }
    }

    /**
     * 恢复任务
     */
    public void start(Scheduler scheduler,Long id) {
        try {
            scheduler.resumeJob(JobKey.jobKey(getKey(id)));
        } catch (SchedulerException e) {
            // 暂停定时任务失败
            logger.error("启动定时任务失败：{}", e.getMessage());
        }
    }

    /**
     * 立即执行一次
     */
    public void run(Scheduler scheduler,Long id) {
        try {
            scheduler.triggerJob(JobKey.jobKey(getKey(id)));
        } catch (SchedulerException e) {
            // 暂停定时任务失败
            logger.error("执行定时任务失败：{}", e.getMessage());
        }
    }

    /**
     * 删除定时任务
     */
    public void delete(Scheduler scheduler,Long id) {
        try {
            // 停止触发器
            scheduler.pauseTrigger(TriggerKey.triggerKey(getKey(id)));
            // 移除触发器
            scheduler.unscheduleJob(TriggerKey.triggerKey(getKey(id)));
            // 删除任务
            scheduler.deleteJob(JobKey.jobKey(getKey(id)));
        } catch (Exception e) {
            logger.error("删除定时任务失败：{}", e.getMessage());
        }
    }

    /**
     * 根据类名获取类
     */
    private static Job getClass(String className) throws Exception {
        Class<?> class1 = Class.forName(className);
        return (Job) class1.newInstance();
    }

    /**
     * 拼接key
     */
    public static String getKey(Long id) {
        return "dbBackUp-" + id;
    }
}
