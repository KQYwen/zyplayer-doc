package com.zyplayer.doc.data.repository.manage.mapper;

import com.zyplayer.doc.data.repository.manage.entity.ApiCustomNode;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * <p>
 * 自建接口文档节点 Mapper 接口
 * </p>
 *
 * @author 暮光：城中城
 * @since 2022-01-29
 */
public interface ApiCustomNodeMapper extends BaseMapper<ApiCustomNode> {
	
	@Update("update api_custom_node set seq_no=seq_no + 1 where parent_id=#{parentId} and seq_no >= #{targetSeq} and yn=1")
	void updateAfterSeq(@Param("parentId") Long parentId, @Param("targetSeq") Integer targetSeq);
	
	@Select("select max(seq_no) from api_custom_node where parent_id=#{parentId} and yn=1")
	Integer getLastSeq(@Param("parentId") Long parentId);
}
