package com.zyplayer.doc.swagger.controller.param;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.resource.BytesResource;
import cn.hutool.core.io.resource.MultiFileResource;
import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.Method;
import org.springframework.web.multipart.MultipartFile;

/**
 * 请求参数对象
 * 
 * @author 暮光：城中城
 * @since 2018年8月21日
 */
public class HttpRequestParam {
	private String url;
	private String method;
	private String header;
	private String form;
	private String body;
	private String[] fileNames;
	private MultipartFile[] files;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	public String getForm() {
		return form;
	}

	public void setForm(String form) {
		this.form = form;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public Map<String, String> getHeaderMap() {
		if (StringUtils.isBlank(header)) {
			return null;
		}
		Map<String, String> headerMap = JSON.parseObject(header, new TypeReference<HashMap<String, String>>() {});
		return headerMap;
	}

	public Map<String, Object> getFormMap() {
		if (StringUtils.isBlank(form)) {
			return null;
		}
		Map<String, Object> formMap = JSON.parseObject(form, new TypeReference<HashMap<String, Object>>() {});
		return formMap;
	}

	public void createHttpRequest(HttpRequest request) {
		Map<String, String> headerMap = this.getHeaderMap();
		if (headerMap != null) {
			request.addHeaders(headerMap);
			if (headerMap.containsKey("Content-Type")) {
				request.contentType(headerMap.get("Content-Type"));
			}
		}
		Map<String, Object> formMap = this.getFormMap();
		if (formMap != null) {
			request.form(formMap);
		}
		// 拼文件
		if (this.getFiles() != null && this.getFiles().length > 0) {
			Map<String, MultiFileResource> bytesResourceMap = new HashMap<>();
			for (int i = 0; i < this.getFiles().length; i++) {
				try {
					String fileName = this.getFileNames()[i].replace("[", "").replace("]", "");
					MultipartFile file = this.getFiles()[i];
					BytesResource bytesResource = new BytesResource(file.getBytes(), file.getOriginalFilename());
					MultiFileResource multiFileResource = bytesResourceMap.get(fileName);
					if (multiFileResource == null) {
						multiFileResource = new MultiFileResource();
					}
					multiFileResource.add(bytesResource);
					bytesResourceMap.put(fileName, multiFileResource);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			for (Map.Entry<String, MultiFileResource> entry : bytesResourceMap.entrySet()) {
				request.form(entry.getKey(), entry.getValue());
			}
		}
		if (StringUtils.isNotBlank(body) && request.getMethod() != Method.GET) {
			request.body(body);
		}
	}
	
	/**
	 * 组装请求
	 * @return HttpRequest
	 */
	public HttpRequest createRequest() {
		if("get".equalsIgnoreCase(this.method)) return get();
		if("post".equalsIgnoreCase(this.method)) return post();
		if("head".equalsIgnoreCase(this.method)) return head();
		if("options".equalsIgnoreCase(this.method)) return options();
		if("put".equalsIgnoreCase(this.method)) return put();
		if("patch".equalsIgnoreCase(this.method)) return patch();
		if("delete".equalsIgnoreCase(this.method)) return delete();
		if("trace".equalsIgnoreCase(this.method)) return trace();
		return get();
	}

	/**
	 * POST请求
	 * @return HttpRequest
	 */
	public HttpRequest post() {
		HttpRequest request = HttpRequest.post(this.getUrl());
		this.createHttpRequest(request);
		return request;
	}

	/**
	 * GET请求
	 * @return HttpRequest
	 */
	public HttpRequest get() {
		HttpRequest request = HttpRequest.get(this.getUrl());
		this.createHttpRequest(request);
		return request;
	}

	/**
	 * HEAD请求
	 * @return HttpRequest
	 */
	public HttpRequest head() {
		HttpRequest request = HttpRequest.head(this.getUrl());
		this.createHttpRequest(request);
		return request;
	}

	/**
	 * OPTIONS请求
	 * @return HttpRequest
	 */
	public HttpRequest options() {
		HttpRequest request = HttpRequest.options(this.getUrl());
		this.createHttpRequest(request);
		return request;
	}

	/**
	 * PUT请求
	 * @return HttpRequest
	 */
	public HttpRequest put() {
		HttpRequest request = HttpRequest.put(this.getUrl());
		this.createHttpRequest(request);
		return request;
	}

	/**
	 * PATCH请求
	 * @return HttpRequest
	 */
	public HttpRequest patch() {
		HttpRequest request = HttpRequest.patch(this.getUrl());
		this.createHttpRequest(request);
		return request;
	}

	/**
	 * DELETE请求
	 * @return HttpRequest
	 */
	public HttpRequest delete() {
		HttpRequest request = HttpRequest.delete(this.getUrl());
		this.createHttpRequest(request);
		return request;
	}

	/**
	 * TRACE请求
	 * @return HttpRequest
	 */
	public HttpRequest trace() {
		HttpRequest request = HttpRequest.trace(this.getUrl());
		this.createHttpRequest(request);
		return request;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}
	
	public MultipartFile[] getFiles() {
		return files;
	}
	
	public void setFiles(MultipartFile[] files) {
		this.files = files;
	}
	
	public String[] getFileNames() {
		return fileNames;
	}
	
	public void setFileNames(String[] fileNames) {
		this.fileNames = fileNames;
	}
}
