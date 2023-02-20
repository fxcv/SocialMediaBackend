package me.springprojects.smbackend.repositories;

import me.springprojects.smbackend.entities.Post;
import me.springprojects.smbackend.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Integer> {

    @Query("SELECT p FROM Post p WHERE p.postCreator=:user")
    public List<Post> getUserPosts(User user);
}
