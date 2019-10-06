package com.zyplayer.doc.data.repository.manage.mapper;

import com.zyplayer.doc.data.repository.manage.entity.DbTransferTask;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 暮光：城中城
 * @since 2019-09-30
 */
public interface DbTransferTaskMapper extends BaseMapper<DbTransferTask> {
	
	@Update("update db_transfer_task set last_execute_status=#{status}, last_execute_info = CONCAT(ifnull(last_execute_info,''), #{executeInfo}) where id=#{taskId} and last_execute_status in(0, 1)")
	void addExecuteInfo(@Param("taskId") Long taskId, @Param("status") Integer status, @Param("executeInfo") String executeInfo);
}
