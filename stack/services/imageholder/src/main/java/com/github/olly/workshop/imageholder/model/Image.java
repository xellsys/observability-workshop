package com.github.olly.workshop.imageholder.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.annotation.Id;

public class Image {
    @Id
    private String id;
    private String contentType;
    private String name;

    @JsonIgnore
    private byte[] data;


    public String getId() {
        return id;
    }


    public Image setId(String id) {
        this.id = id;
        return this;
    }


    public String getContentType() {
        return contentType;
    }


    public Image setContentType(String contentType) {
        this.contentType = contentType;
        return this;
    }


    public byte[] getData() {
        return data;
    }


    public Image setData(byte[] data) {
        this.data = data;
        return this;
    }


    @Override
    public String toString() {
        return "Image{" +
                "id=" + id +
                ", contentType='" + contentType + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
