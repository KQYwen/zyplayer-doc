package com.zyplayer.doc.manage.web.manage;

import com.zyplayer.doc.core.annotation.AuthMan;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 使用路劲的方式访问页面
 */
@Controller
public class DocSystemController {
	
	@GetMapping("/")
	public ModelAndView home() {
		return new ModelAndView("/doc-console.html");
	}
	
	@GetMapping("/doc-wiki")
	public ModelAndView wiki() {
		return new ModelAndView("/doc-wiki.html");
	}
	
	@AuthMan
	@GetMapping("/doc-db")
	public ModelAndView db() {
		return new ModelAndView("/doc-db.html");
	}
	
	@AuthMan
	@GetMapping("/doc-swagger")
	public ModelAndView swagger() {
		return new ModelAndView("/document.html");
	}
	
	@AuthMan
	@GetMapping("/doc-dubbo")
	public ModelAndView dubbo() {
		return new ModelAndView("/doc-dubbo.html");
	}
	
	@AuthMan
	@GetMapping("/doc-es")
	public ModelAndView es() {
		return new ModelAndView("/doc-es.html");
	}
}
