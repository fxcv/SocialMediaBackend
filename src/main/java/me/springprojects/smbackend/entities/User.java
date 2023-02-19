package me.springprojects.smbackend.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
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
    @Column(name = "expiration_date")
    private LocalDate expirationDate;
    private boolean locked;
    @Column(name = "credentials_expired")
    private boolean credentialsExpired;
    private boolean enabled;

    @OneToMany(mappedBy = "postCreator", fetch = FetchType.LAZY)
    private List<Post> postsCreated;

    @JoinTable(name = "user_authority", joinColumns = @JoinColumn(name = "user_id")
            , inverseJoinColumns = @JoinColumn(name = "auth_id"))
    @ManyToMany(fetch = FetchType.EAGER)
    private List<SecurityAuthority> authorities;
}
