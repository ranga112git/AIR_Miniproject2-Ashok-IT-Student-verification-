package com.example1.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexPage_Controller {
	@GetMapping("/")
	public String index() {
		return "index";
	}

}
