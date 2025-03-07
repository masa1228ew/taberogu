package com.example.taberogu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.example.taberogu.entity.User;
import com.example.taberogu.entity.VerificationToken;

public interface VerificationTokenRepository extends JpaRepository< VerificationToken, Integer> {
    public VerificationToken findByToken(String token);
    @Transactional
   	public void deleteByUser(User user);
}
