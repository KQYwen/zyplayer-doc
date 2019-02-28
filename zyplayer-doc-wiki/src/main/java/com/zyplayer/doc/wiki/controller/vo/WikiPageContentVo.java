package com.zyplayer.doc.wiki.controller.vo;

import com.zyplayer.doc.data.repository.manage.entity.WikiPage;
import com.zyplayer.doc.data.repository.manage.entity.WikiPageContent;
import com.zyplayer.doc.data.repository.manage.entity.WikiPageFile;

import java.util.List;

public class WikiPageContentVo {
	private WikiPage wikiPage;
	private WikiPageContent pageContent;
	private List<WikiPageFile> fileList;
	
	public WikiPage getWikiPage() {
		return wikiPage;
	}
	
	public void setWikiPage(WikiPage wikiPage) {
		this.wikiPage = wikiPage;
	}
	
	public WikiPageContent getPageContent() {
		return pageContent;
	}
	
	public void setPageContent(WikiPageContent pageContent) {
		this.pageContent = pageContent;
	}
	
	public List<WikiPageFile> getFileList() {
		return fileList;
	}
	
	public void setFileList(List<WikiPageFile> fileList) {
		this.fileList = fileList;
	}
}
