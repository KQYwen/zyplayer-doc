package com.zyplayer.doc.data.repository.manage.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zyplayer.doc.data.repository.manage.entity.WikiPage;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 暮光：城中城
 * @since 2019-03-05
 */
public interface WikiPageMapper extends BaseMapper<WikiPage> {
	
	@Update("update wiki_page set zan_num=zan_num + #{numAdd} where id=#{id}")
	void updateZanNum(@Param("id") Long id, @Param("numAdd") Integer numAdd);
}
