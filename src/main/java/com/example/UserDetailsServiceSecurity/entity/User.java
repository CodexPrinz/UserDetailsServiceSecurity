package com.example.UserDetailsServiceSecurity.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "User_Security")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String email;
    private String username;
    private String password;
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
            name = "roles",
            joinColumns = @JoinColumn(name = "user")
    )
    private List<String> roles;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }
}
