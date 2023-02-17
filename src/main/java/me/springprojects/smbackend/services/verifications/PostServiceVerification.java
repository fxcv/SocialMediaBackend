package me.springprojects.smbackend.services.verifications;

import lombok.AllArgsConstructor;
import me.springprojects.smbackend.entities.dto.PostDTO;
import me.springprojects.smbackend.exceptions.InvalidPostArgumentException;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class PostServiceVerification {

    public void addPostVerification(PostDTO postDTO){
        if(postDTO.getPostText()==null || postDTO.getPostCreatorName()!=null || postDTO.getPostDate()!=null) throw new InvalidPostArgumentException("Please provide only post's text content");
        if(postDTO.getPostText().length()<5) throw new InvalidPostArgumentException("Post's content should be at least of length 5");
    }
}
