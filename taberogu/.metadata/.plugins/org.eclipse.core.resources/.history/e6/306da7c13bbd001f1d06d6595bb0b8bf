package com.example.taberogu.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.taberogu.Service.StripeService;
import com.example.taberogu.Service.UserService;
import com.example.taberogu.entity.User;
import com.example.taberogu.form.UserEditForm;
import com.example.taberogu.repository.UserRepository;
import com.example.taberogu.security.UserDetailsImpl;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/user")
public class UserController {
 private final UserRepository userRepository;    
 private final UserService userService; 
 private final StripeService stripeService;     
 public UserController(UserRepository userRepository, UserService userService, StripeService stripeService) {
         this.userRepository = userRepository; 
         this.userService = userService; 
         this.stripeService = stripeService;
     }    
     
     @GetMapping
     public String index(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl, Model model) {         
         User user = userRepository.getReferenceById(userDetailsImpl.getUser().getId());  
         
         model.addAttribute("user", user);
         
         return "user/index";
     }
     @GetMapping("/edit")
     public String edit(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl, Model model) {        
         User user = userRepository.getReferenceById(userDetailsImpl.getUser().getId());  
         UserEditForm userEditForm = new UserEditForm(user.getId(), user.getName(), user.getFurigana(),  user.getEmail());
         
         model.addAttribute("userEditForm", userEditForm);
         
         return "user/edit";
     }    
     @PostMapping("/update")
     public String update(@ModelAttribute @Validated UserEditForm userEditForm, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
         // メールアドレスが変更されており、かつ登録済みであれば、BindingResultオブジェクトにエラー内容を追加する
         if (userService.isEmailChanged(userEditForm) && userService.isEmailRegistered(userEditForm.getEmail())) {
             FieldError fieldError = new FieldError(bindingResult.getObjectName(), "email", "すでに登録済みのメールアドレスです。");
             bindingResult.addError(fieldError);                       
         }
         
         if (bindingResult.hasErrors()) {
             return "user/edit";
         }
         
         userService.update(userEditForm);
         redirectAttributes.addFlashAttribute("successMessage", "会員情報を編集しました。");
         
         return "redirect:/user";
     }    
     
     @GetMapping("/{id}/grade")
     public String grade(@PathVariable(name = "id") Integer id, @AuthenticationPrincipal UserDetailsImpl userDetailsImpl,  
             HttpServletRequest httpServletRequest,
            Model model) {
    	 User user = userDetailsImpl.getUser(); 
//    	 UserGradeRegisterForm userGradeRegisterForm = new userGradeRegisterForm( user.getId(),user.getEmail() );
         
//    	  String sessionId = stripeService.createStripeSession( user.getName(), httpServletRequest);
//    	  model.addAttribute("userGradenRegisterForm", userGradeRegisterForm);  
//          model.addAttribute("sessionId", sessionId);
          
          return "user/{id}/grade";
     }
}
