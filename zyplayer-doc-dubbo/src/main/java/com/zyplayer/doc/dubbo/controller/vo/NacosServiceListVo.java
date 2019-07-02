package com.zyplayer.doc.dubbo.controller.vo;

public class NacosServiceListVo {
		private String name;
		private int clusterCount;
		private int ipCount;
		private int healthyInstanceCount;
		
		public String getName() {
			return name;
		}
		
		public void setName(String name) {
			this.name = name;
		}
		
		public int getClusterCount() {
			return clusterCount;
		}
		
		public void setClusterCount(int clusterCount) {
			this.clusterCount = clusterCount;
		}
		
		public int getIpCount() {
			return ipCount;
		}
		
		public void setIpCount(int ipCount) {
			this.ipCount = ipCount;
		}
		
		public int getHealthyInstanceCount() {
			return healthyInstanceCount;
		}
		
		public void setHealthyInstanceCount(int healthyInstanceCount) {
			this.healthyInstanceCount = healthyInstanceCount;
		}
	}