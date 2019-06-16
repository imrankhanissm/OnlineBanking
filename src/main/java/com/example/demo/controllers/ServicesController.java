package com.example.demo.controllers;

import java.util.List;

import com.example.demo.models.TransactionOutputModel;
import com.example.demo.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/services")
public class ServicesController{

	@Autowired
	UserService userService;

	@RequestMapping("")
	public String services(Authentication auth, Model model) {
		model.addAttribute("name", auth.getName());
		return "services";
	}

	@GetMapping("/deposite")
	public String deposite(Authentication auth, Model model) {
		model.addAttribute("name", auth.getName());
		return "deposite";
	}

	@PostMapping("/deposite")
	public String deposite(@RequestParam("amount") double amount, Authentication auth, Model model){
		model.addAttribute("name", auth.getName());
		String username = auth.getName();
		if(amount > 0 && userService.deposite(username, amount))	return "services";
		return "deposite";
	}

	@RequestMapping("/withdraw")
	public String withdraw(Authentication auth, Model model) {
		model.addAttribute("name", auth.getName());
		return "withdraw";
	}

	@PostMapping("/withdraw")
	public String withdraw(@RequestParam("amount") double amount, Authentication auth, Model model){
		model.addAttribute("name", auth.getName());
		String username = auth.getName();
		if(amount > 0 && userService.withdraw(username, amount))	return "services";
		return "withdraw";
	}

	@GetMapping("/transfer")
	public String transfer(Authentication auth, Model model) {
		model.addAttribute("name", auth.getName());
		return "transfer";
	}

	@PostMapping("/transfer")
	public String transfer(Model model, @RequestParam("accountNumber") long toAccountNumber, @RequestParam("amount") double amount, Authentication auth){
		model.addAttribute("name", auth.getName());
		String username = auth.getName();
		if(amount > 0 && userService.transfer(username, toAccountNumber, amount))	return "services";
		return "transfer";
	}

	@RequestMapping("/history")
	public String history(Authentication auth, Model model, @RequestParam(name = "page", defaultValue = "0") int page, @RequestParam(name = "limit", defaultValue = "10") int limit) {
		model.addAttribute("name", auth.getName());
		String username = auth.getName();
		List<TransactionOutputModel> list = userService.history(username, page, limit);
		List<TransactionOutputModel> nextList = userService.history(username, page+1, limit);
		model.addAttribute("prevPage", page-1);
		if(nextList.size() > 0)
			model.addAttribute("nextPage", page+1);
		else	
			model.addAttribute("nextPage", -1);
		model.addAttribute("transactions", list);
		return "history";
	}
}