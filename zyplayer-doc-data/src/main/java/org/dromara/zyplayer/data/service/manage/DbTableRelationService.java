package org.dromara.zyplayer.data.service.manage;

import org.dromara.zyplayer.data.repository.manage.entity.DbTableRelation;
import com.baomidou.mybatisplus.extension.service.IService;
import org.dromara.zyplayer.data.repository.manage.param.TableRelationParam;

/**
 * <p>
 * 表关系 服务类
 * </p>
 *
 * @author 暮光：城中城
 * @since 2021-06-07
 */
public interface DbTableRelationService extends IService<DbTableRelation> {
	
	void update(TableRelationParam param);
	
}
