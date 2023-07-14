package com.ps20697.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ps20697.Entity.Product;
import com.ps20697.Interface.ProductDAO;
import com.ps20697.Service.SessionService;


@Controller
public class ShopController {
	
	@Autowired
	ProductDAO dao;
	
	@Autowired
	SessionService session;
	
	@RequestMapping("/Shop")
	public String shop() {
		return "shop/Home";
	}
	
	@RequestMapping("/About")
	public String about() {
		return "shop/about";
	}
	
	@RequestMapping("/Contact")
	public String contact() {
		return "shop/contact";
	}
	@RequestMapping("/shop/shop")
	public String searchAndPage(Model model,@RequestParam("keywords") Optional<String> kw, @RequestParam("p") Optional<Integer> p) {
		String kwords = kw.orElse(session.get("keywords", ""));
		session.set("keywords", kwords);
		Pageable pageable = PageRequest.of(p.orElse(0), 5);
		Page<Product> page = dao.findAllByNameLike("%"+kwords+"%", pageable);
		model.addAttribute("page", page);
		return "shop/shop";

	}
	//return page
	@RequestMapping(value="/shop/return",params = "returnHomePage" )
	public String returnHomepage() {
		return "redirect:/Shop";
	}
	
	@RequestMapping(value="/shop/return",params = "about" )
	public String returnAbout() {
		return "redirect:/About";
	}
	
	@RequestMapping(value="/shop/return",params = "contact" )
	public String returnContact() {
		return "redirect:/Contact";
	}
}
