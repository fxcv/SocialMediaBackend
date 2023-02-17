package me.springprojects.smbackend.controllers;

import lombok.AllArgsConstructor;
import me.springprojects.smbackend.entities.dto.PostDTO;
import me.springprojects.smbackend.services.PostService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(path = "/post")
public class PostController {

    private final PostService postService;

    @PostMapping(path = "/add")
    public void addPost(@RequestBody PostDTO postDTO){
        postService.addPost(postDTO);
    }

    @GetMapping(path = "/all")
    public List<PostDTO> fetchAllPosts(){
        return postService.fetchAllPosts();
    }
}
