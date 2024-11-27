package com.example.taberogu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.example.taberogu.entity.PasswordResetToken;
import com.example.taberogu.entity.User;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Integer> {
	 public PasswordResetToken findByToken(String token);
	 @Transactional
	public void deleteByUser(User user);
	

	
}
