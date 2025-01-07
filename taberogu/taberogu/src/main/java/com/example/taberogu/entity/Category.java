package com.example.taberogu.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name="category")
@Data

public class Category {
	 @Id
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	 @Column(name="id")
	 private Integer id;
	 
	 @Column(name="category")
	 private String category;

//	public Category getName() {
//		 TODO 自動生成されたメソッド・スタブ
//		return null;
//	}
	 
//	  @OneToMany(mappedBy = "category")
//	    private List<Shop> shops;
}
