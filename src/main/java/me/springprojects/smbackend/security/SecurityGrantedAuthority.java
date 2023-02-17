package me.springprojects.smbackend.security;

import lombok.AllArgsConstructor;
import me.springprojects.smbackend.entities.SecurityAuthority;
import org.springframework.security.core.GrantedAuthority;

@AllArgsConstructor
public class SecurityGrantedAuthority implements GrantedAuthority {

    private final SecurityAuthority securityAuthority;

    @Override
    public String getAuthority() {
        return securityAuthority.getName();
    }
}
