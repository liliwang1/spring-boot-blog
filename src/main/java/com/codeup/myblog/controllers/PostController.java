package com.codeup.myblog.controllers;

import com.codeup.myblog.models.Post;
import com.codeup.myblog.models.PostRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@Controller
public class PostController {

    private final PostRepository postDao;

    public PostController(PostRepository postDao) {
        this.postDao = postDao;
    }

    @GetMapping("/posts")
    public String postsIndex(Model model) {
        model.addAttribute("posts", postDao.findAll());
        return "posts/index";
    }

    @GetMapping("/posts/{id}")
    public String post(@PathVariable long id, Model model) {
        model.addAttribute("post", postDao.getOne(id));
        return "posts/show";
    }

    @PostMapping("/posts/{id}")
    @ResponseBody
    public String editPost(@PathVariable long id,
                           @RequestParam(name = "newTitle") String newTitle,
                           @RequestParam(name = "newBody") String newBody,
                           @RequestParam(name = "deleteConfirm") String deleteConfirm,
                           Model model)
    {
        if (deleteConfirm == null) {
            Post post = new Post(id, newTitle, newBody);
            postDao.save(post);
        } else if (deleteConfirm.equals("true")) {
            postDao.delete(postDao.getOne(id));
        }
//        model.addAttribute("post", postDao.save(post));
        return "redirect: posts/" + id;
    }

    @GetMapping("/posts/create")
    public String viewCreatePost() {
        return "posts/create";
    }

    @PostMapping("/posts/create")
    @ResponseBody
    public String createPost(@RequestParam(name = "title") String title, @RequestParam(name = "body") String body) {
        Post dbpost = postDao.save(new Post(title, body));
        return "created a new post with id" + dbpost.getId();
    }
}
