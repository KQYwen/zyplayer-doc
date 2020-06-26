package com.zyplayer.doc.data.service.manage;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zyplayer.doc.data.config.security.DocUserDetails;
import com.zyplayer.doc.data.repository.manage.entity.UserMessage;
import com.zyplayer.doc.data.repository.support.consts.UserMsgSysType;
import com.zyplayer.doc.data.repository.support.consts.UserMsgType;

/**
 * <p>
 * 用户消息表 服务类
 * </p>
 *
 * @author 暮光：城中城
 * @since 2020-06-23
 */
public interface UserMessageService extends IService<UserMessage> {
	
	void addWikiMessage(UserMessage userMessage);
	
	UserMessage createUserMessage(DocUserDetails currentUser, Long pageId, String dataDesc, UserMsgSysType sysType, UserMsgType msgType);
}
