package com.example.taberogu.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.taberogu.Service.ReservationService;
import com.example.taberogu.entity.Reservations;
import com.example.taberogu.entity.Shop;
import com.example.taberogu.entity.User;
import com.example.taberogu.form.ReservationInputForm;
import com.example.taberogu.form.ReservationRegisterForm;
import com.example.taberogu.repository.ReservationRepository;
import com.example.taberogu.repository.ShopRepository;
import com.example.taberogu.security.UserDetailsImpl;
@Controller
public class ReservationController {
	private final ReservationRepository reservationRepository;
	 private final ShopRepository shopRepository;
     private final ReservationService reservationService; 
    
    public ReservationController(ReservationRepository reservationRepository,ShopRepository shopRepository,ReservationService reservationService) {        
        this.reservationRepository = reservationRepository;  
        this.shopRepository = shopRepository;
        this.reservationService = reservationService;
    }    

    @GetMapping("/reservations")
    public String index(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl, @PageableDefault(page = 0, size = 10, sort = "id", direction = Direction.ASC) Pageable pageable, Model model) {
        User user = userDetailsImpl.getUser();
        Page<Reservations> reservationPage = reservationRepository.findByUserOrderByCreatedAtDesc(user, pageable);
        
        model.addAttribute("reservationPage", reservationPage);         
        
        return "reservations/index";
    }
    @GetMapping("/shop/{id}/reservations/input")
    public String input(@PathVariable(name = "id") Integer id,
                        @ModelAttribute @Validated ReservationInputForm reservationInputForm,
                        BindingResult bindingResult,
                        RedirectAttributes redirectAttributes,
                        Model model)
    {   
        Shop shop = shopRepository.getReferenceById(id);
      
        
             
        
        
        if (bindingResult.hasErrors()) {            
            model.addAttribute("shop", shop);            
            model.addAttribute("errorMessage", "予約内容に不備があります。"); 
            return "shop/show";
        }
        
        redirectAttributes.addFlashAttribute("reservationInputForm", reservationInputForm);           
        
        return "redirect:/shop/{id}/reservations/confirm";
    }    
    
    @GetMapping("/shop/{id}/reservations/confirm")
    public String confirm(@PathVariable(name = "id") Integer id,
                          @ModelAttribute ReservationInputForm reservationInputForm,
                          @AuthenticationPrincipal UserDetailsImpl userDetailsImpl,                          
                          Model model) 
    {        
        Shop shop = shopRepository.getReferenceById(id);
        User user = userDetailsImpl.getUser(); 
                
        //チェックイン日とチェックアウト日を取得する
//        LocalDate checkinDate = reservationInputForm.getCheckinDate();
       
 
        // 宿泊料金を計算する
        
        
        ReservationRegisterForm reservationRegisterForm = new ReservationRegisterForm(shop.getId(), user.getId(),  reservationInputForm.getCheckinDate());
        
        model.addAttribute("shop", shop);  
        model.addAttribute("reservationRegisterForm", reservationRegisterForm);       
        
        return "reservations/confirm";
    }    
    
    @PostMapping("/shop/{id}/reservations/create")
    public String create(@ModelAttribute ReservationRegisterForm reservationRegisterForm) {                
        reservationService.create(reservationRegisterForm);        
        
        return "redirect:/reservations?reserved";
    }
    
    @PostMapping("reservations/{id}/delete")
    public String delete(@PathVariable(name="id") Integer id,RedirectAttributes redirectAttributes) {
   	 
    	reservationRepository.deleteById(id);
   	 
//   	 System.out.println(id);
   	 
   	  redirectAttributes.addFlashAttribute("successMessage", "予約を削除しました。");
   	  
   	  return "redirect:/reservations";
    }
}
