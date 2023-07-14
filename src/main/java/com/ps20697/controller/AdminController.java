package com.ps20697.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AdminController {
	@RequestMapping("/Admin")
	public String admin() {
		return "Admin/index";
	}
}
