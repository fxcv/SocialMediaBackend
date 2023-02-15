package me.springprojects.smbackend.services;

import me.springprojects.smbackend.entities.Post;
import me.springprojects.smbackend.entities.User;
import me.springprojects.smbackend.entities.dto.PostDTO;
import me.springprojects.smbackend.exceptions.InvalidPostArgumentException;
import me.springprojects.smbackend.repositories.PostRepository;
import me.springprojects.smbackend.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@SpringBootTest
class PostServiceTest {

    @Autowired
    private PostService postService;

    @MockBean
    private PostRepository postRepository;

    @MockBean
    private UserRepository userRepository;

    @Test
    public void createsPost(){
        User user = new User();
        user.setPostsCreated(new ArrayList<>());
        PostDTO postDTO = new PostDTO();
        postDTO.setPostText("Example post 1");
        given(userRepository.findById(any())).willReturn(Optional.of(user));

        postService.addPost(postDTO, 999);

        verify(userRepository, times(1)).save(any());
        verify(postRepository, times(1)).save(any());
        assertEquals(postDTO.getPostText(), user.getPostsCreated().get(0).getPostText());
        assertEquals(user, user.getPostsCreated().get(0).getPostCreator());
    }

    @Test
    public void throwsExceptionWhenWrongPostArguments(){
        PostDTO postDTO = new PostDTO();
        given(userRepository.findById(any())).willReturn(Optional.of(new User()));

        assertThrows(InvalidPostArgumentException.class, () -> postService.addPost(postDTO, 999));
    }

    @Test
    public void checkIfFetchesPosts(){
        Post post1 = new Post(); Post post2 = new Post();
        User user = new User(); user.setName("example");
        post1.setPostCreator(user); post2.setPostCreator(user);
        post1.setPostText("example text1"); post2.setPostText("example text2");
        post1.setPostDate(LocalDateTime.now()); post2.setPostDate(LocalDateTime.now());
        given(postRepository.findAll()).willReturn(List.of(post1, post2));

        List<PostDTO> posts = postService.fetchAllPosts();

        assertEquals(user.getName(), posts.get(0).getPostCreatorName());
        assertEquals(user.getName(), posts.get(1).getPostCreatorName());
        assertEquals(post1.getPostText(), posts.get(0).getPostText());
        assertEquals(post2.getPostText(), posts.get(1).getPostText());
    }

}