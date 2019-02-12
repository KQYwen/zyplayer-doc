package com.zyplayer.doc.dubbo.framework.bean;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

/**
 * @author 暮光：城中城
 * @since 2019年1月10日
 **/
public class NacosDubboInfo {
	
	/**
	 * metadata : {}
	 * dom : providers:com.zyplayer.dubbo.service.UserService
	 * cacheMillis : 10000
	 * useSpecifiedURL : false
	 * hosts : [{"valid":true,"marked":false,"metadata":{"side":"provider","protocol":"dubbo","application":"dubbo-provider","methods":"getUserDetail,getUserList","dubbo":"2.0.2","pid":"8164","interface":"com.zyplayer.dubbo.service.UserService","category":"providers","generic":"false","anyhost":"true","bean.name":"ServiceBean:com.zyplayer.dubbo.service.UserService","timestamp":"1549953970871"},"instanceId":"127.0.0.1#22223#DEFAULT#providers:com.zyplayer.dubbo.service.UserService","port":22223,"ip":"127.0.0.1","clusterName":"DEFAULT","weight":1,"serviceName":"providers:com.zyplayer.dubbo.service.UserService","enabled":true}]
	 * checksum : 2489ae2874f2f490caf7d6195192d6c71549954548862
	 * lastRefTime : 1549954548862
	 * env :
	 * clusters :
	 */
	
	private MetadataBean metadata;
	private String dom;
	private int cacheMillis;
	private boolean useSpecifiedURL;
	private String checksum;
	private long lastRefTime;
	private String env;
	private String clusters;
	private List<HostsBean> hosts;
	
	public MetadataBean getMetadata() {
		return metadata;
	}
	
	public void setMetadata(MetadataBean metadata) {
		this.metadata = metadata;
	}
	
	public String getDom() {
		return dom;
	}
	
	public void setDom(String dom) {
		this.dom = dom;
	}
	
	public int getCacheMillis() {
		return cacheMillis;
	}
	
	public void setCacheMillis(int cacheMillis) {
		this.cacheMillis = cacheMillis;
	}
	
	public boolean isUseSpecifiedURL() {
		return useSpecifiedURL;
	}
	
	public void setUseSpecifiedURL(boolean useSpecifiedURL) {
		this.useSpecifiedURL = useSpecifiedURL;
	}
	
	public String getChecksum() {
		return checksum;
	}
	
	public void setChecksum(String checksum) {
		this.checksum = checksum;
	}
	
	public long getLastRefTime() {
		return lastRefTime;
	}
	
	public void setLastRefTime(long lastRefTime) {
		this.lastRefTime = lastRefTime;
	}
	
	public String getEnv() {
		return env;
	}
	
	public void setEnv(String env) {
		this.env = env;
	}
	
	public String getClusters() {
		return clusters;
	}
	
	public void setClusters(String clusters) {
		this.clusters = clusters;
	}
	
	public List<HostsBean> getHosts() {
		return hosts;
	}
	
	public void setHosts(List<HostsBean> hosts) {
		this.hosts = hosts;
	}
	
	public static class MetadataBean {
	}
	
	public static class HostsBean {
		/**
		 * valid : true
		 * marked : false
		 * metadata : {"side":"provider","protocol":"dubbo","application":"dubbo-provider","methods":"getUserDetail,getUserList","dubbo":"2.0.2","pid":"8164","interface":"com.zyplayer.dubbo.service.UserService","category":"providers","generic":"false","anyhost":"true","bean.name":"ServiceBean:com.zyplayer.dubbo.service.UserService","timestamp":"1549953970871"}
		 * instanceId : 127.0.0.1#22223#DEFAULT#providers:com.zyplayer.dubbo.service.UserService
		 * port : 22223
		 * ip : 127.0.0.1
		 * clusterName : DEFAULT
		 * weight : 1
		 * serviceName : providers:com.zyplayer.dubbo.service.UserService
		 * enabled : true
		 */
		
		private boolean valid;
		private boolean marked;
		private MetadataBeanX metadata;
		private String instanceId;
		private int port;
		private String ip;
		private String clusterName;
		private int weight;
		private String serviceName;
		private boolean enabled;
		
		public boolean isValid() {
			return valid;
		}
		
		public void setValid(boolean valid) {
			this.valid = valid;
		}
		
		public boolean isMarked() {
			return marked;
		}
		
		public void setMarked(boolean marked) {
			this.marked = marked;
		}
		
		public MetadataBeanX getMetadata() {
			return metadata;
		}
		
		public void setMetadata(MetadataBeanX metadata) {
			this.metadata = metadata;
		}
		
		public String getInstanceId() {
			return instanceId;
		}
		
		public void setInstanceId(String instanceId) {
			this.instanceId = instanceId;
		}
		
		public int getPort() {
			return port;
		}
		
		public void setPort(int port) {
			this.port = port;
		}
		
		public String getIp() {
			return ip;
		}
		
		public void setIp(String ip) {
			this.ip = ip;
		}
		
		public String getClusterName() {
			return clusterName;
		}
		
		public void setClusterName(String clusterName) {
			this.clusterName = clusterName;
		}
		
		public int getWeight() {
			return weight;
		}
		
		public void setWeight(int weight) {
			this.weight = weight;
		}
		
		public String getServiceName() {
			return serviceName;
		}
		
		public void setServiceName(String serviceName) {
			this.serviceName = serviceName;
		}
		
		public boolean isEnabled() {
			return enabled;
		}
		
		public void setEnabled(boolean enabled) {
			this.enabled = enabled;
		}
		
		public static class MetadataBeanX {
			/**
			 * side : provider
			 * protocol : dubbo
			 * application : dubbo-provider
			 * methods : getUserDetail,getUserList
			 * dubbo : 2.0.2
			 * pid : 8164
			 * interface : com.zyplayer.dubbo.service.UserService
			 * category : providers
			 * generic : false
			 * anyhost : true
			 * bean.name : ServiceBean:com.zyplayer.dubbo.service.UserService
			 * timestamp : 1549953970871
			 */
			
			private String side;
			private String protocol;
			private String application;
			private String methods;
			private String dubbo;
			private String pid;
			@JSONField(name = "interface")
			private String interfaceX;
			private String category;
			private String generic;
			private String anyhost;
			@JSONField(name = "bean.name")
			private String _$BeanName0; // FIXME check this code
			private String timestamp;
			
			public String getSide() {
				return side;
			}
			
			public void setSide(String side) {
				this.side = side;
			}
			
			public String getProtocol() {
				return protocol;
			}
			
			public void setProtocol(String protocol) {
				this.protocol = protocol;
			}
			
			public String getApplication() {
				return application;
			}
			
			public void setApplication(String application) {
				this.application = application;
			}
			
			public String getMethods() {
				return methods;
			}
			
			public void setMethods(String methods) {
				this.methods = methods;
			}
			
			public String getDubbo() {
				return dubbo;
			}
			
			public void setDubbo(String dubbo) {
				this.dubbo = dubbo;
			}
			
			public String getPid() {
				return pid;
			}
			
			public void setPid(String pid) {
				this.pid = pid;
			}
			
			public String getInterfaceX() {
				return interfaceX;
			}
			
			public void setInterfaceX(String interfaceX) {
				this.interfaceX = interfaceX;
			}
			
			public String getCategory() {
				return category;
			}
			
			public void setCategory(String category) {
				this.category = category;
			}
			
			public String getGeneric() {
				return generic;
			}
			
			public void setGeneric(String generic) {
				this.generic = generic;
			}
			
			public String getAnyhost() {
				return anyhost;
			}
			
			public void setAnyhost(String anyhost) {
				this.anyhost = anyhost;
			}
			
			public String get_$BeanName0() {
				return _$BeanName0;
			}
			
			public void set_$BeanName0(String _$BeanName0) {
				this._$BeanName0 = _$BeanName0;
			}
			
			public String getTimestamp() {
				return timestamp;
			}
			
			public void setTimestamp(String timestamp) {
				this.timestamp = timestamp;
			}
		}
	}
}
