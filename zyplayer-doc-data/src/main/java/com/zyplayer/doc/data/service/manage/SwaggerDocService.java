package com.zyplayer.doc.data.service.manage;

import com.zyplayer.doc.data.repository.manage.entity.SwaggerDoc;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * swagger文档地址 服务类
 * </p>
 *
 * @author 暮光：城中城
 * @since 2021-10-15
 */
public interface SwaggerDocService extends IService<SwaggerDoc> {
	public List<SwaggerDoc> getSwaggerDocList(SwaggerDoc swaggerDoc);
}
