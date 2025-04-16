package com.store.entity;

import com.store.base.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

@Entity
public class Author extends BaseEntity<Long> {
    @NotNull(message = "Full Name Is Required")
    @Column(name = "full_name")
    private String fullName;

    @Column(name = "image_path")
    private String imagePath;

    @Column(name = "img_public_id")
    private String publicId;


    @NotNull(message = "Email Is Required")
    @Email(message = "Email Is Not Valid")
    @Column(name = "email", unique = true)
    private String email;

    public Author() {
    }

    public Author(String fullName, String email) {
        this.fullName = fullName;
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImagePath() {

        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getPublicId() {
        return publicId;
    }

    public void setPublicId(String publicId) {
        this.publicId = publicId;
    }
}
