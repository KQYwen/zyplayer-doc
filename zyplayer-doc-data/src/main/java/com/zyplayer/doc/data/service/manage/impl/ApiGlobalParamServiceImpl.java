package com.zyplayer.doc.data.service.manage.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zyplayer.doc.data.repository.manage.entity.ApiGlobalParam;
import com.zyplayer.doc.data.repository.manage.mapper.ApiGlobalParamMapper;
import com.zyplayer.doc.data.service.manage.ApiGlobalParamService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * api文档全局参数记录 服务实现类
 * </p>
 *
 * @author 暮光：城中城
 * @since 2021-11-25
 */
@Service
public class ApiGlobalParamServiceImpl extends ServiceImpl<ApiGlobalParamMapper, ApiGlobalParam> implements ApiGlobalParamService {
	
	@Override
	public List<ApiGlobalParam> getGlobalParamList() {
		QueryWrapper<ApiGlobalParam> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("yn", 1);
		return this.list(queryWrapper);
	}
}
