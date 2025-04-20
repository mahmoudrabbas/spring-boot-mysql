package com.store.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor

public class AuthorDTO {
    private String fullName;
    private String imagePath;
    private String email;

    public AuthorDTO(Author author){
        this.fullName = author.getFullName();
        this.email = author.getEmail();
        this.imagePath = author.getImagePath();
    }

}
