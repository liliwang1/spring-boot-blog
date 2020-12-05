package com.codeup.myblog.controllers;

import com.codeup.myblog.models.Post;
import com.codeup.myblog.models.PostRepository;
import com.codeup.myblog.models.UserRepository;
import com.codeup.myblog.services.EmailService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class PostController {

    private final PostRepository postDao;
    private final UserRepository userDao;
    private final EmailService emailService;

    public PostController(PostRepository postDao, UserRepository userDao, EmailService emailService) {
        this.postDao = postDao;
        this.userDao = userDao;
        this.emailService = emailService;
    }

    @GetMapping("/posts")
    public String postsIndex(Model model) {
        model.addAttribute("posts", postDao.findAll());
        return "posts/index";
    }

    // send post data to the whole page
    @GetMapping("/posts/{id}")
    public String showPost(@PathVariable long id, Model model) {
        model.addAttribute("post", postDao.getOne(id));
        return "posts/show";
    }

    // send post data to the edit form
    @GetMapping("/posts/{id}/edit")
    public String showEditForm(@PathVariable long id, Model model) {
        model.addAttribute("post", postDao.getOne(id));
        return "posts/show";
    }

    @PostMapping("/posts/{id}/edit")
    public String editPost(@PathVariable long id, @ModelAttribute Post editedPost) {
        editedPost.setUser(postDao.getOne(id).getUser());
        postDao.save(editedPost);
        return "redirect:/posts/" + id;
    }

    @PostMapping("/posts/{id}/delete")
    public String deletePost(@PathVariable long id,
                             @RequestParam(name = "delete") String deleteConfirm)
    {
        System.out.println(deleteConfirm);
        postDao.delete(postDao.getOne(id));
        return "redirect:/posts/";
    }

    @GetMapping("/posts/create")
    public String viewCreatePost(Model model) {
        model.addAttribute("post", new Post());
        return "posts/create";
    }

    @PostMapping("/posts/create")
    public String createPost(@ModelAttribute Post post) {
        post.setUser(userDao.getOne(2L));
        Post dbpost = postDao.save(post);
        emailService.prepareAndSend(dbpost, "you have created a new post", "you have successfully created a post with id " + dbpost.getId());
        return "redirect:/posts/" + dbpost.getId();
    }
}
