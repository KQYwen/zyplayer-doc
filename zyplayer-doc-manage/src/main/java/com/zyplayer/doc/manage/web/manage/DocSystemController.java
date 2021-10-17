package com.zyplayer.doc.manage.web.manage;

import com.zyplayer.doc.core.annotation.AuthMan;
import org.springframework.http.HttpStatus;
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
		ModelAndView modelAndView = new ModelAndView("/doc-console.html");
		modelAndView.setStatus(HttpStatus.OK);
		return modelAndView;
	}
	
	@GetMapping("/doc-wiki")
	public ModelAndView wiki() {
		ModelAndView modelAndView = new ModelAndView("/doc-wiki.html");
		modelAndView.setStatus(HttpStatus.OK);
		return modelAndView;
	}
	
	@AuthMan
	@GetMapping("/doc-db")
	public ModelAndView db() {
		ModelAndView modelAndView = new ModelAndView("/doc-db.html");
		modelAndView.setStatus(HttpStatus.OK);
		return modelAndView;
	}
	
	@AuthMan
	@GetMapping("/doc-swagger")
	public ModelAndView swagger() {
		ModelAndView modelAndView = new ModelAndView("/document.html");
		modelAndView.setStatus(HttpStatus.OK);
		return modelAndView;
	}
	
	@AuthMan
	@GetMapping("/doc-swagger-plus")
	public ModelAndView swaggerPlus() {
		ModelAndView modelAndView = new ModelAndView("/doc-swagger-plus.html");
		modelAndView.setStatus(HttpStatus.OK);
		return modelAndView;
	}
	
	@AuthMan
	@GetMapping("/doc-dubbo")
	public ModelAndView dubbo() {
		ModelAndView modelAndView = new ModelAndView("/doc-dubbo.html");
		modelAndView.setStatus(HttpStatus.OK);
		return modelAndView;
	}
	
	@AuthMan
	@GetMapping("/doc-es")
	public ModelAndView es() {
		ModelAndView modelAndView = new ModelAndView("/doc-es.html");
		modelAndView.setStatus(HttpStatus.OK);
		return modelAndView;
	}
}
