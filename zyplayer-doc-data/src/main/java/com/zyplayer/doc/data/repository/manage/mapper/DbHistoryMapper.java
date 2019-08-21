package com.zyplayer.doc.data.repository.manage.mapper;

import com.zyplayer.doc.data.repository.manage.entity.DbHistory;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Update;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 暮光：城中城
 * @since 2019-08-21
 */
public interface DbHistoryMapper extends BaseMapper<DbHistory> {
	
	@Update("delete a from db_history a,(select id from db_history order by id desc limit 100, 1) b where a.id < b.id")
	void deleteHistory();
	
}
