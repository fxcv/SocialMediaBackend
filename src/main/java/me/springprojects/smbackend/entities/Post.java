package me.springprojects.smbackend.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "posts")
@Getter
@Setter
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "post_date")
    private LocalDateTime postDate;

    @Column(name = "post_text")
    private String postText;

    @JoinColumn(name = "post_creator")
    @ManyToOne
    private User postCreator;

}
