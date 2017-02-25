package com.example.aninditha.homework7;

/**
 * Created by Aninditha on 11/22/2016.
 */

public class User {
    String fistName;
    String lastName;
    String fullName;
    String profilePicUrl;
    String gender;
    String email;
    String password;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    String id;

    public User() {
    }

    public User(String fistName, String gender, String profilePicUrl, String fullName, String lastName, String email, String password, String id) {
        this.fistName = fistName;
        this.gender = gender;
        this.profilePicUrl = profilePicUrl;
        this.fullName = fullName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.id = id;

    }

    public String getFistName() {
        return fistName;
    }

    public void setFistName(String fistName) {
        this.fistName = fistName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getProfilePicUrl() {
        return profilePicUrl;
    }

    public void setProfilePicUrl(String profilePicUrl) {
        this.profilePicUrl = profilePicUrl;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
