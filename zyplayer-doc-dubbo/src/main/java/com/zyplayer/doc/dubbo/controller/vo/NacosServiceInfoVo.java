package com.zyplayer.doc.dubbo.controller.vo;

import java.util.List;

public class NacosServiceInfoVo {
	
	private int count;
	private List<NacosServiceListVo> serviceList;
	
	public int getCount() {
		return count;
	}
	
	public void setCount(int count) {
		this.count = count;
	}
	
	public List<NacosServiceListVo> getServiceList() {
		return serviceList;
	}
	
	public void setServiceList(List<NacosServiceListVo> serviceList) {
		this.serviceList = serviceList;
	}
	
}
