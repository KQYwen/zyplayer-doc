package com.zyplayer.doc.data.service.manage;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zyplayer.doc.data.repository.manage.entity.ApiDoc;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * api文档地址 服务类
 * </p>
 *
 * @author 暮光：城中城
 * @since 2021-11-25
 */
public interface ApiDocService extends IService<ApiDoc> {
	IPage<ApiDoc> getApiDocList(ApiDoc apiDoc, Integer pageNum, Integer pageSize);
	
	ApiDoc getByShareUuid(String shareUuid);
}
