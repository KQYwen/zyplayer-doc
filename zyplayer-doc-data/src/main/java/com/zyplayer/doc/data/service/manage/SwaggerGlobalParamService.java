package com.zyplayer.doc.data.service.manage;

import com.zyplayer.doc.data.repository.manage.entity.SwaggerGlobalParam;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * swagger文档全局参数记录 服务类
 * </p>
 *
 * @author 暮光：城中城
 * @since 2021-10-15
 */
public interface SwaggerGlobalParamService extends IService<SwaggerGlobalParam> {
	public List<SwaggerGlobalParam> getGlobalParamList();
}
