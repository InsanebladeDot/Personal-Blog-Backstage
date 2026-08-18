package com.example.springboottext.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ImageData {
   private String url;
   private String alt;
   private String href;

    public ImageData(String name) {
        this.url = "http://localhost:8082/"+name;
        this.alt = "image";
        this.href = "";
    }
}

