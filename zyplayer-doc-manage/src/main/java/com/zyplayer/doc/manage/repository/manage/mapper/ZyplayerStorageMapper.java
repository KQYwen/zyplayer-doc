package com.zyplayer.doc.manage.repository.manage.mapper;

import com.zyplayer.doc.manage.repository.manage.entity.ZyplayerStorage;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author 暮光：城中城
 * @since 2018-11-27
 */
public interface ZyplayerStorageMapper extends BaseMapper<ZyplayerStorage> {

	Integer selectTop();

}
