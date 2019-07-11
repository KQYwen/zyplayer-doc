package com.zyplayer.doc.data.repository.manage.mapper;

import com.zyplayer.doc.data.repository.manage.entity.WikiPageContent;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zyplayer.doc.data.repository.manage.param.SearchByEsParam;
import com.zyplayer.doc.data.repository.manage.vo.SpaceNewsVo;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 暮光：城中城
 * @since 2019-02-24
 */
public interface WikiPageContentMapper extends BaseMapper<WikiPageContent> {
	
	List<SpaceNewsVo> getNewsList(SearchByEsParam param);
}
