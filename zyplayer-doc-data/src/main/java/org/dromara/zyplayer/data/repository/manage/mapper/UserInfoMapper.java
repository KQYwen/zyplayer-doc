package org.dromara.zyplayer.data.repository.manage.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.dromara.zyplayer.data.repository.manage.entity.UserInfo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 暮光：城中城
 * @since 2018-12-03
 */
public interface UserInfoMapper extends BaseMapper<UserInfo> {
	
	@Select(value = "show tables")
	@Select(value = "SELECT table_name FROM information_schema.tables where table_schema = 'zyplayer_doc'", databaseId="postgresql")
	List<String> getTableList();
	
	@Select("${sql}")
	List<String> executeSql(@Param("sql") String sql);
	
	@Select("SHOW COLUMNS FROM ${tableName}")
	@Select(value = "SELECT * FROM information_schema.columns WHERE table_name = #{tableName} and table_schema = 'zyplayer_doc'", databaseId="postgresql")
	List<Map<String, Object>> getTableColumnList(@Param("tableName") String tableName);
	
	@Select("SHOW INDEX FROM ${tableName}")
	@Select(value = "SELECT * FROM pg_catalog.pg_indexes WHERE tablename = #{tableName} and schemaname = 'zyplayer_doc'", databaseId="postgresql")
	List<Map<String, Object>> getTableIndexList(@Param("tableName") String tableName);
}
