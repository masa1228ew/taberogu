package com.example.taberogu.Service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.example.taberogu.form.UserGradeRegisterForm;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class StripeService {
	 @Value("${stripe.api-key}")
     private String stripeApiKey;
	 public String createStripeSession(String userName, UserGradeRegisterForm userGradeRegisterForm, HttpServletRequest httpServletRequest) {
		   Stripe.apiKey = stripeApiKey;
         String requestUrl = new String(httpServletRequest.getRequestURL());
         SessionCreateParams params = SessionCreateParams.builder()
        	        .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
        	        .addLineItem(
        	            SessionCreateParams.LineItem.builder()
        	                .setPrice("price_1QTlYlBZ4UD9z1bMerQL8aai")  // ここで事前に作成したプランの価格IDを設定
        	                .setQuantity(1L)
        	                .build())
        	        .setMode(SessionCreateParams.Mode.SUBSCRIPTION)
        	        .setSuccessUrl(requestUrl.replaceAll("/houses/[0-9]+/reservations/confirm", "") + "/reservations?reserved")
        	        .setCancelUrl(requestUrl.replace("/reservations/confirm", ""))
        	        .setPaymentIntentData(
        	        		 SessionCreateParams.PaymentIntentData.builder()
//        	        		 .putMetadata("email",userGradeRegisterForm.getUser().getEmail())
        	        		
        	        		 .build())
        	        		.build();

        	    try {
        	        Session session = Session.create(params);
        	        return session.getUrl();
        	    } catch (StripeException e) {
        	        // エラーハンドリングを実施
        	        e.printStackTrace();
        	        return null;
        	    }
	 }
}