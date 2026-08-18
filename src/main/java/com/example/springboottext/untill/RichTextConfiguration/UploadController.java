package com.example.springboottext.untill.RichTextConfiguration;

import com.example.springboottext.pojo.*;
import com.example.springboottext.untill.CosConfig.UploadFileController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Slf4j //加上这个注解会自动生成 一个日志记录
@RestController
@RequestMapping("/upload")
public class UploadController {
    private String uploadUrl = "D:\\Graduation Project\\DataSet";
    @PostMapping("/image")
    public RestBean uploadImage( @RequestPart(value = "file",required = true) MultipartFile file) throws IOException {
        //拼接路劲，保存文件
        log.info("传输文件上来了{}",file.getContentType());
        UploadFileController uploadFileController = new UploadFileController();

        String adress = uploadFileController.uploadFile(file);

        //new一个imageData保存前端所需要的数据
        ImageData image = new ImageData();
        image.setUrl(adress);
        image.setAlt(file.getContentType());
        //返回数据
        return RestBean.success(image);
    }
    @PostMapping("/video")
    public RestBean uploadVideo( @RequestPart(value = "file",required = true) MultipartFile file) throws IOException {
        //接收、校验逻辑请自行编写
        log.info("File uploaded: {}", file.getOriginalFilename());
        UploadFileController uploadFileController = new UploadFileController();

        String adress = uploadFileController.uploadFile(file);

        //new一个imageData保存前端所需要的数据
        VideoData videoData = new VideoData(adress,getFileExtension(file.getOriginalFilename()));
        videoData.setUrl(adress);
        //返回数据
        return RestBean.success(videoData);
    }


    /**
     * 通过文件路径获取文件后缀名
     * @param filePath 文件路径
     * @return 文件后缀名，如果没有则返回空字符串
     */
    public static String getFileExtension(String filePath) {
        if (filePath == null || filePath.isEmpty()) {
            return "";
        }
        int dotIndex = filePath.lastIndexOf('.');
        return (dotIndex > 0 && dotIndex < filePath.length() - 1) ? filePath.substring(dotIndex + 1) : "";
    }

    /**
     * 通过File对象获取文件后缀名
     * @param file 文件对象
     * @return 文件后缀名，如果没有则返回空字符串
     */
    public static String getFileExtensionFromFileObject(File file) {
        if (file != null && file.exists() && file.isFile()) {
            String fileName = file.getName();
            return getFileExtension(fileName);
        }
        return "";
    }
}