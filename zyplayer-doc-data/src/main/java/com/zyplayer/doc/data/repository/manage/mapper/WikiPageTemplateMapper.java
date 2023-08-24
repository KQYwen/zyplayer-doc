package com.zyplayer.doc.data.repository.manage.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zyplayer.doc.data.repository.manage.entity.WikiPage;
import com.zyplayer.doc.data.repository.manage.entity.WikiPageTemplate;
import com.zyplayer.doc.data.repository.manage.vo.WikiPageTemplateInfoVo;
import com.zyplayer.doc.data.repository.manage.vo.WikiTemplateTagVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author 暮光：城中城
 * @since 2019-03-09
 */
public interface WikiPageTemplateMapper extends BaseMapper<WikiPageTemplate> {

    List<WikiPageTemplateInfoVo> getAllTemplate(@Param("user") Long user, @Param("name") String name, @Param("share") boolean share, @Param("tags") List tags, @Param("pageNum") Long pageNum);

    List<WikiTemplateTagVo> getAllTags(@Param("user") Long user,@Param("open") boolean open);

    Long getAllTemplateCount(@Param("user") Long user, @Param("name") String name, @Param("share") boolean share, @Param("tags") List tags);

}
