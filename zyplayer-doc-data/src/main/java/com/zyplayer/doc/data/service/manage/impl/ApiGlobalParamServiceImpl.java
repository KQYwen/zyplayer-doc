package com.zyplayer.doc.data.service.manage.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zyplayer.doc.data.config.security.DocUserDetails;
import com.zyplayer.doc.data.config.security.DocUserUtil;
import com.zyplayer.doc.data.repository.manage.entity.ApiGlobalParam;
import com.zyplayer.doc.data.repository.manage.mapper.ApiGlobalParamMapper;
import com.zyplayer.doc.data.service.manage.ApiGlobalParamService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
	public List<ApiGlobalParam> getGlobalParamList(Long docId) {
		DocUserDetails currentUser = DocUserUtil.getCurrentUser();
		Long docIdNew = Optional.ofNullable(docId).orElse(0L);
		
		QueryWrapper<ApiGlobalParam> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("yn", 1);
		// 全局参数才按创建人来控制，文档的全局参数大家共用
		if (docIdNew == 0) {
			queryWrapper.eq("create_user_id", currentUser.getUserId());
		} else {
			queryWrapper.and(con -> con.or(or -> or.eq("doc_id", docIdNew))
					.or(or -> or.eq("create_user_id", currentUser.getUserId())));
		}
		return this.list(queryWrapper);
	}
}
