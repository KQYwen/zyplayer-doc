package com.zyplayer.doc.data.service.manage;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zyplayer.doc.data.repository.manage.entity.WikiPageTemplate;
import com.zyplayer.doc.data.repository.manage.vo.WikiPageTemplateInfoVo;
import com.zyplayer.doc.data.repository.manage.vo.WikiTemplateTagVo;

import java.util.List;

/**
 * <p>
 *     模板服务接口
 * </p>
 *
 * @author Sh1yu
 * @since 2023-08-24
 */
public interface WikiPageTemplateService extends IService<WikiPageTemplate> {

    /**
     * 根据模板的公开情况获取模板标签
     */
    List<WikiTemplateTagVo> getAllTags(Long user, boolean open);

    /**
     * 根据条件获取模板
     */
    WikiPageTemplate getWikiPageTemplateBySpaceAndPage(Long spaceId, Long pageId);


    /**
     * 根据条件获取模板信息
     */
    List<WikiPageTemplateInfoVo> filterAll(Long user, String name, boolean open, List<String> tags, Long pageNum);

    /**
     * 根据条件获取总条数
     */
    Long total(Long user, String name, boolean open, List<String> tags);
}
