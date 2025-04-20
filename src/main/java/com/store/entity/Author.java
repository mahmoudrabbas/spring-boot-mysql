package com.store.entity;

import com.store.base.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Author  extends BaseEntity<Long> {
    @NotNull(message = "Full Name Is Required")
    @Column(name = "full_name")
    private String fullName;

    @Column(name = "image_path")
    private String imagePath;


    @NotNull(message = "Email Is Required")
    @Email(message = "Email Is Not Valid")
    @Column(name = "email", unique = true)
    private String email;


}
