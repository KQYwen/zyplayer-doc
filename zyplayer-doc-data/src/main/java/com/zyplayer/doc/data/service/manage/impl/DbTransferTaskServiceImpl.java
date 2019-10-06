package com.zyplayer.doc.data.service.manage.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zyplayer.doc.data.repository.manage.entity.DbTransferTask;
import com.zyplayer.doc.data.repository.manage.mapper.DbTransferTaskMapper;
import com.zyplayer.doc.data.service.manage.DbTransferTaskService;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author 暮光：城中城
 * @since 2019-09-30
 */
@Service
public class DbTransferTaskServiceImpl extends ServiceImpl<DbTransferTaskMapper, DbTransferTask> implements DbTransferTaskService {
	
	@Override
	public void addExecuteInfo(Long taskId, Integer status, String executeInfo) {
		executeInfo = "> " + executeInfo + "\n";
		baseMapper.addExecuteInfo(taskId, status, executeInfo);
	}
	
	@Override
	public void resetExecuteInfo(Long taskId) {
		DbTransferTask taskUpdate = new DbTransferTask();
		taskUpdate.setId(taskId);
		taskUpdate.setLastExecuteInfo("");
		taskUpdate.setLastExecuteTime(new Date());
		taskUpdate.setLastExecuteStatus(0);
		this.updateById(taskUpdate);
	}
}
