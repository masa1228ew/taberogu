package com.example.taberogu.Service;

import org.springframework.stereotype.Service;

import com.example.taberogu.entity.Review;
import com.example.taberogu.entity.Shop;
import com.example.taberogu.entity.User;
import com.example.taberogu.form.ReviewEditForm;
import com.example.taberogu.form.ReviewRegisterForm;
import com.example.taberogu.repository.ReviewRepository;

import jakarta.transaction.Transactional;

@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;        
    
    public ReviewService(ReviewRepository reviewRepository) {        
        this.reviewRepository = reviewRepository;        
    }     
    
    @Transactional
    public void create(Shop shop, User user,ReviewRegisterForm reviewRegisterForm) {
   	 Review review = new Review();
   	
   	 review.setShop(shop);                
        review.setUser(user);
   	 review.setContent(reviewRegisterForm.getContent());
   	
   	 
   	 reviewRepository.save(review);
    }
    
    public boolean hasUserAlreadyReviewed(Shop shop, User user) {
        return reviewRepository.findByShopAndUser(shop, user) != null;
    }
    @Transactional
    public void update(ReviewEditForm reviewEditForm) {
        Review review = reviewRepository.getReferenceById(reviewEditForm.getId());
       
        
       
//        review.setHouse(house);                
//        review.setUser(user);
//        review.setId(reviewEditForm.getId());                
        review.setContent(reviewEditForm.getContent());
//        review.setContent(reviewEditForm.getContent());
        
                    
        reviewRepository.save(review);
    }    
   
    
}