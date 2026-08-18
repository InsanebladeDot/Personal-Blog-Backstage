package com.example.springboottext.untill.CosConfig;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.io.InputStream;
 
@RestController
@RequestMapping("/Cosupload")
public class UploadFileController {
 
    @PostMapping("/cos")
    public String uploadFile(MultipartFile file) throws IOException {
        InputStream inputStream = file.getInputStream();
        String contentType = file.getContentType();
        String originalFilename = file.getOriginalFilename();
        //后缀截取
        String ext = originalFilename.substring(originalFilename.lastIndexOf("."),originalFilename.length());
        String result = COSUtil.upLoad(inputStream, ext, contentType,determineFileType(file.getContentType()));
        return result;
    }

    public void delateFile(String adress) throws IOException {
        COSUtil.deleteFile(adress);
    }
    private int determineFileType(String contentType) {
        // 判断文件类型
        if (isImageType(contentType)) {
            return 1; // 图片类型
        } else if (isVideoType(contentType)) {
            return 2; // 视频类型
        } else {
            return 3; // 其他类型
        }
    }
    private boolean isImageType(String contentType) {
        // 图片类型判断
        return contentType.startsWith("image/") ||
                contentType.equals("image/x-icon") ||
                contentType.equals("image/bmp") ||
                contentType.equals("image/x-cmu-raster") ||
                contentType.equals("image/cis-cod") ||
                contentType.equals("image/g3fax") ||
                contentType.equals("image/gif") ||
                contentType.equals("image/ief") ||
                contentType.equals("image/jpeg") ||
                contentType.equals("image/jp2") ||
                contentType.equals("image/ktx") ||
                contentType.equals("image/png") ||
                contentType.equals("image/prs.btif") ||
                contentType.equals("image/tiff") ||
                contentType.equals("image/vnd.adobe.photoshop") ||
                contentType.equals("image/vnd.cns.inf2") ||
                contentType.equals("image/vnd.dece.graphic") ||
                contentType.equals("image/vnd.djvu") ||
                contentType.equals("image/vnd.dwg") ||
                contentType.equals("image/vnd.dxf") ||
                contentType.equals("image/vnd.fastbidsheet") ||
                contentType.equals("image/vnd.fpx") ||
                contentType.equals("image/vnd.fst") ||
                contentType.equals("image/vnd.fujixerox.edmics-mmr") ||
                contentType.equals("image/vnd.fujixerox.edmics-rlc") ||
                contentType.equals("image/vnd.microsoft.icon") ||
                contentType.equals("image/vnd.ms-modi") ||
                contentType.equals("image/vnd.net-fpx") ||
                contentType.equals("image/vnd.wap.wbmp") ||
                contentType.equals("image/vnd.xiff") ||
                contentType.equals("image/webp") ||
                contentType.equals("image/x-cmu-raster") ||
                contentType.equals("image/x-cmx") ||
                contentType.equals("image/x-freehand") ||
                contentType.equals("image/x-icon") ||
                contentType.equals("image/x-jg") ||
                contentType.equals("image/x-jpe") ||
                contentType.equals("image/x-jpeg") ||
                contentType.equals("image/x-kodak-dcr") ||
                contentType.equals("image/x-photoshop") ||
                contentType.equals("image/x-pict") ||
                contentType.equals("image/x-portable-anymap") ||
                contentType.equals("image/x-portable-bitmap") ||
                contentType.equals("image/x-portable-graymap") ||
                contentType.equals("image/x-portable-pixmap") ||
                contentType.equals("image/x-quicktime") ||
                contentType.equals("image/x-rgb") ||
                contentType.equals("image/x-tga") ||
                contentType.equals("image/x-xbitmap") ||
                contentType.equals("image/x-xpixmap") ||
                contentType.equals("image/x-xwindowdump");
    }

    private boolean isVideoType(String contentType) {
        // 视频类型判断
        return contentType.startsWith("video/") ||
                contentType.equals("video/3gpp") ||
                contentType.equals("video/3gpp2") ||
                contentType.equals("video/3gpp-tt") ||
                contentType.equals("video/3gpp2-tt") ||
                contentType.equals("video/320×240-mjpeg") ||
                contentType.equals("video/3gpp") ||
                contentType.equals("video/3gpp2") ||
                contentType.equals("video/3gpp-pd") ||
                contentType.equals("video/3gpp-tt") ||
                contentType.equals("video/3gpp2-pd") ||
                contentType.equals("video/3gpp2-tt") ||
                contentType.equals("video/3gppc") ||
                contentType.equals("video/3gppc-tt") ||
                contentType.equals("video/3gppc-pd") ||
                contentType.equals("video/3gppc-omc-buf") ||
                contentType.equals("video/3gppc-omc-scv") ||
                contentType.equals("video/3gppc-omc-sp") ||
                contentType.equals("video/3gppc-omc-ts") ||
                contentType.equals("video/3gppc-omc-wb") ||
                contentType.equals("video/3gpp-tt") ||
                contentType.equals("video/3gpp2-pd") ||
                contentType.equals("video/3gpp2-tt") ||
                contentType.equals("video/3gppc-tt") ||
                contentType.equals("video/3gppc-pd") ||
                contentType.equals("video/3gppc-omc-buf") ||
                contentType.equals("video/3gppc-omc-scv") ||
                contentType.equals("video/3gppc-omc-sp") ||
                contentType.equals("video/3gppc-omc-ts") ||
                contentType.equals("video/3gppc-omc-wb") ||
                contentType.equals("video/3gpp2-pd") ||
                contentType.equals("video/3gpp2-tt") ;

    }
}