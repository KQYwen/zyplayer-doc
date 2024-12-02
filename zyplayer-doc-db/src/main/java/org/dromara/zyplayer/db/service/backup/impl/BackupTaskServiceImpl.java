package org.dromara.zyplayer.db.service.backup.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSON;
import org.dromara.zyplayer.data.repository.manage.entity.BackupTask;
import org.dromara.zyplayer.data.repository.manage.mapper.BackupTaskMapper;
import org.dromara.zyplayer.db.framework.utils.QuartzManagerUtils;
import org.dromara.zyplayer.db.service.backup.BackupTaskService;
import org.quartz.Scheduler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import javax.annotation.PostConstruct;
import java.util.List;

/**
 * 备份任务实现
 *
 * @author diantu
 * @since 2023年3月8日
 */
public class BackupTaskServiceImpl implements BackupTaskService {

    private static final Logger logger = LoggerFactory.getLogger(BackupTaskServiceImpl.class);

    @Autowired
    private Scheduler scheduler;

    @Autowired
    private BackupTaskMapper backupTaskMapper;

    /**
     * 系统启动执行
     */
    @PostConstruct
    public void init() {
        List<BackupTask> list = backupTaskMapper.selectList(null);
        if (CollectionUtil.isNotEmpty(list)) {
            for (BackupTask item : list) {
                try {
                    QuartzManagerUtils.add(scheduler,item.getId(), item.getCron(), JSON.toJSONString(item.getParam()), item.getStatus());
                } catch (Exception e) {
                    logger.error(e.getMessage());
                }
            }
        }
    }

}
