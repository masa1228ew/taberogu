package com.example.taberogu.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.taberogu.entity.Shop;
import com.example.taberogu.repository.ShopRepository;
import com.example.taberogu.repository.UserRepository;

@Controller
public class HomeController {
     private final ShopRepository shopRepository;
     private final UserRepository userRepository;
     
     public HomeController(ShopRepository shopRepository,UserRepository userRepository) {
         this.shopRepository = shopRepository;   
         this.userRepository = userRepository;
     }    
//     @GetMapping("/")
//     public String index(Model model,@AuthenticationPrincipal UserDetailsImpl userDetailsImpl) {
//         List<Shop> newShop = shopRepository.findTop10ByOrderByCreatedAtDesc();
//         User user = userRepository.getReferenceById(userDetailsImpl.getUser().getId());
//         model.addAttribute("newShop", newShop);
//         model.addAttribute("user",user);
//        
//        return "index";
//}
     
//     @GetMapping("/")
//     public String index(Model modell) {
//         List<Shop> newShop = shopRepository.findTop10ByOrderByCreatedAtDesc();
//         User user = userRepository.getReferenceById(userDetailsImpl.getUser().getId());
//         model.addAttribute("newShop", newShop);
//         model.addAttribute("user",user);
//        
//        return "index";
//}
     
     @GetMapping("/")
     public String index(Model model) {
         List<Shop> newShop = shopRepository.findTop10ByOrderByCreatedAtDesc();
         model.addAttribute("newShop", newShop);        
        
        return "index";
}
}