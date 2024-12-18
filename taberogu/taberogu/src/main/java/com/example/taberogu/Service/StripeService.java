package com.example.taberogu.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.example.taberogu.entity.User;
import com.example.taberogu.repository.UserRepository;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import com.stripe.model.checkout.Session;
import com.stripe.param.CustomerCreateParams;
import com.stripe.param.checkout.SessionCreateParams;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class StripeService {
	 private final UserRepository userRepository;
	 private static final Logger logger = LoggerFactory.getLogger(StripeService.class);
	 
	 public StripeService(UserRepository userRepository) {
		 this.userRepository = userRepository;
	 }
	// Spring FrameworkやSpring BootなどのJavaベースのフレームワークで使用されるアノテーション
		// ・Springの依存性注入機能を使用して、外部の構成ファイルや環境変数から値を注入するために使用される
		// ・"${stripe.api-key}"：外部の構成ファイルや環境変数から取得したい値を指定するプレースホルダー
		// ・stripe.api-key：プロパティキーで、外部の構成ファイル（例えば、application.propertiesやapplication.yml）や環境変数にこのキーに対応する値が設定されていることを期待している
		// 		Springは自動的にstripe.api-keyに対応する値を取得し、@Valueアノテーションが付けられたフィールドやメソッドの引数に注入する
		@Value("${stripe.api-key}")
		// stripeApiKeyフィールドに外部の構成から取得されたAPIキーを注入する
		private String stripeApiKey;
		
     // セッションを作成し、Stripeに必要な情報を返す
		public String createCheckoutSession(User user, HttpServletRequest httpServletRequest) throws StripeException {
	        
		    Stripe.apiKey = stripeApiKey;

		    // 顧客作成のパラメーターを構築
		    CustomerCreateParams customerParams = 
		        CustomerCreateParams.builder()
		            .setName(user.getName())
		            .setEmail(user.getEmail())
		            .build();
		            
		    // Stripe上に顧客を作成
		    Customer customer = Customer.create(customerParams);

		    // 顧客IDをユーザーテーブルにセット＆保存 (事前に顧客IDのフィールドがあることを前提)
		    user.setCustomerId(customer.getId());
		    userRepository.save(user);

		    // セッション作成のためのパラメーターを構築する
		    SessionCreateParams params =
		        SessionCreateParams.builder()
		            .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
		            .addLineItem(
		                SessionCreateParams.LineItem.builder()
		                    .setQuantity(1L)
		                    .setPrice("price_1QTlYlBZ4UD9z1bMerQL8aai")
		                    .build()
		            )
		            .setMode(SessionCreateParams.Mode.SUBSCRIPTION)
		            .setCustomer(customer.getId())  // ここで顧客IDを設定
//		            .setSuccessUrl("http://localhost:8080/user/success?session_id={CHECKOUT_SESSION_ID}")
		            .setSuccessUrl("http://localhost:8080/user/success?session_id={CHECKOUT_SESSION_ID}")
		            .setCancelUrl("http://localhost:8080/user/cancel")
		            .build();
		    
		    try {
		        // Stripe APIを呼び出してセッションを作成
		        Session session = Session.create(params);
		        return session.getId();
		    } catch (StripeException e) {
//		        e.printStackTrace();
//		        throw e;
		    	logger.error("Stripe API error", e);
		        throw new RuntimeException("Stripe セッションの作成中にエラーが発生しました。詳細: " + e.getMessage());
		    }
		}
}
