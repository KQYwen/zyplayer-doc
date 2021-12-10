package com.zyplayer.doc.db.service.common;

import com.zyplayer.doc.core.exception.ConfirmException;
import com.zyplayer.doc.data.config.security.DocUserUtil;
import com.zyplayer.doc.data.repository.support.consts.DocAuthConst;
import com.zyplayer.doc.data.repository.support.consts.DocSysModuleType;
import com.zyplayer.doc.data.repository.support.consts.DocSysType;
import com.zyplayer.doc.db.framework.consts.DbAuthType;
import com.zyplayer.doc.db.framework.db.mapper.base.ExecuteType;
import org.springframework.stereotype.Service;

/**
 * 执行权限判断类
 *
 * @author 暮光：城中城
 * @since 2021-08-14
 */
@Service
public class ExecuteAuthService {
	
	/**
	 * 获取数据库语句执行类型，只读或读写
	 *
	 * @author 暮光：城中城
	 * @since 2021-08-14
	 */
	public ExecuteType getExecuteType(Long sourceId) {
		boolean manageAuth = DocUserUtil.haveAuth(DocAuthConst.DB_DATASOURCE_MANAGE);
		boolean select = DocUserUtil.haveCustomAuth(DbAuthType.SELECT.getName(), DocSysType.DB.getType(), DocSysModuleType.Db.DATASOURCE.getType(), sourceId);
		boolean update = DocUserUtil.haveCustomAuth(DbAuthType.UPDATE.getName(), DocSysType.DB.getType(), DocSysModuleType.Db.DATASOURCE.getType(), sourceId);
		if (!manageAuth && !select && !update) {
			throw new ConfirmException("没有该数据源的执行权限");
		}
		return (manageAuth || update) ? ExecuteType.ALL : ExecuteType.SELECT;
	}
}
