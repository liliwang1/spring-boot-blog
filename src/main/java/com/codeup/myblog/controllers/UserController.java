package com.codeup.myblog.controllers;

import com.codeup.myblog.models.RoleRepository;
import com.codeup.myblog.models.User;
import com.codeup.myblog.models.UserRepository;
import com.codeup.myblog.models.UserWithRoles;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Date;

@Controller
public class UserController {
    private UserRepository usersDao;
    private PasswordEncoder passwordEncoder;
    private RoleRepository rolesDao;

    public UserController(UserRepository usersDao, PasswordEncoder passwordEncoder, RoleRepository rolesDao) {
        this.usersDao = usersDao;
        this.passwordEncoder = passwordEncoder;
        this.rolesDao = rolesDao;
    }

    @GetMapping("/register")
    public String showSignupForm(Model model){
        model.addAttribute("user", new User());
        return "users/register";
    }

    @PostMapping("/register")
    public String saveUser(@ModelAttribute User user){
        String hash = passwordEncoder.encode(user.getPassword());
        user.setPassword(hash);
        user.setCreateDate(new Date());
        usersDao.save(user);

        authenticate(user);
        return "/posts/show";
//        return "redirect:/login";
    }

    private void authenticate(User user) {
        // Notice how we're using an empty list for the roles
        UserDetails userDetails = new UserWithRoles(user, rolesDao.ofUserWith(user.getUserName()));
        Authentication auth = new UsernamePasswordAuthenticationToken(
                userDetails,
                userDetails.getPassword(),
                userDetails.getAuthorities()
        );
        SecurityContext context = SecurityContextHolder.getContext();
        context.setAuthentication(auth);
    }

    @GetMapping("/profile")
    public String showProfile(Model model) {
        return "users/profile";
    }
}
