package com.store.entity;

import com.store.base.entity.BaseEntity;
import jakarta.persistence.*;

@Entity(name = "product_image")
public class ProductImage extends BaseEntity<Long> {

    @Column(name = "image_name")
    private String imageName;
    @Column(name = "image_type")
    private String imageType;

    @Lob
    @Column(length = 1000000)
    private byte [] image;


    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getImageType() {
        return imageType;
    }

    public void setImageType(String imageType) {
        this.imageType = imageType;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
