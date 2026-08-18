package com.example.springboottext.untill;

import com.example.springboottext.pojo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
@Slf4j //加上这个注解会自动生成 一个日志记录
@RestController
@RequestMapping("/Audio")
public class AudioUploadUntill {


    @PostMapping("/upload/audio")
    public Result handleAudioUpload(@RequestParam("file") MultipartFile file) {

        log.info("File uploaded: {}", file.getOriginalFilename());
        if (file.isEmpty()) {
            return Result.error("No file uploaded");
        }

        // 确保文件是.wav类型
        String fileName = file.getOriginalFilename();
        if (!fileName.endsWith(".wav")) {
            return Result.error("Invalid file type. Only .wav files are allowed.");
        }

        try {
            // 指定保存文件的路径为项目的resources下的static/audioOS目录
            String uploadDirectory = "D:\\Study_Note\\SpringBoot_Study\\SpringbootText_Data_wav\\";
            File uploadDir = new File(uploadDirectory);

            // 确保目录存在，不存在则创建
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }

            // 构建文件保存的完整路径
            String filePath = uploadDirectory + fileName;
            File dest = new File(filePath);

            // 保存文件到指定路径
            file.transferTo(dest);

            log.info("File uploaded successfully to: {}", filePath);
            return Result.success(filePath);
        } catch (IOException e) {
            log.error("Error occurred while uploading file", e);
            return Result.error(e.getLocalizedMessage());
        }
    }
}
