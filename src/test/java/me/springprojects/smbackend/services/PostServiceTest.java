package me.springprojects.smbackend.services;

import me.springprojects.smbackend.entities.Post;
import me.springprojects.smbackend.entities.User;
import me.springprojects.smbackend.entities.dto.PostDTO;
import me.springprojects.smbackend.exceptions.InvalidPostArgumentException;
import me.springprojects.smbackend.repositories.PostRepository;
import me.springprojects.smbackend.repositories.UserRepository;
import me.springprojects.smbackend.utility.UserUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@SpringBootTest
class PostServiceTest {

    @Autowired
    private PostService postService;

    @MockBean
    private PostRepository postRepository;

    @MockBean
    private UserUtil userUtil;

    @MockBean
    private UserRepository userRepository;

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

    @Test
    public void checkIfAddPost(){
        PostDTO postDTO = new PostDTO();
        postDTO.setPostText("test post");
        User user = new User();
        user.setPostsCreated(new ArrayList<>());

        given(userUtil.getUser()).willReturn(user);

        postService.addPost(postDTO);

        verify(postRepository, times(1)).save(any());
        verify(userRepository, times(1)).save(any());
        assertEquals(postDTO.getPostText(), user.getPostsCreated().get(0).getPostText());
        assertEquals(user, user.getPostsCreated().get(0).getPostCreator());
    }

    @Test
    public void checkIfGetsCurrentUserPost(){
        Post post = new Post();
        User user = new User();
        user.setUsername("testUser");
        post.setPostCreator(user);
        post.setPostDate(LocalDateTime.now());
        post.setPostText("test text");
        user.setPostsCreated(new ArrayList<>());
        user.getPostsCreated().add(post);
        given(userUtil.getUser()).willReturn(user);
        given(postRepository.getUserPosts(user)).willReturn(List.of(post));

        List<PostDTO> userPosts = postService.getCurrentUserPosts();

        assertEquals(user.getUsername(), userPosts.get(0).getPostCreatorName());
        assertEquals(post.getPostDate().toString(), userPosts.get(0).getPostDate());
        assertEquals(post.getPostText(), userPosts.get(0).getPostText());
    }

    @Test
    public void checkIfThrowsExceptionWhenPostTextIsInvalid(){
        PostDTO postDTO = new PostDTO();
        postDTO.setPostText("");

        assertThrows(InvalidPostArgumentException.class, () -> postService.addPost(postDTO));
    }

    @Test
    public void checkIfThrowsExceptionWhenMoreThanPostTextProvided(){
        PostDTO postDTO = new PostDTO();
        postDTO.setPostText("test post");
        postDTO.setPostCreatorName("test creator");
        postDTO.setPostDate("2020-09-25");

        assertThrows(InvalidPostArgumentException.class, () -> postService.addPost(postDTO));
    }

}