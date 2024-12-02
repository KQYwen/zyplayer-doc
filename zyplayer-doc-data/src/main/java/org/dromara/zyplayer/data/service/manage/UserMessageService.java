package org.dromara.zyplayer.data.service.manage;

import com.baomidou.mybatisplus.extension.service.IService;
import org.dromara.zyplayer.data.config.security.DocUserDetails;
import org.dromara.zyplayer.data.repository.manage.entity.UserMessage;
import org.dromara.zyplayer.data.repository.support.consts.DocSysType;
import org.dromara.zyplayer.data.repository.support.consts.UserMsgType;

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
	
	UserMessage createUserMessage(DocUserDetails currentUser, Long pageId, String dataDesc, DocSysType sysType, UserMsgType msgType);
}
