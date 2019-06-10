package com.zyplayer.doc.data.repository.manage.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zyplayer.doc.data.repository.manage.entity.WikiPage;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 暮光：城中城
 * @since 2019-03-09
 */
public interface WikiPageMapper extends BaseMapper<WikiPage> {
	
	@Update("update wiki_page set zan_num=zan_num + #{numAdd} where id=#{id}")
	void updateZanNum(@Param("id") Long id, @Param("numAdd") Integer numAdd);
	
	@Update("update wiki_page set seq_no=seq_no + 1 where parent_id=#{parentId} and seq_no >= #{afterSeq} and del_flag=0")
	void updateAfterSeq(@Param("parentId") Long parentId, @Param("afterSeq") Integer afterSeq);
	
	@Select("select max(seq_no) from wiki_page where parent_id=#{parentId} and del_flag=0")
	Integer getLastSeq(@Param("parentId") Long parentId);
}
