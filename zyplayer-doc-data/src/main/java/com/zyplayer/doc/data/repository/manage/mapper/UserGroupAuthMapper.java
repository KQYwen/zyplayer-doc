package com.zyplayer.doc.data.repository.manage.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zyplayer.doc.data.repository.manage.entity.UserGroupAuth;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 * 用户组在各项目内的授权关系 Mapper 接口
 * </p>
 *
 * @author 暮光：城中城
 * @since 2021-02-09
 */
public interface UserGroupAuthMapper extends BaseMapper<UserGroupAuth> {
	
	@Select("select a.id from user_group_auth a join user_group_relation b on a.group_id=b.group_id and b.user_id=#{userId} " +
			"where a.project_type=#{projectType} and a.auth_type=#{authType} and a.data_id=#{spaceId} and b.del_flag=0")
	Long haveAuth(Long spaceId, Integer projectType, Integer authType, Long userId);
}
