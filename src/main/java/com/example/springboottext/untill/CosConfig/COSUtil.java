package com.example.springboottext.untill.CosConfig;
 
import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.model.DeleteObjectRequest;
import com.qcloud.cos.model.ObjectMetadata;
import com.qcloud.cos.model.PutObjectResult;
import com.qcloud.cos.region.Region;
 
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
 
public class COSUtil {
    /**
     * 腾讯云COS
     * SecretId:AKIDGa===============uqeHiw
     * SecretKey:CHCC===========8GLM
     */
    public static String SECRET_ID = "AKIDVqzuiNs8AwYu6BAwVpDe0CB3wt2DKDyb"; //这个参数需要你替换为自己的
    public static String SECRET_KEY = "bz8A1snV5ayQp131ugKrFRSN9saCdyBH"; //这个参数需要你替换为自己的
    public static String BUCKET_NAME = "insande1314520-1328627845"; //这个参数需要你替换为自己的
    public static String REGION = "ap-chengdu"; //这个参数需要你替换为自己的
 
    //期望的 存的前缀
    public static String prePath="DataSet/"; //这个参数需要你替换为自己的
    
    /**
     * 获取客户端对象
     * @return
     */
    public static COSClient getInstance(){
        COSCredentials cred = new BasicCOSCredentials(SECRET_ID, SECRET_KEY);
        Region region = new Region(REGION);
        ClientConfig clientConfig = new ClientConfig(region);
        return new COSClient(cred, clientConfig);
    }
 
    /**
     * 图片上传
     * @param in
     * @param ext
     * @param contentType
     * @return
     */
    public static String upLoad(InputStream in, String ext, String contentType,Integer type){
        ObjectMetadata objectMetadata = new ObjectMetadata();
        String Type = "";

        if(type == 1)Type = "Images/";
        else if(type == 2)Type = "videos/";
        else Type ="other/";
        String key =  prePath + Type +getTimeStamp()+ext;
        objectMetadata.setContentType(contentType);
        PutObjectResult putObjectResult = getInstance().putObject(BUCKET_NAME, key, in, objectMetadata);

        return "http://"+BUCKET_NAME+".cos."+REGION+".myqcloud.com/"+key; //返回上传的图片的地址
    }
    /**
     * 删除指定的对象
     * @param key 对象的路径和名称
     */
    public static void deleteFile(String key) {
        COSClient cosClient = getInstance();
        DeleteObjectRequest deleteObjectRequest = new DeleteObjectRequest(BUCKET_NAME, key);
        cosClient.deleteObject(deleteObjectRequest);
    }
    // 获得时间戳
    public static String getTimeStamp(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");
        String dateTime = LocalDateTime.now(ZoneOffset.of("+8")).format(formatter);
        return dateTime;
    }
 
}