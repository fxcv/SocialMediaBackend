package me.springprojects.smbackend.repositories;

import me.springprojects.smbackend.entities.SecurityAuthority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SecurityAuthorityRepository extends JpaRepository<SecurityAuthority, Integer> {

    @Query("SELECT a FROM SecurityAuthority a WHERE a.name =:name")
    public SecurityAuthority findAuthorityByName(String name);
}
