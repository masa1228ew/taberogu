package com.example.taberogu.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.taberogu.entity.User;
import com.example.taberogu.repository.PasswordResetTokenRepository;
import com.example.taberogu.repository.UserRepository;

@Service
public class PassService {
	@Autowired
    private PasswordResetTokenRepository passwordResetTokenRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
	}
	
    

//    public boolean updatePassword(String token, String newPassword) {
//        PasswordResetToken resetToken = passwordResetTokenRepository.findByToken(token);
//        if (resetToken == null) {
//            return false;
//        }
//        Calendar cal = Calendar.getInstance();
//        if ((resetToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
//            return false;
//        }
//        User user = resetToken.getUser();
//        user.setPassword(passwordEncoder.encode(newPassword));
//        userRepository.save(user);
//        passwordResetTokenRepository.delete(resetToken);
//        return true;
//    }

//    private Date calculateExpiryDate(int expiryTimeInMinutes) {
//        Calendar cal = Calendar.getInstance();
//        cal.setTime(new Date());
//        cal.add(Calendar.MINUTE, expiryTimeInMinutes);
//        return new Date(cal.getTime().getTime());
//    }
}
