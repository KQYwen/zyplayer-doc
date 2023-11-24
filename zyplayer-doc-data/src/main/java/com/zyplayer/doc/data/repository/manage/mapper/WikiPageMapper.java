package com.zyplayer.doc.data.repository.manage.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zyplayer.doc.data.repository.manage.entity.WikiPage;
import com.zyplayer.doc.data.repository.manage.vo.WikiPageTemplateInfoVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

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
	
	@Update("update wiki_page set seq_no=seq_no + 1 where space_id = #{spaceId} and parent_id=#{parentId} and seq_no >= #{afterSeq} and del_flag=0")
	void updateAfterSeq(@Param("spaceId") Long spaceId, @Param("parentId") Long parentId, @Param("afterSeq") Integer afterSeq);
	
	@Select("select max(seq_no) from wiki_page where space_id = #{spaceId} and parent_id=#{parentId} and del_flag=0")
	Integer getLastSeq(@Param("spaceId") Long spaceId, @Param("parentId") Long parentId);

	@Select("SELECT a.*,b.tag_name AS tags,b.share_status as shareStatus from wiki_page a left join wiki_page_template b on a.space_id =b.space_id and a.id = b.page_id where a.del_flag = 0 and a.space_id =#{spaceId}")
	List<WikiPageTemplateInfoVo> getWikiPageTemplateInfos(@Param("spaceId") Long spaceId);

	void updateChildrenSeq(@Param("spaceId") Long spaceId, @Param("parentId") Long parentId);
}
