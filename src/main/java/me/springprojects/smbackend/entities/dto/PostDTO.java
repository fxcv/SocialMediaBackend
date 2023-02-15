package me.springprojects.smbackend.entities.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostDTO {

    private String postCreatorName;
    private String postDate;
    private String postText;
}
