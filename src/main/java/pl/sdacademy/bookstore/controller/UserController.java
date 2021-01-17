package pl.sdacademy.bookstore.controller;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.sdacademy.bookstore.db.TokenEntity;
import pl.sdacademy.bookstore.repository.UserRepository;
import pl.sdacademy.bookstore.repository.TokenRepository;
import pl.sdacademy.bookstore.service.UserService;
import pl.sdacademy.bookstore.db.UserEntity;

import java.security.Principal;
import java.util.Collection;

@Controller
public class UserController {

    private UserService userService;
    private UserRepository appUserRepo;
    private TokenRepository tokenRepo;

    public UserController(UserService userService, UserRepository appUserRepo, TokenRepository tokenRepo) {
        this.userService = userService;
        this.appUserRepo = appUserRepo;
        this.tokenRepo = tokenRepo;
    }

    @GetMapping("/user-test")
    public String userTest(Principal principal, Model model) {
        model.addAttribute("name", principal.getName());
        Collection<? extends GrantedAuthority> authorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        Object details = SecurityContextHolder.getContext().getAuthentication().getDetails();
        model.addAttribute("authorities", authorities);
        model.addAttribute("details", details);
        return "user-test";
    }

    @GetMapping("/sign-up")
    public String singup(Model model) {
        model.addAttribute("user", new UserEntity());
        return "sign-up";
    }

    @PostMapping("/register")
    public String register(UserEntity userEntity) {
        userService.addUser(userEntity);
        return "sign-up";
    }

    @GetMapping("/token")
    public String singup(@RequestParam String value) {
        TokenEntity byValue = tokenRepo.findByValue(value);
        UserEntity userEntity = byValue.getAppUser();
        userEntity.setEnabled(true);
        appUserRepo.save(userEntity);
        return "user-test";
    }
}
