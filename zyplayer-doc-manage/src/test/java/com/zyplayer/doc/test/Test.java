package com.zyplayer.doc.test;

public class Test {
	
	public static void main(String[] args) {
		String originRegex = "\\w+\\.zyplayer\\.com";
		String origin = "http://doc.zyplayer.com/manage/";
		origin = origin.replace("http://", "").replace("https://", "");
		origin = origin.substring(0, origin.indexOf("/"));
		System.out.println(origin.toLowerCase().matches(originRegex));
	}
	
}
