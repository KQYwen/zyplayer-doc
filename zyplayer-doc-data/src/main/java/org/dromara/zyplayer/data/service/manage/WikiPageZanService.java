package org.dromara.zyplayer.data.service.manage;

import org.dromara.zyplayer.data.repository.manage.entity.WikiPageZan;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 暮光：城中城
 * @since 2019-03-05
 */
public interface WikiPageZanService extends IService<WikiPageZan> {
	void zanPage(WikiPageZan wikiPageZan);
}
