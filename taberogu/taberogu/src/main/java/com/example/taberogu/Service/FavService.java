package com.example.taberogu.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import org.springframework.stereotype.Service;

import com.example.taberogu.entity.Fav;
import com.example.taberogu.entity.Shop;
import com.example.taberogu.entity.User;
import com.example.taberogu.form.FavForm;
import com.example.taberogu.repository.FavRepository;
import com.example.taberogu.repository.ShopRepository;

import jakarta.transaction.Transactional;

@Service
public class FavService {
	private final FavRepository favRepository;
	private final ShopRepository shopRepository;
	
	public FavService(FavRepository favRepository,ShopRepository shopRepository) {
		this.favRepository = favRepository;
		this.shopRepository = shopRepository;
	}
	@Transactional
	public void create(
			Shop shop,User user,
//			@PathVariable(name = "houseId") Integer houseId,
//			@AuthenticationPrincipal UserDetailsImpl userDetailsImpl,
//			House house, 
//			UserDetailsImpl userDetailsImpl,
			 FavForm favForm
			 ) {
		 favForm.setUpdatedAt(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));

//		    User user = userDetailsImpl.getUser();
//		    House house = houseRepository.getReferenceById(houseId);
		    
		   
	  //すでにいいねしていた場合、いいねを取り消す
	  if(favRepository.existsByUserAndShop(user, shop) == true) {
	      favRepository.deleteByUserAndShop(user, shop);
	  }else {  //いいねしていなかった場合、投稿へのいいねを登録する
		 Fav fav = new Fav();
//	    fav.setFavId(favId);
	    fav.setUser(user);
	    fav.setShop(shop);
	    LocalDateTime ldt = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
	    fav.setCreatedAt(ldt);
	    fav.setUpdatedAt(ldt);
	    favRepository.save(fav);
	  }
	}
//	    return "redirect:/postmain?postdetail";
//	}
	public boolean isFav(Shop shop, User user) {
				
		 return favRepository.existsByUserAndShop(user, shop);
		
		
	}
	

}
