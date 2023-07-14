package com.ps20697.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ps20697.Entity.Account;
import com.ps20697.Interface.AccountDAO;
import com.ps20697.Service.CookieService;
import com.ps20697.Service.ParamService;
import com.ps20697.Service.SessionService;


@Controller
public class accountController {

	@Autowired
	ParamService paramService;
	@Autowired
	SessionService sessionService;

	@Autowired
	CookieService cookieService;
	
	@Autowired
	AccountDAO dao;
	private Account account;

	@GetMapping("/SignIn")
	public String signInView() {
		return "account/SignIn";
	}

	@PostMapping("/SignIn")
	public String login2(Model model) {
		model.addAttribute("username", sessionService.get("username",""));
		model.addAttribute("password", sessionService.get("password",""));
		String un = paramService.getString("username", "");
		String pw = paramService.getString("password", "");
		boolean rm = paramService.getBoolean("remember", false);
		
		account = dao.findByUserLikeAndPassLike(un, pw);
		
		if (account !=null) {
			sessionService.set("username", un);
			sessionService.set("password", pw);
			if (rm == true) {
				cookieService.add("user", un, 10);
				cookieService.add("pword", pw, 10);
				model.addAttribute("username", un);
				model.addAttribute("password", pw);
			} else {
				cookieService.remove("user");
				model.addAttribute("username", "");
				model.addAttribute("password", "");
			}
			if (account.isAdmin() == true) {
				return "Admin/index";
			}else {
				return "/shop/Home";
			}
		} else {
			model.addAttribute("message", "!");
			return "redirect:/account/SignIn";
		}
		
	}

	@GetMapping("/account/SignUp")
	public String signUp() {
		return "account/SignUp";
	}
	
	@PostMapping("/account/SignUp")
	public String signUpSetData(Model model) {
		String un = paramService.getString("username", "");
		String fullname = paramService.getString("fullname", "");
		String email = paramService.getString("email", "");
		String password = paramService.getString("password", "");
		String repeatPassword = paramService.getString("repeatPassword", "");
		boolean statement = paramService.getBoolean("statement", false);
		account = dao.findByUserLike(un);
		if (account !=null) {
			model.addAttribute("validate", "Username already in use");
			return "/account/SignUp";
		}else {
			 if (un.equals("")) {
				model.addAttribute("validate", "Please confirm your login name");
				return "/account/SignUp";
			}else if (fullname.equals("")) {
				model.addAttribute("validate", "Please confirm your Fullname");
				return "/account/SignUp";
			}else if (password.equals("")) {
				model.addAttribute("validate", "Please confirm your Password");
				return "/account/SignUp";
			}else if (repeatPassword.equals("")) {
				model.addAttribute("validate", "Please confirm your password");
				return "/account/SignUp";
			}else if (repeatPassword.equals(password)) {
				model.addAttribute("validate","Confirmation password is not correct");
				return "/account/SignUp";
			}else if (statement != true) {
				model.addAttribute("validate","Please agree to our policies");
				return "/account/SignUp";
			}else {
				Account account  = new Account();
				account.setUsername(un);
				account.setActivated(true);
				account.setAdmin(false);
				account.setFullname(fullname);
				account.setPassword(password);
				account.setPhoto(null);
				account.setEmail(email);
				dao.save(account);
				return "/SignIn";
			}
		}
		
	}

	@RequestMapping("/account/forGotPassWord")
	public String forGotPassWord() {
		return "account/forGotPassWord";
	}
}
