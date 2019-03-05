package com.zyplayer.doc.test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class MediaTypeTest {
	
	public static void main(String[] args) throws IOException {
//		MediaType xx = MediaType.parseMediaType("png");
//		System.out.println(xx.getSubtype());
		File file = new File("e:/tmp/wikiFiles/2019/03/04/dcf4b4357d5a454180cf54a5d8a2f868.jpg");
//		String path = file.get
		
		String contentType = Files.probeContentType(file.toPath());
		System.out.println(contentType);
	}
	
}
