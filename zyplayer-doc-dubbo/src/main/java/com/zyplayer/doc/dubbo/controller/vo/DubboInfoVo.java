package com.zyplayer.doc.dubbo.controller.vo;

import com.zyplayer.doc.dubbo.framework.bean.DubboDocInfo;
import com.zyplayer.doc.dubbo.framework.bean.DubboInfo;

import java.util.List;
import java.util.Map;

/**
 * dubbo信息对象
 *
 * @author 暮光：城中城
 * @since 2019年1月10日
 **/
public class DubboInfoVo {
	private List<DubboInfo> serverList;
	private Map<String, DubboDocInfo> docMap;
	
	public List<DubboInfo> getServerList() {
		return serverList;
	}
	
	public void setServerList(List<DubboInfo> serverList) {
		this.serverList = serverList;
	}
	
	public Map<String, DubboDocInfo> getDocMap() {
		return docMap;
	}
	
	public void setDocMap(Map<String, DubboDocInfo> docMap) {
		this.docMap = docMap;
	}
}
