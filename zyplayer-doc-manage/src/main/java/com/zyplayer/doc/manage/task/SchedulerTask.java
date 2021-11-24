package com.zyplayer.doc.manage.task;

import cn.hutool.http.HttpRequest;
import com.alibaba.fastjson.JSON;
import com.zyplayer.doc.core.util.ZyplayerDocVersion;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.StringReader;
import java.util.Objects;
import java.util.Properties;

/**
 * 定时器，用于访问gitee获取最新版本和升级内容
 *
 * @author 暮光：城中城
 * @since 2019年4月27日
 */
@Component
public class SchedulerTask {
	private static Logger logger = LoggerFactory.getLogger(SchedulerTask.class);
	
	@Value("${zyplayer.doc.manage.upgradePropertiesUrl:}")
	private String upgradePropertiesUrl;
	
	@PostConstruct
	private void init() {
		// 初始完成去加载一次
		this.upgradeTask();
	}
	
	//	@Scheduled(cron = "0 0/2 * * * ? ")
	@Scheduled(cron = "0 0 1 * * ?")
	public void upgradeTask() {
		// 检查更新，访问的码云服务器获取升级内容的
		if (StringUtils.isBlank(upgradePropertiesUrl)) {
			return;
		}
		try {
			String upgradeStr = HttpRequest.get(upgradePropertiesUrl).execute().body();
			Properties properties = new Properties();
			properties.load(new StringReader(upgradeStr));
			if (Objects.equals(ZyplayerDocVersion.version, properties.getProperty("lastVersion"))) {
				return;
			}
			String jsonString = JSON.toJSONString(properties);
			UpgradeUtil.upgradeInfo = JSON.parseObject(jsonString, UpgradeInfo.class);
			UpgradeUtil.upgradeInfo.setNowVersion(ZyplayerDocVersion.version);
		} catch (Exception e) {
			logger.info("获取升级内容失败");
		}
	}
}

