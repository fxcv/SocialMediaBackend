package me.springprojects.smbackend.services;

import lombok.AllArgsConstructor;
import me.springprojects.smbackend.entities.Post;
import me.springprojects.smbackend.entities.User;
import me.springprojects.smbackend.entities.dto.PostDTO;
import me.springprojects.smbackend.repositories.PostRepository;
import me.springprojects.smbackend.repositories.UserRepository;
import me.springprojects.smbackend.services.verifications.PostServiceVerification;
import me.springprojects.smbackend.utility.UserUtil;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final PostServiceVerification postServiceVerification;
    private final UserUtil userUtil;

    public void addPost(PostDTO postDTO){
        postServiceVerification.addPostVerification(postDTO);
        Post post = new Post();
        User user = userUtil.getUser();
        post.setPostText(postDTO.getPostText());
        post.setPostDate(LocalDateTime.now());
        post.setPostCreator(user);
        user.getPostsCreated().add(post);
        postRepository.save(post);
        userRepository.save(user);
    }

    public List<PostDTO> fetchAllPosts(){
        return postRepository.findAll().stream()
                                       .map(post -> {
                                           PostDTO postDTO = new PostDTO();
                                           postDTO.setPostCreatorName(post.getPostCreator().getUsername());
                                           postDTO.setPostDate(post.getPostDate().toString());
                                           postDTO.setPostText(post.getPostText());
                                           return postDTO;
                                       })
                                       .collect(Collectors.toList());
    }
}
