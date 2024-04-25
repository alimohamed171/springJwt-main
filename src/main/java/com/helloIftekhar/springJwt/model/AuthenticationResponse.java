package com.helloIftekhar.springJwt.model;

public class AuthenticationResponse {
    private String token;
    private String username;
    private int userID;
    private String message;
    private Role role;

    public AuthenticationResponse(String token, String message,String username,int userID , Role role) {
        this.token = token;
        this.message = message;
        this.userID=userID;
        this.username=username;
        this.role=role;
    }

    public AuthenticationResponse(String token, String message) {
        this.token = token;
        this.message = message;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getToken() {
        return token;
    }

    public String getMessage() {
        return message;
    }

}
