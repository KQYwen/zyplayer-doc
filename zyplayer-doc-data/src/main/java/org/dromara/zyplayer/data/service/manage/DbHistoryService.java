package org.dromara.zyplayer.data.service.manage;

import org.dromara.zyplayer.data.repository.manage.entity.DbHistory;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 暮光：城中城
 * @since 2019-08-21
 */
public interface DbHistoryService extends IService<DbHistory> {
	
	void saveHistory(String content, String paramJson, Long datasourceId);
	
}
