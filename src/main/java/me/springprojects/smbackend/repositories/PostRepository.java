package me.springprojects.smbackend.repositories;

import me.springprojects.smbackend.entities.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Integer> {

}
