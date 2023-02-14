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
    private String name;
    private String lastname;
    private String email;
    private String password;

    @OneToMany(mappedBy = "groupCreator", fetch = FetchType.LAZY)
    private List<Group> groupsCreated;

    @JoinTable(name = "user_group", joinColumns = @JoinColumn(name = "user"),
                                    inverseJoinColumns = @JoinColumn(name = "group"))
    @ManyToMany
    private List<Group> groupsJoined;

    @OneToMany(mappedBy = "postCreator", fetch = FetchType.LAZY)
    private List<Post> postsCreated;
}
