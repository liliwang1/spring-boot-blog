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

    // need fixing below
    @PostMapping("/posts/{id}/edit")
    public String editPost(@PathVariable long id,
                           @RequestParam(name = "title") String newTitle,
                           @RequestParam(name = "body") String newBody,
                           Model model) {
        Post post = new Post(id, newTitle, newBody);
        postDao.save(post);
//        model.addAttribute("post", postDao.save(post));
        return "redirect:/posts/" + id;
    }

    @PostMapping("/posts/{id}/delete")
    public String deletePost(@PathVariable long id,
                             @RequestParam(name = "delete") String deleteConfirm,
                             Model model) {
        System.out.println(deleteConfirm);
        postDao.delete(postDao.getOne(id));
//        model.addAttribute("post", postDao.save(post));
        return "redirect:/posts/";
    }


    // test below
//    @PostMapping("/posts/edit")
//    @ResponseBody
//    public String editPost(@RequestParam(name = "id") long id,
//                           @RequestParam(name = "title") String newTitle,
//                           @RequestParam(name = "body") String newBody,
//                           Model model) {
//
//        Post post = new Post(id, newTitle, newBody);
//        postDao.save(post);
//        return "redirect: posts/" + id;
//    }
//
//    @PostMapping("/posts/new")
//    @ResponseBody
//    public String editPost(@RequestParam(name = "id") long id,
//                           @RequestParam(name = "delete") String deleteConfirm,
//                           Model model) {
//        System.out.println(deleteConfirm);
//        postDao.delete(postDao.getOne(id));
//        return "redirect: posts/" + id;
//    }
    // test above

    @GetMapping("/posts/create")
    public String viewCreatePost() {
        return "posts/create";
    }

    @PostMapping("/posts/create")
    public String createPost(@RequestParam(name = "title") String title, @RequestParam(name = "body") String body) {
        Post dbpost = postDao.save(new Post(title, body));
        return "redirect:/posts/" + dbpost.getId();
    }
}
