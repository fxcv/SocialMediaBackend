package me.springprojects.smbackend.services.verifications;

import lombok.AllArgsConstructor;
import me.springprojects.smbackend.entities.User;
import me.springprojects.smbackend.entities.dto.PostDTO;
import me.springprojects.smbackend.exceptions.InvalidPostArgumentException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@AllArgsConstructor
public class PostServiceVerification extends Verification{

    public void addPostVerification(PostDTO postDTO, Optional<User> userOptional){
        this.verificateUserExistence(userOptional);
        if(postDTO.getPostText()==null || postDTO.getPostCreatorName()!=null || postDTO.getPostDate()!=null) throw new InvalidPostArgumentException("Please provide only post's text content");
        if(postDTO.getPostText().length()<5) throw new InvalidPostArgumentException("Post's content should be at least of length 5");
    }
}
