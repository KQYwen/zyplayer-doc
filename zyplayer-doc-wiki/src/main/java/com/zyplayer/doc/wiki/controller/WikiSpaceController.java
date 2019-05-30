package com.zyplayer.doc.wiki.controller;

import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.zyplayer.doc.core.json.DocResponseJson;
import com.zyplayer.doc.core.json.ResponseJson;
import com.zyplayer.doc.data.aspect.AuthMan;
import com.zyplayer.doc.data.config.security.DocUserDetails;
import com.zyplayer.doc.data.config.security.DocUserUtil;
import com.zyplayer.doc.data.repository.manage.entity.WikiSpace;
import com.zyplayer.doc.data.service.manage.WikiSpaceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * 文档控制器
 *
 * @author 暮光：城中城
 * @since 2019年2月17日
 */
@AuthMan
@RestController
@RequestMapping("/zyplayer-doc-wiki/space")
public class WikiSpaceController {
	private static Logger logger = LoggerFactory.getLogger(WikiSpaceController.class);
	
	@Resource
	WikiSpaceService wikiSpaceService;
	
	@PostMapping("/list")
	public ResponseJson<List<WikiSpace>> list(WikiSpace wikiSpace) {
		DocUserDetails currentUser = DocUserUtil.getCurrentUser();
		UpdateWrapper<WikiSpace> wrapper = new UpdateWrapper<>();
		wrapper.eq("del_flag", 0);
		wrapper.and(con -> con.and(conSub -> conSub.eq("type", 3).eq("create_user_id", currentUser.getUserId())).or().in("type", 1, 2));
		List<WikiSpace> authList = wikiSpaceService.list(wrapper);
		return DocResponseJson.ok(authList);
	}
	
	@PostMapping("/update")
	public ResponseJson<WikiSpace> update(WikiSpace wikiSpace) {
		Long id = wikiSpace.getId();
		DocUserDetails currentUser = DocUserUtil.getCurrentUser();
		
		if (id != null && id > 0) {
			WikiSpace wikiSpaceSel = wikiSpaceService.getById(id);
			if (Objects.equals(wikiSpaceSel.getEditType(), 1)) {
				return DocResponseJson.warn("当前空间不允许编辑！");
			}
			if (Objects.equals(wikiSpaceSel.getType(), 3) && !currentUser.getUserId().equals(wikiSpaceSel.getCreateUserId())) {
				return DocResponseJson.warn("您没有该空间的编辑权！");
			}
			wikiSpace.setUuid(null);
			wikiSpace.setEditType(null);
			wikiSpaceService.updateById(wikiSpace);
		} else {
			wikiSpace.setUuid(RandomUtil.simpleUUID());
			wikiSpace.setCreateTime(new Date());
			wikiSpace.setCreateUserId(currentUser.getUserId());
			wikiSpace.setCreateUserName(currentUser.getUsername());
			wikiSpaceService.save(wikiSpace);
		}
		return DocResponseJson.ok(wikiSpace);
	}
}

