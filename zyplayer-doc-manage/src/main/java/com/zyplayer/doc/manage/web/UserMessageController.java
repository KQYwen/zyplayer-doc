package com.zyplayer.doc.manage.web;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zyplayer.doc.core.annotation.AuthMan;
import com.zyplayer.doc.core.json.DocResponseJson;
import com.zyplayer.doc.core.json.ResponseJson;
import com.zyplayer.doc.data.config.security.DocUserDetails;
import com.zyplayer.doc.data.config.security.DocUserUtil;
import com.zyplayer.doc.data.repository.manage.entity.UserMessage;
import com.zyplayer.doc.data.service.manage.UserMessageService;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Arrays;

/**
 * 用户消息控制器
 *
 * @author 暮光：城中城
 * @since 2020年6月25日
 */
@AuthMan
@RestController
@RequestMapping("/user/message")
public class UserMessageController {
	
	@Resource
	UserMessageService userMessageService;
	
	/**
	 * 消息列表
	 *
	 * @param pageNum   当前页
	 * @param pageSize  每页条数
	 * @param sysType 系统类型
	 * @param msgStatus 消息状态
	 * @return 数据列表
	 */
	@PostMapping("/list")
	public ResponseJson<Object> list(Long pageNum, Long pageSize, Integer sysType, Integer msgStatus) {
		DocUserDetails currentUser = DocUserUtil.getCurrentUser();
		IPage<UserMessage> page = new Page<>(pageNum, pageSize);
		QueryWrapper<UserMessage> wrapper = new QueryWrapper<>();
		wrapper.eq("accept_user_id", currentUser.getUserId());
		wrapper.eq(sysType != null, "sys_type", sysType);
		wrapper.orderByAsc("msg_status").orderByDesc("creation_time");
//		if (msgStatus != null && msgStatus >= 0) {
//			wrapper.eq("msg_status", msgStatus);
//		}
		wrapper.notIn("msg_status", 2);
		userMessageService.page(page, wrapper);
		return DocResponseJson.ok(page);
	}
	
	/**
	 * 更新消息已读状态
	 *
	 * @param ids 消息IDS
	 * @return 是否成功
	 */
	@PostMapping("/read")
	public ResponseJson<Object> read(String ids) {
		this.update(ids, 1);
		return DocResponseJson.ok();
	}
	
	/**
	 * 删除消息
	 *
	 * @param ids 消息IDS
	 * @return 是否成功
	 */
	@PostMapping("/delete")
	public ResponseJson<Object> delete(String ids) {
		this.update(ids, 2);
		return DocResponseJson.ok();
	}
	
	/**
	 * 更新消息状态
	 *
	 * @param ids    消息IDS
	 * @param status 状态
	 */
	public void update(String ids, Integer status) {
		if (StringUtils.isBlank(ids)) {
			return;
		}
		DocUserDetails currentUser = DocUserUtil.getCurrentUser();
		QueryWrapper<UserMessage> wrapper = new QueryWrapper<>();
		wrapper.in("id", Arrays.asList(ids.split(",")));
		wrapper.eq("accept_user_id", currentUser.getUserId());
		UserMessage msgUp = new UserMessage();
		msgUp.setMsgStatus(status);
		userMessageService.update(msgUp, wrapper);
	}
}
