package com.zyplayer.doc.manage.web;

import com.zyplayer.doc.core.annotation.AuthMan;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 使用路劲的方式访问页面
 *
 * @author 暮光：城中城
 * @since 2019-06-05
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
	
	@GetMapping("/doc-db")
	public ModelAndView db() {
		ModelAndView modelAndView = new ModelAndView("/doc-db.html");
		modelAndView.setStatus(HttpStatus.OK);
		return modelAndView;
	}
	
	@GetMapping("/doc-api")
	public ModelAndView swaggerPlus() {
		ModelAndView modelAndView = new ModelAndView("/doc-api.html");
		modelAndView.setStatus(HttpStatus.OK);
		return modelAndView;
	}
	
	@GetMapping("/doc-dubbo")
	public ModelAndView dubbo() {
		ModelAndView modelAndView = new ModelAndView("/doc-dubbo.html");
		modelAndView.setStatus(HttpStatus.OK);
		return modelAndView;
	}
}
