package com.example.taberogu.Service;

import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;

import com.example.taberogu.entity.Category;
import com.example.taberogu.form.CategoryEditForm;
import com.example.taberogu.form.CategoryRegisterForm;
import com.example.taberogu.repository.CategoryRepository;

import jakarta.transaction.Transactional;

@Service
public class CategoryService {
	 private final CategoryRepository categoryRepository;
	 
	 public CategoryService( CategoryRepository categoryRepository) {
//         this.shopRepository = shopRepository;   
         this.categoryRepository = categoryRepository;
     }    
	 
	 public Category findById(Integer id) {
         return categoryRepository.findById(id).orElseThrow(() -> new NoSuchElementException("カテゴリが見つかりません"));
     }
	 public void updateCategory(CategoryEditForm form) {
         Category category = categoryRepository.findById(form.getId()).orElseThrow(() -> new NoSuchElementException("カテゴリが見つかりません"));
         category.setCategory(form.getCategory());
         

         
         categoryRepository.save(category);
     }
	 
	  @Transactional
	     public void create(CategoryRegisterForm categoryRegisterForm) {
	         Category category = new Category();        
//	         MultipartFile imageFile = categoryRegisterForm.getImageFile();
	         
//	         if (!imageFile.isEmpty()) {
//	             String imageName = imageFile.getOriginalFilename(); 
//	             String hashedImageName = generateNewFileName(imageName);
//	             Path filePath = Paths.get("src/main/resources/static/storage/" + hashedImageName);
//	             copyImageFile(imageFile, filePath);
//	             shop.setImageName(hashedImageName);
//	         }
	         
	         category.setCategory(categoryRegisterForm.getCategory()); 
	         categoryRepository.save(category);
	        
	     }  
	 
	   @Transactional
	     public void update(CategoryEditForm categoryEditForm) {
	         Category category = categoryRepository.getReferenceById(categoryEditForm.getId());
//	         MultipartFile imageFile = shopEditForm.getImageFile();
	         
//	         if (!imageFile.isEmpty()) {
//	             String imageName = imageFile.getOriginalFilename(); 
//	             String hashedImageName = generateNewFileName(imageName);
//	             Path filePath = Paths.get("src/main/resources/static/storage/" + hashedImageName);
//	             copyImageFile(imageFile, filePath);
//	             shop.setImageName(hashedImageName);
//	         }
	         
	         category.setCategory(categoryEditForm.getCategory());                
//	         shop.setDescription(shopEditForm.getDescription());
//	         shop.setCategoryId(shopEditForm.getCategory());
	        
//	         shop.setAddress(shopEditForm.getAddress());
//	         shop.setPhoneNumber(shopEditForm.getPhoneNumber());
//	         shop.setEmail(shopEditForm.getEmail());
	                     
	         categoryRepository.save(category);
	     }
	     
}
