package com.zyplayer.doc.wiki.controller;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.zyplayer.doc.core.json.DocResponseJson;
import com.zyplayer.doc.core.json.ResponseJson;
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
		if(wikiSpace.getType() == null) {
			wrapper.in("type", 1, 2);
		} else if(wikiSpace.getType() == 1 || wikiSpace.getType() == 2) {
			wrapper.eq(wikiSpace.getType() != null, "type", wikiSpace.getType());
			wrapper.eq(Objects.equals(wikiSpace.getType(), 2), "create_user_id", wikiSpace.getCreateUserId());
		} else if(wikiSpace.getType() == 3) {
			wrapper.eq("create_user_id", currentUser.getUserId());
		}
		List<WikiSpace> authList = wikiSpaceService.list(wrapper);
		return DocResponseJson.ok(authList);
	}
	
	@PostMapping("/update")
	public ResponseJson<Object> update(WikiSpace wikiSpace) {
		Long id = wikiSpace.getId();
		if (id != null && id > 0) {
			wikiSpaceService.updateById(wikiSpace);
		} else {
			DocUserDetails currentUser = DocUserUtil.getCurrentUser();
			wikiSpace.setCreateTime(new Date());
			wikiSpace.setCreateUserId(currentUser.getUserId());
			wikiSpace.setCreateUserName(currentUser.getUsername());
			wikiSpaceService.save(wikiSpace);
		}
		return DocResponseJson.ok(wikiSpace);
	}
}

