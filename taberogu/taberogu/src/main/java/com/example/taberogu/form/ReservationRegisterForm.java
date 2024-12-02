package com.example.taberogu.form;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReservationRegisterForm {
	private Integer shopId;
    
    private Integer userId;    
        
    private LocalDate checkinDate;    
        

}
