package com.example.taberogu.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.taberogu.entity.Shop;
import com.example.taberogu.repository.ShopRepository;

@Controller
@RequestMapping("/shop")
public class ShopController {
	 private final ShopRepository shopRepository; 
	 
	 public ShopController(ShopRepository shopRepository) {
         this.shopRepository = shopRepository;        
          
     }	
	 
	 @GetMapping
     public String index(Model model,@PageableDefault Pageable pageable, @RequestParam(name = "keyword", required = false) String keyword,
    		 @RequestParam(name = "category", required = false) String category)  {
    	 
    	 Page<Shop> shopPage;
    	 
    	  if (keyword != null && !keyword.isEmpty()) {
              shopPage = shopRepository.findByNameLike("%" + keyword + "%", pageable);                
          }
    	  else if(category != null && !category.isEmpty()){
    		  shopPage = shopRepository.findByCategory( category , pageable);
    	  }
    	  else {
              shopPage = shopRepository.findAll(pageable);
          }  
    	 model.addAttribute("shopPage",shopPage);
    	 model.addAttribute("keyword", keyword);
    	 model.addAttribute("category", category);
    	 
    	 return "shop/index";
     }
	 @GetMapping("/{id}")
     public String show(@PathVariable(name = "id") Integer id, Model model) {
         Shop shop = shopRepository.getReferenceById(id);
         
         model.addAttribute("shop", shop);         
         
         return "shop/show";
     }    
}