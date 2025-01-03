package com.example.taberogu.Service;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.taberogu.entity.PasswordResetToken;
import com.example.taberogu.entity.Role;
import com.example.taberogu.entity.User;
import com.example.taberogu.form.SignupForm;
import com.example.taberogu.form.UserEditForm;
import com.example.taberogu.repository.PasswordResetTokenRepository;
import com.example.taberogu.repository.RoleRepository;
import com.example.taberogu.repository.UserRepository;
import com.example.taberogu.repository.VerificationTokenRepository;

@Service
public class UserService {
	 private final UserRepository userRepository;
     private final RoleRepository roleRepository;
     private final PasswordEncoder passwordEncoder;
     private final PasswordResetTokenRepository passwordResetTokenRepository;
     private final  VerificationTokenRepository verificationTokenRepository;
     
     public UserService(UserRepository userRepository, RoleRepository roleRepository, 
    		 			PasswordEncoder passwordEncoder,PasswordResetTokenRepository passwordResetTokenRepository,
    		 			VerificationTokenRepository verificationTokenRepository) {
         this.userRepository = userRepository;
         this.roleRepository = roleRepository;        
         this.passwordEncoder = passwordEncoder;
         this.passwordResetTokenRepository = passwordResetTokenRepository;
         this.verificationTokenRepository = verificationTokenRepository;
     }    
     
     @Transactional
     public User create(SignupForm signupForm) {
         User user = new User();
         Role role = roleRepository.findByName("ROLE_GENERAL");
         
         user.setName(signupForm.getName());
         user.setFurigana(signupForm.getFurigana());
        
        
        
         user.setEmail(signupForm.getEmail());
         user.setPassword(passwordEncoder.encode(signupForm.getPassword()));
         user.setRole(role);
//         user.setEnabled(true);
         user.setEnabled(false);
         
         return userRepository.save(user);
     }    
     
     @Transactional
     public void update(UserEditForm userEditForm) {
         User user = userRepository.getReferenceById(userEditForm.getId());
         
         user.setName(userEditForm.getName());
         user.setFurigana(userEditForm.getFurigana());
         user.setEmail(userEditForm.getEmail());      
         
         userRepository.save(user);
     }     
     
     public boolean isEmailRegistered(String email) {
         User user = userRepository.findByEmail(email);  
         return user != null;
     }    
     public boolean isSamePassword(String password, String passwordConfirmation) {
         return password.equals(passwordConfirmation);
     }     
     @Transactional
     public void enableUser(User user) {
         user.setEnabled(true); 
         userRepository.save(user);
         passwordResetTokenRepository.deleteByUser(user);
        
     }

	 
	public void createPasswordResetTokenForUser(String email, String token) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new RuntimeException("ユーザーが見つかりません。");
        }
        PasswordResetToken myToken = new PasswordResetToken();
        myToken.setToken(token);
        myToken.setUser(user);
        passwordResetTokenRepository.deleteByUser(user);
        passwordResetTokenRepository.save(myToken);
        
      
    }

    public PasswordResetToken getPasswordResetToken(String token) {
        return passwordResetTokenRepository.findByToken(token);
    }
	
	 public boolean updatePassword(String token, String newPassword) {
	        PasswordResetToken resetToken = passwordResetTokenRepository.findByToken(token);
	        if (resetToken == null) {
	            return false;
	        }
	       
	        User user = resetToken.getUser();
	        user.setPassword(passwordEncoder.encode(newPassword));
	        userRepository.save(user);
	        passwordResetTokenRepository.delete(resetToken);
	        return true;
	    }
	 
	 public boolean isEmailChanged(UserEditForm userEditForm) {
         User currentUser = userRepository.getReferenceById(userEditForm.getId());
         return !userEditForm.getEmail().equals(currentUser.getEmail());      
     }  
	
}
