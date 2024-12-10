package com.example.taberogu.form;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CategoryRegisterForm {
	 @NotBlank(message = "カテゴリを入力してください。")
     private String category;
}
