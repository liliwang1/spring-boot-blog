package com.codeup.myblog.controllers;

import com.codeup.myblog.models.*;
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

import javax.servlet.http.HttpServletRequest;
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

    @GetMapping("user/register")
    public String showSignupForm(Model model) {
        model.addAttribute("user", new User());
        return "users/register";
    }

    @PostMapping("user/register")
    public String saveUser(@ModelAttribute User blogger) {
        String hash = passwordEncoder.encode(blogger.getPassword());
        blogger.setPassword(hash);
        blogger.setCreateDate(new Date());
        usersDao.save(blogger);
        rolesDao.save(UserRole.blogger(blogger));

        authenticate(blogger);
        return "redirect:/posts/show";
//        return "redirect:/login";
    }

    @GetMapping("admin/register")
    public String showAdminSignupForm(Model model) {
        model.addAttribute("user", new User());
        return "users/register";
    }

    @PostMapping("admin/register")
    public String saveAdmin(@ModelAttribute User admin) {
        String hash = passwordEncoder.encode(admin.getPassword());
        admin.setPassword(hash);
        admin.setCreateDate(new Date());
        usersDao.save(admin);
        rolesDao.save(UserRole.admin(admin));

        authenticate(admin);
        return "redirect:/posts/show";
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

    @GetMapping("/dashboard")
    public String dashboard(HttpServletRequest request) {
        if (request.isUserInRole("ROLE_ADMIN")) {
            return "redirect:/dashboard/admin"; // Suppose we already have an action for this one
        }
        return "redirect:/dashboard/blogger"; // And another for this one
    }


    @GetMapping("/profile")
    public String showProfile(Model model) {
        return "users/profile";
    }
}
