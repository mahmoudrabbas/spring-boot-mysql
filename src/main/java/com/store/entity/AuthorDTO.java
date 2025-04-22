package com.store.entity;



public class AuthorDTO {
    private String fullName;
    private String imagePath;
    private String email;

    public AuthorDTO(Author author){
        this.fullName = author.getFullName();
        this.email = author.getEmail();
        this.imagePath = author.getImagePath();
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
