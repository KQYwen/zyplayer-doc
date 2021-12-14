package com.zyplayer.doc.data.service.manage;

import com.zyplayer.doc.data.repository.manage.entity.ApiGlobalParam;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * api文档全局参数记录 服务类
 * </p>
 *
 * @author 暮光：城中城
 * @since 2021-11-25
 */
public interface ApiGlobalParamService extends IService<ApiGlobalParam> {
	
	List<ApiGlobalParam> getGlobalParamList(Long docId);
}
