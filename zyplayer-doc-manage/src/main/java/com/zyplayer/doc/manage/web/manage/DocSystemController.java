package com.zyplayer.doc.manage.web.manage;

import com.zyplayer.doc.data.aspect.AuthMan;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 使用路劲的方式访问页面
 */
@AuthMan
@Controller
public class DocSystemController {
	
	@GetMapping("/doc-wiki")
	public ModelAndView wiki() {
		return new ModelAndView("/doc-wiki-v2.html");
	}
	
	@GetMapping("/doc-db")
	public ModelAndView db() {
		return new ModelAndView("/doc-db.html");
	}
	
	@GetMapping("/doc-swagger")
	public ModelAndView swagger() {
		return new ModelAndView("/document.html");
	}
	
	@GetMapping("/doc-dubbo")
	public ModelAndView dubbo() {
		return new ModelAndView("/doc-dubbo.html");
	}
}
