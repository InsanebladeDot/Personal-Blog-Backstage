package com.example.springboottext.untill.RichTextConfiguration;
import com.example.springboottext.pojo.*;
import com.example.springboottext.untill.CosConfig.COSUtil;
import com.example.springboottext.untill.CosConfig.UploadFileController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
@Slf4j //加上这个注解会自动生成 一个日志记录
@RestController
@RequestMapping("/DelectFiles")
public class DelectControllerFile {
    private String uploadUrl = "D:\\Graduation Project\\DataSet";
    @PostMapping("/Imagefile")
    public RestBean DelectImageFile(@RequestBody List<ImageData> imageList) throws IOException {

        log.info("数据中蓝{}", Arrays.toString(imageList.toArray()));
        UploadFileController uploadFileController = new UploadFileController();

        for (ImageData imageData : imageList) {
            uploadFileController.delateFile(extractObjectKeyFromUrl(imageData.getUrl()));
        }
        return RestBean.success("成功");
    }

    @PostMapping("/Videofile")
    public RestBean DelectVideoFile(@RequestBody List<VideoData> videoList) throws IOException {
        log.info("数据中蓝{}", Arrays.toString(videoList.toArray()));
        UploadFileController uploadFileController = new UploadFileController();

        for (VideoData videoData : videoList) {
            uploadFileController.delateFile( extractObjectKeyFromUrl(videoData.getUrl()));
        }
        return RestBean.success("成功");
    }

    private void deleteFile(String relativePath) {
        try {
            File file = new File(Paths.get(uploadUrl, relativePath).toString());
            if (file.exists()) {
                if (file.delete()) {
                    log.info("文件删除成功: {}", relativePath);
                } else {
                    log.error("文件删除失败: {}", relativePath);
                }
            } else {
                log.warn("文件不存在: {}", relativePath);
            }
        } catch (Exception e) {
            log.error("文件删除时发生异常: {}", relativePath, e);
        }
    }

    public static String extractObjectKeyFromUrl(String fullUrl) {
        // 假设URL格式是固定的，并且形如 "https://bucket-name.cos.region.myqcloud.com/path/to/object"
        // 我们可以通过字符串操作来提取 "path/to/object" 部分
        String bucketAndRegionPrefix = "http://insande1314520-1328627845.cos.ap-chengdu.myqcloud.com/";
        // 检查URL是否以预期的前缀开始
        if (fullUrl.startsWith(bucketAndRegionPrefix)) {
            return fullUrl.substring(bucketAndRegionPrefix.length());
        } else {
            throw new IllegalArgumentException("Invalid URL format: " + fullUrl);
        }
    }
}