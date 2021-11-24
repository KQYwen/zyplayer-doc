package com.zyplayer.doc.manage.web;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zyplayer.doc.core.annotation.AuthMan;
import com.zyplayer.doc.core.json.DocResponseJson;
import com.zyplayer.doc.core.json.ResponseJson;
import com.zyplayer.doc.data.config.security.DocUserDetails;
import com.zyplayer.doc.data.config.security.DocUserUtil;
import com.zyplayer.doc.data.repository.manage.entity.UserGroup;
import com.zyplayer.doc.data.repository.manage.entity.UserGroupRelation;
import com.zyplayer.doc.data.repository.manage.entity.UserInfo;
import com.zyplayer.doc.data.repository.manage.mapper.UserGroupMapper;
import com.zyplayer.doc.data.repository.support.consts.DocAuthConst;
import com.zyplayer.doc.data.service.manage.UserGroupRelationService;
import com.zyplayer.doc.data.service.manage.UserGroupService;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 用户分组控制器
 *
 * @author 暮光：城中城
 * @since 2021-02-09
 */
@RestController
@RequestMapping("/user/group")
public class UserGroupController {
	
	@Resource
	UserGroupService userGroupService;
	@Resource
	UserGroupMapper userGroupMapper;
	@Resource
	UserGroupRelationService userGroupRelationService;
	
	@AuthMan
	@PostMapping("/list")
	public ResponseJson<Object> list() {
		QueryWrapper<UserGroup> wrapper = new QueryWrapper<>();
		wrapper.select("id", "name");
		wrapper.eq("del_flag", 0);
		List<UserGroup> userGroupList = userGroupService.list(wrapper);
		return DocResponseJson.ok(userGroupList);
	}
	
	@PostMapping("/update")
	@AuthMan(DocAuthConst.USER_MANAGE)
	public ResponseJson<Object> update(Long id, String name) {
		if (StringUtils.isBlank(name)) {
			return DocResponseJson.warn("分组名不能为空");
		}
		UserGroup userGroup = new UserGroup();
		userGroup.setId(id);
		userGroup.setName(name);
		if (userGroup.getId() == null) {
			DocUserDetails currentUser = DocUserUtil.getCurrentUser();
			userGroup.setCreateTime(new Date());
			userGroup.setCreateUserId(currentUser.getUserId());
			userGroup.setCreateUserName(currentUser.getUsername());
		}
		userGroupService.saveOrUpdate(userGroup);
		return DocResponseJson.ok();
	}
	
	@PostMapping("/delete")
	@AuthMan(DocAuthConst.USER_MANAGE)
	public ResponseJson<Object> delete(Long id) {
		UserGroup userGroupUp = new UserGroup();
		userGroupUp.setId(id);
		userGroupUp.setDelFlag(1);
		userGroupService.updateById(userGroupUp);
		return DocResponseJson.ok();
	}
	
	@PostMapping("/relation/update")
	@AuthMan(DocAuthConst.USER_MANAGE)
	public ResponseJson<Object> relationUpdate(Long groupId, Long userId) {
		QueryWrapper<UserGroupRelation> wrapper = new QueryWrapper<>();
		wrapper.eq("group_id", groupId).eq("user_id", userId);
		UserGroupRelation userGroupRelation = userGroupRelationService.getOne(wrapper);
		if (userGroupRelation == null) {
			DocUserDetails currentUser = DocUserUtil.getCurrentUser();
			userGroupRelation = new UserGroupRelation();
			userGroupRelation.setCreateTime(new Date());
			userGroupRelation.setCreateUserId(currentUser.getUserId());
			userGroupRelation.setCreateUserName(currentUser.getUsername());
			userGroupRelation.setGroupId(groupId);
			userGroupRelation.setUserId(userId);
		}
		userGroupRelation.setDelFlag(0);
		userGroupRelationService.saveOrUpdate(userGroupRelation);
		return DocResponseJson.ok();
	}
	
	@PostMapping("/relation/remove")
	@AuthMan(DocAuthConst.USER_MANAGE)
	public ResponseJson<Object> relationRemove(Long groupId, Long userId) {
		QueryWrapper<UserGroupRelation> wrapper = new QueryWrapper<>();
		wrapper.eq("group_id", groupId).eq("user_id", userId);
		UserGroupRelation relationUp = new UserGroupRelation();
		relationUp.setDelFlag(1);
		userGroupRelationService.update(relationUp, wrapper);
		return DocResponseJson.ok();
	}
	
	@PostMapping("/relation/list")
	@AuthMan(DocAuthConst.USER_MANAGE)
	public ResponseJson<Object> groupUserList(Long groupId) {
		List<UserInfo> userInfoList = userGroupMapper.groupUserList(groupId);
		return DocResponseJson.ok(userInfoList);
	}
	
}
