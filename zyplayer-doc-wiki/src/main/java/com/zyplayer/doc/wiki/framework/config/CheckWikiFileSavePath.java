package com.zyplayer.doc.wiki.framework.config;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * 检测文件存放目录是否有被清理的风险
 *
 * @author 暮光：城中城
 * @since 2020年6月14日
 */
@Component
public class CheckWikiFileSavePath {
	private static Logger logger = LoggerFactory.getLogger(CheckWikiFileSavePath.class);
	
	@Value("${zyplayer.doc.wiki.upload-path:}")
	private String uploadPath;
	@Value("${zyplayer.doc.wiki.upload-path-check:true}")
	private Boolean uploadPathCheck;
	
	@PostConstruct
	public void postConstruct() {
		if (StringUtils.isBlank(uploadPath) || !uploadPathCheck) {
			return;
		}
		// 我就有这么笨，存在/tmp目录下文件被删了、、、特此搞个检测功能，提醒广大用户
		if (uploadPath.startsWith("/tmp") || uploadPath.startsWith("/var/tmp")) {
			logger.error("Linux系统中如果文件存放至/tmp或/var/tmp目录下，文件有被系统定期删除的风险。" +
					"如果确定没有问题，可修改配置文件的：zyplayer.doc.wiki.upload-path-check 项的值关闭此检查。" +
					"但我们还是建议您不要存放至此目录！");
			throw new RuntimeException("WIKI文件存放目录有被清理的风险");
		}
	}
	
}
