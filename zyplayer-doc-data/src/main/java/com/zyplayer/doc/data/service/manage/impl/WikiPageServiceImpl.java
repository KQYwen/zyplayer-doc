package com.zyplayer.doc.data.service.manage.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zyplayer.doc.data.repository.manage.entity.WikiPage;
import com.zyplayer.doc.data.repository.manage.mapper.WikiPageMapper;
import com.zyplayer.doc.data.service.manage.WikiPageService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Optional;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 暮光：城中城
 * @since 2019-06-05
 */
@Service
public class WikiPageServiceImpl extends ServiceImpl<WikiPageMapper, WikiPage> implements WikiPageService {
	
	@Resource
	WikiPageMapper wikiPageMapper;
	
	@Override
	public void changeParent(WikiPage wikiPage, Integer beforeSeq, Integer afterSeq) {
		if (beforeSeq != null && beforeSeq >= 0) {
			// 在此seq之前
			wikiPageMapper.updateAfterSeq(wikiPage.getParentId(), beforeSeq);
			wikiPage.setSeqNo(beforeSeq);
		} else if (afterSeq != null && afterSeq >= 0) {
			// 在此seq之后
			wikiPageMapper.updateAfterSeq(wikiPage.getParentId(), afterSeq + 1);
			wikiPage.setSeqNo(afterSeq + 1);
		} else {
			// 放在末尾
			Integer lastSeq = wikiPageMapper.getLastSeq(wikiPage.getParentId());
			lastSeq = Optional.ofNullable(lastSeq).orElse(0);
			wikiPage.setSeqNo(lastSeq + 1);
		}
		this.updateById(wikiPage);
	}
}
