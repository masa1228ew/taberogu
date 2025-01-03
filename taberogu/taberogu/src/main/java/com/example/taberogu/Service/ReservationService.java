package com.example.taberogu.Service;

import java.time.LocalDate;

import org.springframework.stereotype.Service;

import com.example.taberogu.entity.Reservations;
import com.example.taberogu.entity.Shop;
import com.example.taberogu.entity.User;
import com.example.taberogu.form.ReservationRegisterForm;
import com.example.taberogu.repository.ReservationRepository;
import com.example.taberogu.repository.ShopRepository;
import com.example.taberogu.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class ReservationService {
	 private final ReservationRepository reservationRepository;  
     private final ShopRepository shopRepository;  
     private final UserRepository userRepository;  
     
     public ReservationService(ReservationRepository reservationRepository, ShopRepository shopRepository, UserRepository userRepository) {
         this.reservationRepository = reservationRepository;  
         this.shopRepository = shopRepository;  
         this.userRepository = userRepository;  
     }    
     
     @Transactional
     public void create(ReservationRegisterForm reservationRegisterForm) { 
         Reservations reservations = new Reservations();
         Shop shop = shopRepository.getReferenceById(reservationRegisterForm.getShopId());
         User user = userRepository.getReferenceById(reservationRegisterForm.getUserId());
//         LocalDate checkinDate = LocalDate.parse(reservationRegisterForm.getCheckinDate());
         LocalDate checkinDate = reservationRegisterForm.getCheckinDate(); 
                  
                 
         reservations.setShop(shop);
         reservations.setUser(user);
         reservations.setCheckinDate(checkinDate);
         
         
         reservationRepository.save(reservations);
     }    
    
    // 宿泊料金を計算する
  
}
