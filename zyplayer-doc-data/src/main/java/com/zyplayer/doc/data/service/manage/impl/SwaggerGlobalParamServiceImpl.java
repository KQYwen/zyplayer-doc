package com.zyplayer.doc.data.service.manage.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zyplayer.doc.data.repository.manage.entity.SwaggerGlobalParam;
import com.zyplayer.doc.data.repository.manage.mapper.SwaggerGlobalParamMapper;
import com.zyplayer.doc.data.service.manage.SwaggerGlobalParamService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * swagger文档全局参数记录 服务实现类
 * </p>
 *
 * @author 暮光：城中城
 * @since 2021-10-15
 */
@Service
public class SwaggerGlobalParamServiceImpl extends ServiceImpl<SwaggerGlobalParamMapper, SwaggerGlobalParam> implements SwaggerGlobalParamService {
	
	
	@Override
	public List<SwaggerGlobalParam> getGlobalParamList() {
		QueryWrapper<SwaggerGlobalParam> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("yn", 1);
		return this.list(queryWrapper);
	}
}
