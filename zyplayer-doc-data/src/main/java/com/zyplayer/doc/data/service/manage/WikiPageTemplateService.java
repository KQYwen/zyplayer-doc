package com.zyplayer.doc.data.service.manage;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zyplayer.doc.data.repository.manage.entity.WikiPage;
import com.zyplayer.doc.data.repository.manage.entity.WikiPageTemplate;
import com.zyplayer.doc.data.repository.manage.vo.WikiPageTemplateInfoVo;
import com.zyplayer.doc.data.repository.manage.vo.WikiTemplateTagVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author 暮光：城中城
 * @since 2019-03-09
 */
public interface WikiPageTemplateService extends IService<WikiPageTemplate> {
    public List<WikiTemplateTagVo> getAllTags( Long user, boolean open);

    WikiPageTemplate getWikiPageTemplateBySpaceAndPage(Long spaceId, Long pageId);

    List<WikiPageTemplateInfoVo> filterAll(Long user, String name, boolean open, List<String> tags, Long pageNum);

    Long total(Long user, String name, boolean open, List<String> tags);
}
