package me.springprojects.smbackend.repositories;

import me.springprojects.smbackend.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM User u WHERE u.email =:email")
    public boolean checkIfEmailExists(String email);

    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM User u WHERE u.username =:username")
    public boolean checkIfUserExists(String username);

    @Query("SELECT u FROM User u WHERE u.username =:username")
    public Optional<User> findUserByUsername(String username);
}
