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
public class productController {
	@Autowired
	SessionService session;
	@Autowired
	ProductDAO dao;

//	@RequestMapping("/product")
//	public  String product() {
//		return "shop/shop";
//	}
	@RequestMapping("/product")
	public String searchAndPage(Model model, @RequestParam("keywords") Optional<String> kw,
			@RequestParam("p") Optional<Integer> p, @RequestParam("g") Optional<String> g,
			@RequestParam("t") Optional<String> t, @RequestParam("c") Optional<String> c) {
		String kwords = kw.orElse(session.get("keywords", ""));
		session.set("keywords", kwords);

		String gender = g.orElse(session.get("gender", "all"));
		session.set("gender", gender);

		String type = t.orElse("");

		String category = c.orElse("");

		Pageable pageable = PageRequest.of(p.orElse(0), 9);
		Page<Product> page = null;

		if (gender.equals("all")) {
			if (!type.equals("")) {
				page = dao.findAllByNameLikeAndTypeLike("%" + kwords + "%", "%" + type + "%", pageable);
			} else if (!category.equals("")) {
				page = dao.findAllByNameLikeAndCategoryLike("%" + kwords + "%", "%" + category + "%", pageable);
			} else {
				page = dao.findAllByNameLike("%" + kwords + "%", pageable);
			}
		} else {
			if (!type.equals("")) {
				page = dao.findByNameLikeAndGenderLikeAndTypeLike("%" + kwords + "%", Boolean.valueOf(gender),
						"%" + type + "%", pageable);
			} else if (!category.equals("")) {
				page = dao.findByNameLikeAndGenderLikeAndCategoryLike("%" + kwords + "%", Boolean.valueOf(gender),
						"%" + category + "%", pageable);
			} else {
				page = dao.findByNameLikeAndGenderLike("%" + kwords + "%", Boolean.valueOf(gender), pageable);
			}
		}

		model.addAttribute("page", page);
		model.addAttribute("keywords", session.get("keywords", ""));
		System.out.println(gender);
		System.out.println(category);
		System.out.println(type);
		return "shop/shop";

	}

}
