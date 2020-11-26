package com.codeup.myblog;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;

@Controller
public class PostController {

    @GetMapping("/posts")
    public String postsIndex(Model model) {
        ArrayList<Post> posts = new ArrayList<>();
        posts.add(new Post(1, "Post 1", "This is the first post."));
        posts.add(new Post(2, "Post 2", "This is the second post."));
        model.addAttribute("posts", posts);
        return "/posts/index";
    }

    @GetMapping("/posts/{id}")
    public String post(@PathVariable long id, Model model) {
        Post post = new Post(3, "Post 3", "This is the third post.");
        model.addAttribute("post", post);
        return "/posts/show";
    }

    @GetMapping("/posts/create")
    @ResponseBody
    public String viewCreatePost() {
        return "view form to create a post";
    }

    @PostMapping("/posts/create")
    @ResponseBody
    public String createPost() {
        return "create a new post";
    }
}
