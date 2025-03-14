package com.mdtalalwasim.crud.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class ProductViewController {
	
	@GetMapping("/")
	public String index(){
		System.out.println("load index");
		
		
		return "index";
	}
	
	@GetMapping("/products")
	public String showProductForm(){
		System.out.println("load product form");
		
		
		return "product/product-add-form";
	}
	
	@GetMapping("/products-list")
	public String listOfProduct(){
		return "product/product-list";
	}
	
	@GetMapping("/edit-product")
	public String editProductForm(){
		
		return "product/product-add-form";
	}
}
