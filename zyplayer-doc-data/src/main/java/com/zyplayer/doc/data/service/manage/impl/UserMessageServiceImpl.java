package com.zyplayer.doc.data.service.manage.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zyplayer.doc.data.config.security.DocUserDetails;
import com.zyplayer.doc.data.repository.manage.entity.UserMessage;
import com.zyplayer.doc.data.repository.manage.mapper.UserMessageMapper;
import com.zyplayer.doc.data.repository.support.consts.UserMsgSysType;
import com.zyplayer.doc.data.repository.support.consts.UserMsgType;
import com.zyplayer.doc.data.service.manage.UserMessageService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Objects;

/**
 * <p>
 * 用户消息表 服务实现类
 * </p>
 *
 * @author 暮光：城中城
 * @since 2020-06-23
 */
@Service
public class UserMessageServiceImpl extends ServiceImpl<UserMessageMapper, UserMessage> implements UserMessageService {
	
	@Override
	public UserMessage createUserMessage(DocUserDetails currentUser, Long dataId, String dataDesc, UserMsgSysType sysType, UserMsgType msgType) {
		UserMessage userMessage = new UserMessage();
		userMessage.setDataId(dataId);
		userMessage.setDataDesc(dataDesc);
		userMessage.setSysType(sysType.getType());
		userMessage.setMsgType(msgType.getType());
		userMessage.setOperatorUserId(currentUser.getUserId());
		userMessage.setOperatorUserName(currentUser.getUsername());
		return userMessage;
	}
	
	@Override
	public void addWikiMessage(UserMessage userMessage) {
		// 初始值，操作人的消息默认为已读
		userMessage.setMsgStatus(1);
		userMessage.setCreationTime(new Date());
		// 操作人
		userMessage.setAcceptUserId(userMessage.getOperatorUserId());
		this.setWikiMsgContentForOperator(userMessage);
		this.save(userMessage);
		// 影响人
		if (userMessage.getAffectUserId() != null && !Objects.equals(userMessage.getAffectUserId(), userMessage.getOperatorUserId())) {
			userMessage.setId(null);
			// 收影响人的消息为未读
			userMessage.setMsgStatus(0);
			userMessage.setAcceptUserId(userMessage.getAffectUserId());
			this.setWikiMsgContentForAffect(userMessage);
			this.save(userMessage);
		}
		// 后期可以添加文档的关注人等
	}
	
	/**
	 * 给操作人发通知的内容
	 *
	 * @param userMessage
	 */
	private void setWikiMsgContentForOperator(UserMessage userMessage) {
		if (Objects.equals(UserMsgType.WIKI_PAGE_UPLOAD.getType(), userMessage.getMsgType())) {
			userMessage.setMsgContent(String.format("您在‘%s’文档内上传了一个附件", userMessage.getDataDesc()));
		} else if (Objects.equals(UserMsgType.WIKI_PAGE_AUTH.getType(), userMessage.getMsgType())) {
			userMessage.setMsgContent(String.format("您修改了‘%s’文档内‘%s’的权限", userMessage.getDataDesc(), userMessage.getAffectUserName()));
		} else if (Objects.equals(UserMsgType.WIKI_PAGE_COMMENT.getType(), userMessage.getMsgType())) {
			userMessage.setMsgContent(String.format("您评论了‘%s’", userMessage.getDataDesc()));
		} else if (Objects.equals(UserMsgType.WIKI_PAGE_COMMENT_DEL.getType(), userMessage.getMsgType())) {
			userMessage.setMsgContent(String.format("您删除了‘%s’文档的评论", userMessage.getDataDesc()));
		} else if (Objects.equals(UserMsgType.WIKI_PAGE_PARENT.getType(), userMessage.getMsgType())) {
			userMessage.setMsgContent(String.format("您修改了‘%s’文档的父级", userMessage.getDataDesc()));
		} else if (Objects.equals(UserMsgType.WIKI_PAGE_DELETE.getType(), userMessage.getMsgType())) {
			userMessage.setMsgContent(String.format("您删除了‘%s’", userMessage.getDataDesc()));
		} else if (Objects.equals(UserMsgType.WIKI_PAGE_CREATE.getType(), userMessage.getMsgType())) {
			userMessage.setMsgContent(String.format("您创建了‘%s’", userMessage.getDataDesc()));
		} else if (Objects.equals(UserMsgType.WIKI_PAGE_UPDATE.getType(), userMessage.getMsgType())) {
			userMessage.setMsgContent(String.format("您修改了‘%s’", userMessage.getDataDesc()));
		} else if (Objects.equals(UserMsgType.WIKI_PAGE_ZAN.getType(), userMessage.getMsgType())) {
			userMessage.setMsgContent(String.format("您赞同了‘%s’", userMessage.getDataDesc()));
		} else if (Objects.equals(UserMsgType.WIKI_PAGE_FILE_DEL.getType(), userMessage.getMsgType())) {
			userMessage.setMsgContent(String.format("您删除了‘%s’文档的一个附件", userMessage.getDataDesc()));
		}
	}
	
	/**
	 * 给影响人发通知的内容
	 *
	 * @param userMessage
	 */
	private void setWikiMsgContentForAffect(UserMessage userMessage) {
		if (Objects.equals(UserMsgType.WIKI_PAGE_UPLOAD.getType(), userMessage.getMsgType())) {
			userMessage.setMsgContent(String.format("‘%s’为您的文档‘%s’上传了附件", userMessage.getOperatorUserName(), userMessage.getDataDesc()));
		} else if (Objects.equals(UserMsgType.WIKI_PAGE_AUTH.getType(), userMessage.getMsgType())) {
			userMessage.setMsgContent(String.format("您在‘%s’文档内的权限被‘%s’修改了", userMessage.getDataDesc(), userMessage.getOperatorUserName()));
		} else if (Objects.equals(UserMsgType.WIKI_PAGE_COMMENT.getType(), userMessage.getMsgType())) {
			userMessage.setMsgContent(String.format("您的‘%s’文档收到了来自‘%s’的评论", userMessage.getDataDesc(), userMessage.getOperatorUserName()));
		} else if (Objects.equals(UserMsgType.WIKI_PAGE_COMMENT_DEL.getType(), userMessage.getMsgType())) {
			userMessage.setMsgContent(String.format("‘%s’删除了您的文档‘%s’的一条评论", userMessage.getOperatorUserName(), userMessage.getDataDesc()));
		} else if (Objects.equals(UserMsgType.WIKI_PAGE_PARENT.getType(), userMessage.getMsgType())) {
			userMessage.setMsgContent(String.format("您的‘%s’文档被‘%s’修改了父级", userMessage.getDataDesc(), userMessage.getOperatorUserName()));
		} else if (Objects.equals(UserMsgType.WIKI_PAGE_DELETE.getType(), userMessage.getMsgType())) {
			userMessage.setMsgContent(String.format("您的‘%s’文档被‘%s’删除了", userMessage.getDataDesc(), userMessage.getOperatorUserName()));
		} else if (Objects.equals(UserMsgType.WIKI_PAGE_UPDATE.getType(), userMessage.getMsgType())) {
			userMessage.setMsgContent(String.format("您的‘%s’文档被‘%s’修改了", userMessage.getDataDesc(), userMessage.getOperatorUserName()));
		} else if (Objects.equals(UserMsgType.WIKI_PAGE_ZAN.getType(), userMessage.getMsgType())) {
			userMessage.setMsgContent(String.format("您的‘%s’文档收到了‘%s’的赞同", userMessage.getDataDesc(), userMessage.getOperatorUserName()));
		} else if (Objects.equals(UserMsgType.WIKI_PAGE_FILE_DEL.getType(), userMessage.getMsgType())) {
			userMessage.setMsgContent(String.format("您的‘%s’文档被‘%s’删除了一个附件", userMessage.getDataDesc(), userMessage.getOperatorUserName()));
		}
	}
}
