package com.example.taberogu.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.taberogu.entity.Fav;
import com.example.taberogu.entity.Shop;
import com.example.taberogu.entity.User;

public interface FavRepository extends JpaRepository<Fav, Integer> {
	 public Fav findByShopAndUser(Shop shop, User user);

	public Fav getReferenceByFavId(Integer favId);

	public boolean existsByUserAndFavId(User user, int fav);

	public void deleteByUserAndFavId(User user, int fav);

	public boolean existsByUserAndShop(User user, Shop shop);

	public void deleteByUserAndShop(User user, Shop shop);

	public Page<Fav> findByUserOrderByCreatedAtDesc(User user, Pageable pageable);

	//public Page<Fav> findByHouseOrderByCreatedAtDesc(House house,Pageable pageable);
	}