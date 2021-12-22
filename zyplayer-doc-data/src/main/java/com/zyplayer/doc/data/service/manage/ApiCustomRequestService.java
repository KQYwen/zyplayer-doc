package com.zyplayer.doc.data.service.manage;

import com.zyplayer.doc.data.repository.manage.entity.ApiCustomRequest;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zyplayer.doc.data.repository.manage.vo.ApiCustomVo;

import java.util.List;

/**
 * <p>
 * 自建接口文档 服务类
 * </p>
 *
 * @author 暮光：城中城
 * @since 2021-12-22
 */
public interface ApiCustomRequestService extends IService<ApiCustomRequest> {
	
	/**
	 * 构建目录树
	 *
	 * @author 暮光：城中城
	 * @since 2021-12-22
	 */
	List<ApiCustomVo> buildCustomApiList(Long docId);
}
