package com.example.taberogu.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.taberogu.Service.StripeService;
import com.example.taberogu.Service.UserService;
import com.example.taberogu.entity.User;
import com.example.taberogu.form.UserEditForm;
import com.example.taberogu.repository.UserRepository;
import com.example.taberogu.security.UserDetailsImpl;
import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import com.stripe.model.Subscription;
import com.stripe.model.checkout.Session;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/user")
public class UserController {
 private final UserRepository userRepository;    
 private final UserService userService; 
 private final StripeService stripeService;     
 public UserController(UserRepository userRepository, UserService userService, StripeService stripeService) {
         this.userRepository = userRepository; 
         this.userService = userService; 
         this.stripeService = stripeService;
     }    
     
     @GetMapping
     public String index(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl, Model model) {         
         User user = userRepository.getReferenceById(userDetailsImpl.getUser().getId());  
         
         model.addAttribute("user", user);
         
         return "user/index";
     }
     @GetMapping("/edit")
     public String edit(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl, Model model) {        
         User user = userRepository.getReferenceById(userDetailsImpl.getUser().getId());  
         UserEditForm userEditForm = new UserEditForm(user.getId(), user.getName(), user.getFurigana(),  user.getEmail());
         
         model.addAttribute("userEditForm", userEditForm);
         
         return "user/edit";
     }    
     @PostMapping("/update")
     public String update(@ModelAttribute @Validated UserEditForm userEditForm, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
         // メールアドレスが変更されており、かつ登録済みであれば、BindingResultオブジェクトにエラー内容を追加する
         if (userService.isEmailChanged(userEditForm) && userService.isEmailRegistered(userEditForm.getEmail())) {
             FieldError fieldError = new FieldError(bindingResult.getObjectName(), "email", "すでに登録済みのメールアドレスです。");
             bindingResult.addError(fieldError);                       
         }
         
         if (bindingResult.hasErrors()) {
             return "user/edit";
         }
         
         userService.update(userEditForm);
         redirectAttributes.addFlashAttribute("successMessage", "会員情報を編集しました。");
         
         return "redirect:/user";
     }    
     
     @GetMapping("/{id}/grade")
     public String grade(@PathVariable(name = "id") Integer id, @AuthenticationPrincipal UserDetailsImpl userDetailsImpl,  
             HttpServletRequest httpServletRequest,
            Model model) {
    	 User user = userDetailsImpl.getUser(); 
//    	 UserGradeRegisterForm userGradeRegisterForm = new userGradeRegisterForm( user.getId(),user.getEmail() );
         
//    	  String sessionId = stripeService.createStripeSession( user.getName(), httpServletRequest);
//    	  model.addAttribute("userGradenRegisterForm", userGradeRegisterForm);  
//          model.addAttribute("sessionId", sessionId);
          
          return "user/{id}/grade";
     }
     
//     サブスク料金の支払い
     @PostMapping("/create-checkout-session")
     public ResponseEntity<String> subscribeUser(@AuthenticationPrincipal UserDetailsImpl userDetails,
             @RequestParam String paymentMethodId) {
    	 System.out.println("テスト");
try {
User user = userRepository.getReferenceById(userDetails.getUser().getId());

// Stripeの顧客IDを確認し、必要なら作成
if (user.getCustomerId() == null) {
Customer customer = stripeService.createCustomer(user);
user.setCustomerId(customer.getId());
userRepository.save(user);
}

System.out.println("テスト2");
System.out.println("Name: " + user.getName());
// 支払い方法を顧客に追加
stripeService.attachPaymentMethodToCustomer(user.getCustomerId(), paymentMethodId);

// 事前に作成したプランIDでサブスクリプションを作成
String planId = "price_1QTlYlBZ4UD9z1bMerQL8aai"; // 事前にStripeで作成したプランIDを指定します
System.out.println(planId);
Subscription subscription = stripeService.createSubscription(user.getCustomerId(), planId);


return ResponseEntity.ok("Subscription successful: " + subscription.getId());

} catch (StripeException e) {
e.printStackTrace();
return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
.body("Error while creating subscription: " + e.getMessage());
}
}


//     public String createCheckoutSession(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl, HttpServletRequest httpServletRequest, Model model
//    		                             ,@RequestParam String name, 
//                                         @RequestParam String email, 
//                                         @RequestParam String paymentMethodId
//    		                               ,@RequestParam Map<String, Object> customerParams
//    		 ){
//    	 System.out.println("テスト");
//    	 User user = userRepository.getReferenceById(userDetailsImpl.getUser().getId());
//     	User user = userRepository.getByName(userDetailsImpl.getUser().getName());
//    	 System.out.println("テスト名前");
//    	 Optional<User> optionalUser = userRepository.findByName(userDetailsImpl.getUser().getName());
//    	 if (optionalUser.isPresent()) {
//    	     User user = optionalUser.get();
    	     // userを使用する処理
//    	 } else {
//    	     throw new UsernameNotFoundException("ユーザーが見つかりません");
//    	 }
//         try {
//         	stripeServiceの決済セッション(createCheckoutSession())を実行
//             String sessionId = stripeService.createCustomer(user, httpServletRequest);
//        	 Customer customer = stripeService.createCustomer(name, email, paymentMethodId);
//        	 Customer customer = stripeService.createCustomer(user);
        	 
//        	 model.addAttribute("customerId", customer.getId());
//             model.addAttribute("customerName", customer.getName());
//             model.addAttribute("customerEmail", customer.getEmail());

//             System.out.println("Customer created successfully: " + customer.getId());
             
//        	  // customerParamsから必要な値を取得
//        	    String name = (String) customerParams.get("name");
//        	    String email = (String) customerParams.get("email");

        	    // 必要な処理を実行
//        	    System.out.println("Name: " + customer.getName());
//        	    System.out.println("Email: " + customer.getEmail());

//        	    model.addAttribute("name",name);
//        	    model.addAttribute("email",email);

//             Customer customer = stripeService.createCustomer(name, email);
//             return ResponseEntity.ok("Customer created with ID: " + customer.getId());
             
//        	    String price_1QTlYlBZ4UD9z1bMerQL8aai;
//				String planId = "price_1QTlYlBZ4UD9z1bMerQL8aai";
        	    
//             Subscription subscription = stripeService.createSubscription(customer.getId(), planId);
//            try {
//             System.out.println("テストサブスク");
//             PaymentMethod paymentMethod = stripeService.getDefaultPaymentMethod(customer.getId());
//             stripeService.attachPaymentMethodToCustomer(customerId, paymentMethodId);
//             model.addAttribute("paymentMethod",paymentMethod);
//            }
//            catch (StripeException e) {
//                e.printStackTrace();
//                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                                     .body("Failed to attach payment method: " + e.getMessage());
//            }
//             model.addAttribute("subscriptionId", subscription.getId());
             // フロントエンドにセッションIDを渡す
//             model.addAttribute("sessionId", sessionId);
//             model.addAttribute("sessionId", customer);
//             System.out.println("テストトライ2");
//             System.out.println("Session ID: " + sessionId); 
//             System.out.println("Session ID: " + customer);
//             System.out.println("Received name: " + name);
//             System.out.println("Received email: " + email);
//             System.out.println("Received paymentMethodId: " + paymentMethodId);
//             
//             return "redirect:/"; 
             
//         } 
//         catch (Exception e) {
             // エラーハンドリング
//             model.addAttribute("error", e.getMessage());
//             return "error"; e.printStackTrace();
//        	    throw new RuntimeException("予期しないエラーが発生しました。", e);
//        	 e.printStackTrace();
//        	    model.addAttribute("error", e.getMessage());
//        	    return "error";
         
//         }
         
     
     
 //  サブスク料金支払処理が成功した後の処理
     @GetMapping("/success")
     public String success(@RequestParam("session_id") String sessionId, Model model, RedirectAttributes redirectAttributes) {
         // セッションIDから関連するサブスクリプション情報を取得
         try {
             Session session = Session.retrieve(sessionId);
             // 顧客情報（例として、顧客IDを取得）
             String customerId = session.getCustomer();
             // 顧客情報からユーザーを特定（仮定として、顧客IDをユーザーテーブルのフィールドとして保持）
             User user = userRepository.findByCustomerId(customerId).orElseThrow();
//             user.setSubscriptionStartDate(LocalDate.now());
//             user.setSubscriptionEndDate(LocalDate.now().plusMonths(1));
             
             userRepository.save(user);
             
             redirectAttributes.addFlashAttribute("message", "支払処理が完了しました");
             return "redirect:/";
         } catch (StripeException e) {
             model.addAttribute("error", e.getMessage());
             return "error";
         }
     }
//     @GetMapping("/success")
//     public String successPage(@RequestParam("session_id") String sessionId, Model model) {
         // ここでサブスクリプション成功の処理を追加
//         model.addAttribute("message", "支払いが成功しました。");
//         return "success";  // success.htmlテンプレートを表示
//     }

     @GetMapping("/cancel")
     public String cancelPage(Model model) {
         model.addAttribute("message", "支払いがキャンセルされました。再度試してください。");
         return "cancel";  // cancel.htmlテンプレートを表示
     }
}
