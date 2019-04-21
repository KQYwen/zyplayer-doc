package com.zyplayer.doc.manage.task;

import cn.hutool.http.HttpRequest;
import com.alibaba.fastjson.JSON;
import com.zyplayer.doc.manage.utils.UpgradeUtil;
import com.zyplayer.doc.manage.utils.bean.UpgradeInfo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.StringReader;
import java.util.Objects;
import java.util.Properties;

@Component
public class SchedulerTask {
	
	@Value("${zyplayer.doc.manage.version:''}")
	private String nowVersion;
	@Value("${zyplayer.doc.manage.upgradePropertiesUrl:''}")
	private String upgradePropertiesUrl;
	
//	@Scheduled(cron = "0/10 * * * * ? ")
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
			if (Objects.equals(nowVersion, properties.getProperty("lastVersion"))) {
				return;
			}
			properties.setProperty("nowVersion", nowVersion);
			String jsonString = JSON.toJSONString(properties);
			UpgradeUtil.upgradeInfo = JSON.parseObject(jsonString, UpgradeInfo.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

