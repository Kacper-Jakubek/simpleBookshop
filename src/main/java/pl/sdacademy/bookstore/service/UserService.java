package pl.sdacademy.bookstore.service;


import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.sdacademy.bookstore.db.UserEntity;
import pl.sdacademy.bookstore.db.TokenEntity;
import pl.sdacademy.bookstore.repository.UserRepository;
import pl.sdacademy.bookstore.repository.TokenRepository;

import javax.mail.MessagingException;
import java.util.UUID;

@Service
public class UserService {


    private TokenRepository tokenRepo;

    private MailService mailService;
    private UserRepository appUserRepo;
    private PasswordEncoder passwordEncoder;


    public UserService(UserRepository appUserRepo, PasswordEncoder passwordEncoder, TokenRepository tokenRepo, MailService mailService) {
        this.appUserRepo = appUserRepo;
        this.passwordEncoder = passwordEncoder;
        this.tokenRepo = tokenRepo;
        this.mailService = mailService;
    }

    public void addUser(UserEntity userEntity) {
        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
        userEntity.setRole("ROLE_USER");
        appUserRepo.save(userEntity);
        sendToken(userEntity);
    }

    private void sendToken(UserEntity userEntity) {
        String tokenValue = UUID.randomUUID().toString();
        TokenEntity tokenEntity = new TokenEntity();
        tokenEntity.setValue(tokenValue);
        tokenEntity.setAppUser(userEntity);
        tokenRepo.save(tokenEntity);
        String url = "http://localhost:8080/token?value=" + tokenValue;

        try {
            mailService.sendMail(userEntity.getUsername(), "Potwierdź rejestrację w simpleBookshop!", url, false);
        } catch (MessagingException e) {
            e.printStackTrace();
        }


    }


}
