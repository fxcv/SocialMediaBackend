package me.springprojects.smbackend.services;

import me.springprojects.smbackend.entities.Post;
import me.springprojects.smbackend.entities.User;
import me.springprojects.smbackend.entities.dto.PostDTO;
import me.springprojects.smbackend.repositories.PostRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@SpringBootTest
class PostServiceTest {

    @Autowired
    private PostService postService;

    @MockBean
    private PostRepository postRepository;

    @Test
    public void checkIfFetchesPosts(){
        Post post1 = new Post(); Post post2 = new Post();
        User user = new User(); user.setUsername("example");
        post1.setPostCreator(user); post2.setPostCreator(user);
        post1.setPostText("example text1"); post2.setPostText("example text2");
        post1.setPostDate(LocalDateTime.now()); post2.setPostDate(LocalDateTime.now());
        given(postRepository.findAll()).willReturn(List.of(post1, post2));

        List<PostDTO> posts = postService.fetchAllPosts();

        assertEquals(user.getUsername(), posts.get(0).getPostCreatorName());
        assertEquals(user.getUsername(), posts.get(1).getPostCreatorName());
        assertEquals(post1.getPostText(), posts.get(0).getPostText());
        assertEquals(post2.getPostText(), posts.get(1).getPostText());
    }

}