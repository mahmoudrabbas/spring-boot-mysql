package com.store.entity;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PostDTO {

    private Long id;
    private String userId;
    private String title;
    private String body;

}
