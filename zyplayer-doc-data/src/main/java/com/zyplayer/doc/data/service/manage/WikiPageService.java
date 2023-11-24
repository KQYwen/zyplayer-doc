package com.zyplayer.doc.data.service.manage;

import com.zyplayer.doc.data.repository.manage.entity.WikiPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zyplayer.doc.data.repository.manage.vo.WikiPageTemplateInfoVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 暮光：城中城
 * @since 2019-03-09
 */
public interface WikiPageService extends IService<WikiPage> {

	void changeParent(WikiPage wikiPage, Integer beforeSeq, Integer afterSeq);
	
	void deletePage(WikiPage wikiPage);

	List<WikiPageTemplateInfoVo> wikiPageTemplateInfos(Long spaceId);
}
