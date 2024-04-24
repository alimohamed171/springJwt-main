package com.helloIftekhar.springJwt.model;

public class AuthenticationResponse {
    private String token;
    private String username;
    private int userID;
    private String message;

    public AuthenticationResponse(String token, String message,String username,int userID) {
        this.token = token;
        this.message = message;
        this.userID=userID;
        this.username=username;
    }

    public AuthenticationResponse(String token, String message) {
        this.token = token;
        this.message = message;
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
