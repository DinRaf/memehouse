package ru.kek.memehouse.controllers.templates;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * gordeevnm@gmail.com
 * 13.04.18
 */
@Controller
public class IndexFtlController {
	@RequestMapping("/")
	public String index() {
		return "index";
	}
	
	@RequestMapping("/edit")
	public String edit() {
		return "edit";
	}
}
