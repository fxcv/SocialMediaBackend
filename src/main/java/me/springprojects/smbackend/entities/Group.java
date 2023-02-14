package me.springprojects.smbackend.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;

    @JoinColumn(name = "group_creator")
    @ManyToOne
    private User groupCreator;

    @ManyToMany(mappedBy = "groupsJoined", fetch = FetchType.LAZY)
    private List<User> usersJoined;
}
