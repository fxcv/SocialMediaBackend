package me.springprojects.smbackend.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String username;
    private String email;
    private String password;

    @OneToMany(mappedBy = "postCreator", fetch = FetchType.LAZY)
    private List<Post> postsCreated;

    @JoinTable(name = "user_authority", joinColumns = @JoinColumn(name = "user_id")
            , inverseJoinColumns = @JoinColumn(name = "auth_id"))
    @ManyToMany(fetch = FetchType.EAGER)
    private List<SecurityAuthority> authorities;
}
