package com.toll.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.toll.service.CategoryService;
import com.toll.service.ProductService;

@Controller
public class HomeController 
{
	@Autowired
	CategoryService cs;
	
	@Autowired
	ProductService ps;
	
	@GetMapping({"/","/home"})
	public String home(Model m)
	{
		return "index";
		
	}
	
	@GetMapping("/shop")
	public String shop(Model m)
	{
		m.addAttribute("categories",cs.getAllCategory());
		m.addAttribute("products",ps.getAllProduct());
		return "shop";
		
	}
	
	@GetMapping("/shop/category/{id}")
	public String shopByCategory(Model m,@PathVariable int id)
	{
		m.addAttribute("categories",cs.getAllCategory());
		m.addAttribute("products",ps.getAllProductsByCategoryById(id));
		return "shop";
		
	}
	
	@GetMapping("/shop/viewproduct/{id}")
	public String viewProduct(Model m,@PathVariable long id)
	{
		
		m.addAttribute("product",ps.findProductById(id).get());
		return "viewProduct";
		
	}
	
	
	
}
