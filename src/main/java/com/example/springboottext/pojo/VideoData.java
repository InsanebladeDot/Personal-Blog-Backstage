package com.example.springboottext.pojo;

import lombok.Data;

@Data
public class VideoData {
    private String url;
    private String poster;// 视频后缀类别
    private String name;// 视频后缀类别
    public VideoData(String name,String poster) {

        this.url = "http://localhost:8082/"+name;
        this.poster = poster ;
        this.name = name;
    }
}
