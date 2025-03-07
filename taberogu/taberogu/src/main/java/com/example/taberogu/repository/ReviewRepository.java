package com.example.taberogu.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.taberogu.entity.Review;
import com.example.taberogu.entity.Shop;
import com.example.taberogu.entity.User;

public interface ReviewRepository extends JpaRepository<Review, Integer> {
	//public List<Review> findByHouseId(Integer houseId);
	//public List<Review> findTop10ByOrderByCreatedAtDesc(House house);
		
//		public List<Review> findTop6ByShopOrderByCreatedAtDesc(Shop Shop);
//		public Review findByShopAndUser(Shop shop, User user);
//		public long countByShop(Shop Shop);
//		public Page<Review> findByShopOrderByCreatedAtDesc(Shop shop, Pageable pageable);
	
	public List<Review> findTop6ByShopOrderByCreatedAtDesc(Shop shop);
	public Review findByShopAndUser(Shop shop, User user);
	public long countByShop(Shop shop);
	public Page<Review> findByShopOrderByCreatedAtDesc(Shop shop, Pageable pageable);
	public List<Review> findAllByShopOrderByCreatedAtDesc(Shop shop);
	public Page<Review> findAllByShopOrderByCreatedAtDesc(Shop shop,Pageable pageable);
	}

