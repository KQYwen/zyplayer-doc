package com.zyplayer.doc.data.service.manage;

import com.zyplayer.doc.data.repository.manage.entity.DbTransferTask;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 暮光：城中城
 * @since 2019-09-30
 */
public interface DbTransferTaskService extends IService<DbTransferTask> {
	
	void addExecuteInfo(Long taskId, Integer status, String executeInfo);
	
	void resetExecuteInfo(Long taskId);
}
