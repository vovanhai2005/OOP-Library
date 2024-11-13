package org.example.ooplibrary.Object;

import javafx.scene.image.ImageView;

public class User {
    private String username;
    private String fullName;
    private String gender;
    private String dob;
    private String email;
    private String phoneNumber;
    private byte[] image;
    private ImageView[] features;

    public User(String username, String fullName, String gender, String dob, String email, String phoneNumber, byte[] image) {
        this.username = username;
        this.fullName = fullName;
        this.gender = gender;
        this.dob = dob;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.image = image;
        this.features = new ImageView[3];
    }

    public String getFulLName() {
        return fullName;
    }

    public void setFulLName(String fulLName) {
        this.fullName = fulLName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public ImageView[] getFeatures() {
        return features;
    }

    public void setFeatures(ImageView[] features) {
        this.features = features;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
