package com.zyplayer.doc.data.service.manage.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zyplayer.doc.data.repository.manage.entity.ApiDoc;
import com.zyplayer.doc.data.repository.manage.mapper.ApiDocMapper;
import com.zyplayer.doc.data.service.manage.ApiDocService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * api文档地址 服务实现类
 * </p>
 *
 * @author 暮光：城中城
 * @since 2021-11-25
 */
@Service
public class ApiDocServiceImpl extends ServiceImpl<ApiDocMapper, ApiDoc> implements ApiDocService {
	
	@Override
	public IPage<ApiDoc> getApiDocList(ApiDoc apiDoc, Integer pageNum, Integer pageSize) {
		QueryWrapper<ApiDoc> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("yn", 1);
		queryWrapper.eq(apiDoc.getDocType() != null, "doc_type", apiDoc.getDocType());
		queryWrapper.eq(apiDoc.getOpenVisit() != null, "open_visit", apiDoc.getOpenVisit());
		queryWrapper.eq(apiDoc.getDocStatus() != null, "doc_status", apiDoc.getDocStatus());
		queryWrapper.orderByAsc("id");
		queryWrapper.select("id", "name", "doc_type", "doc_url", "rewrite_domain", "open_visit", "doc_status", "create_user_id", "create_user_name", "create_time");
		IPage<ApiDoc> page = new Page<>(pageNum, pageSize);
		return this.page(page, queryWrapper);
	}
}
