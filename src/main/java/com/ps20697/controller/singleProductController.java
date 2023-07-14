package com.ps20697.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ps20697.Entity.Cart;
import com.ps20697.Entity.Product;
import com.ps20697.Entity.ProductDetails;
import com.ps20697.Interface.ProductDAO;
import com.ps20697.Service.SessionService;
import com.ps20697.Service.ShoppingCartService;

@Controller
public class singleProductController {
	@Autowired
	ProductDAO dao;

	@Autowired
	ShoppingCartService cart;
	
	@Autowired
	SessionService session;

	@GetMapping("/{id}")
	public String product(@PathVariable("id") Integer id, Model model) {
		Product product = dao.findById(id).get();
		model.addAttribute("item", product);

		List<ProductDetails> productDetail = product.getProductDetails();
		if (productDetail.isEmpty()) {

		} else {
			model.addAttribute("details", productDetail);

		}

		Boolean sizeS = true;
		Boolean sizeM = true;
		Boolean sizeL = true;
		Boolean sizeXL = true;
		for (int i = 0; i < productDetail.size() - 1; i++) {
			if (productDetail.get(i).getSize().equals("S")) {
				sizeS = false;
			}
			if (productDetail.get(i).getSize().equals("M")) {
				sizeM = false;
			}
			if (productDetail.get(i).getSize().equals("L")) {
				sizeL = false;
			}
			if (productDetail.get(i).getSize().equals("XL")) {
				sizeXL = false;
			}
		}
		model.addAttribute("sizeM", sizeM);
		model.addAttribute("sizeS", sizeS);
		model.addAttribute("sizeL", sizeL);
		model.addAttribute("sizeXL", sizeXL);
		System.out.println(sizeM);
		System.out.println(sizeS);
		System.out.println(sizeL);
		System.out.println(sizeXL);
		return "shop/shop-single";
	}

	@PostMapping("/{id}")
	public String addCart(@PathVariable("id") Integer id, Model model, @RequestParam("color") String color,
			@RequestParam("size") String size, @RequestParam("qty") String qty) {
		if (session.get("username","").equals("") && session.get("password","").equals("")) {
			return "/account/SignIn";
		}
		Product product = dao.findById(id).get();
		model.addAttribute("item", product);

		List<ProductDetails> productDetail = product.getProductDetails();

		if (productDetail.isEmpty()) {

		} else {
			model.addAttribute("details", productDetail);

		}
		Boolean sizeS = true;
		Boolean sizeM = true;
		Boolean sizeL = true;
		Boolean sizeXL = true;
		for (int i = 0; i < productDetail.size() - 1; i++) {
			if (productDetail.get(i).getSize().equals("S")) {
				sizeS = false;
			}
			if (productDetail.get(i).getSize().equals("M")) {
				sizeM = false;
			}
			if (productDetail.get(i).getSize().equals("L")) {
				sizeL = false;
			}
			if (productDetail.get(i).getSize().equals("XL")) {
				sizeXL = false;
			}
		}
		model.addAttribute("sizeM", sizeM);
		model.addAttribute("sizeS", sizeS);
		model.addAttribute("sizeL", sizeL);
		model.addAttribute("sizeXL", sizeXL);
		String realSize = size;
		boolean Stock = true;
		if (realSize == null) {
			realSize = "NO";
		}
		for (int i = 0; i < productDetail.size() - 1; i++) {
			if (productDetail.get(i).getColor().equals(color) && productDetail.get(i).getSize().equals(realSize)) {
				Stock = false;
			}

		}

		if (color.equals("") && productDetail.size() >= 0) {
			model.addAttribute("validateColor", "Please choose color");
			return "/shop/shop-single";
		} else if (size.equals("") && (sizeM == true || sizeS == true || sizeL == true || sizeXL == true)) {
			model.addAttribute("validateSize", "Please choose Size");
			return "/shop/shop-single";
		} else if (productDetail.size() <= 0 && Stock == true) {
			model.addAttribute("validateDetail", "The product is not in stock");
			return "/shop/shop-single";
		} else {
			cart.add(id, realSize, Integer.parseInt(qty), color);
			return "/shop/shop-single";
		}
	}

	@RequestMapping("/viewCart")
	public String viewCart(Model model) {
		model.addAttribute("cart", cart.getItems());
		model.addAttribute("ttPrice", cart.getAmount());
		System.out.println(cart.getItems().toString());

		return "shop/shoppingCart";
	}

	@RequestMapping("/deleteCart/{id}")
	public String deleteCart(Model model, @PathVariable("id") Integer id) {
		cart.remove(id);
		return "redirect:/viewCart";
	}

	@RequestMapping("/{changeId}")
	public String changeCart(Model model, @PathVariable("changeId") Integer id) {
		Product product = dao.findById(id).get();
		model.addAttribute("item", product);

		List<ProductDetails> productDetail = product.getProductDetails();
		if (productDetail.isEmpty()) {

		} else {
			model.addAttribute("details", productDetail);

		}

		Boolean sizeS = true;
		Boolean sizeM = true;
		Boolean sizeL = true;
		Boolean sizeXL = true;
		for (int i = 0; i < productDetail.size() - 1; i++) {
			if (productDetail.get(i).getSize().equals("S")) {
				sizeS = false;
			}
			if (productDetail.get(i).getSize().equals("M")) {
				sizeM = false;
			}
			if (productDetail.get(i).getSize().equals("L")) {
				sizeL = false;
			}
			if (productDetail.get(i).getSize().equals("XL")) {
				sizeXL = false;
			}
		}
		model.addAttribute("sizeM", sizeM);
		model.addAttribute("sizeS", sizeS);
		model.addAttribute("sizeL", sizeL);
		model.addAttribute("sizeXL", sizeXL);
		System.out.println(sizeM);
		System.out.println(sizeS);
		System.out.println(sizeL);
		System.out.println(sizeXL);
		return "shop/shop-single";
	}

	@RequestMapping("/changeB/{Quantity}")
	public String changeQuantityB(@PathVariable("Quantity") int id, Model model) {
		Cart item = cart.getItem(id);

		if (item.getQuantity() == 1) {
			cart.update(id, item.getQuantity());
		} else {
			cart.update(id, item.getQuantity() - 1);
		}
		return "redirect:/viewCart";
	}

	@RequestMapping("/changeT/{Quantity}")
	public String changeQuantityT(@PathVariable("Quantity") int id, Model model) {
		Cart item = cart.getItem(id);

		if (item.getQuantity() == 10) {
			cart.update(id, item.getQuantity());
		} else {
			cart.update(id, item.getQuantity() + 1);
		}
		return "redirect:/viewCart";
	}
	@RequestMapping("/checkOut")
	public String checkOut(Model model) {
		   
		return "shop/checkOut";
	}
}
