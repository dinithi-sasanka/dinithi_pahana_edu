package com.example.dinithi_pahana_edu.model;

public class User {
    private int id;
    private String username;
    private String password;
    private String role;
    private String useName;
    private String email;
    private String telephone;

    public User() {}

    public User(int id, String username, String password, String role, String useName, String email, String telephone) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
        this.useName = useName;
        this.email = email;
        this.telephone = telephone;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getUseName() {
        return useName;
    }
    public void setUseName(String useName) {
        this.useName = useName;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getTelephone() {
        return telephone;
    }
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    private static native void sleepNanos0(long nanos) throws InterruptedException;
} 