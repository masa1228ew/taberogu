package com.example.taberogu.form;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ReviewRegisterForm {
	
	 @NotNull
	 private Integer content;
}
