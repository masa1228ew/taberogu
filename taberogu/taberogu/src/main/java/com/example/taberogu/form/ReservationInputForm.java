package com.example.taberogu.form;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ReservationInputForm {
	@NotNull(message = "利用日を選択してください。")
	 @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate checkinDate;

//	public LocalDate getCheckinDate() {
//		 TODO 自動生成されたメソッド・スタブ
//		return checkinDate;
//	}

//	public void setCheckinDate(LocalDate checkinDate) {
//        this.checkinDate = checkinDate;
//    }

//	public LocalDate getCheckinDate() {
		// TODO 自動生成されたメソッド・スタブ
//		return null;
//	}   
	
//	 public LocalDate getCheckinDate() {
//		 String checkinDateCh = checkinDate;
//         LocalDate checkinDate = LocalDate.parse(checkinDateCh,DateTimeFormatter.ofPattern("yyyy/MM/dd"));
       
//         return LocalDate(checkinDate);
//     }
	 
}
