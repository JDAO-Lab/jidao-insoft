package com.inSoft.utils;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.inSoft.config.AliOssConfig;
import com.inSoft.config.TencentCosConfig;
import com.inSoft.config.UploadConfig;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.auth.COSStaticCredentialsProvider;
import com.qcloud.cos.model.CannedAccessControlList;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.PutObjectResult;
import com.qcloud.cos.model.StorageClass;
import com.qcloud.cos.region.Region;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.io.File;
import java.util.UUID;

@Component
public class UploadUtils {

    @Autowired
    private UploadConfig uploadConfig; // 注入UploadConfig bean

    @Autowired
    private AliOssConfig aliOssConfig;

    @Autowired
    private TencentCosConfig tencentCosConfig;

    public String tencentCosUpload(MultipartFile file) throws IOException {
        String secretId = tencentCosConfig.getSecretId();
        String secretKey = tencentCosConfig.getSecretKey();
        String region = tencentCosConfig.getRegion();
        String bucketName = tencentCosConfig.getBucketName();
        String domain = uploadConfig.getDefaultLocalDomain();

        COSCredentials cred = new BasicCOSCredentials(secretId, secretKey);
        ClientConfig clientConfig = new ClientConfig(new Region(region));
        COSClient cosClient = new COSClient(new COSStaticCredentialsProvider(cred), clientConfig);

        String originalFilename = file.getOriginalFilename();
        String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
        String randomDir = UUID.randomUUID().toString().replace("-", "") + "/";
        String fileName = randomDir + UUID.randomUUID().toString().replace("-", "") + fileExtension;

        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, fileName, file.getInputStream(), null);
        putObjectRequest.setStorageClass(StorageClass.Standard);
        putObjectRequest.setCannedAcl(CannedAccessControlList.PublicRead); // 设置为公共读

        PutObjectResult putObjectResult = cosClient.putObject(putObjectRequest);
        DeBugUtils.print("cosResult:" + putObjectResult);
        String url = domain + "/" + fileName;

        cosClient.shutdown();

        return url;
    }


    /**
     * 实现上传图片到阿里云OSS
     */
    public String aliOssUpload(MultipartFile file) throws IOException {
        String endpoint = aliOssConfig.getEndpoint();
        String domain = uploadConfig.getDefaultLocalDomain();
        String accessKeyId = aliOssConfig.getAccessKeyId();
        String accessKeySecret = aliOssConfig.getAccessKeySecret();
        String bucketName = aliOssConfig.getBucketName();
        // 获取上传的文件的输入流
        InputStream inputStream = file.getInputStream();
        String originalFilename = file.getOriginalFilename();
        String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
        String randomDir = UUID.randomUUID().toString().replace("-", "") + "/";
        String fileName = randomDir + UUID.randomUUID().toString().replace("-", "") + fileExtension;
        //上传文件到 OSS
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        ossClient.putObject(bucketName, fileName, inputStream);
        //文件访问路径
        String url = domain+"/"+ fileName;
        // 关闭ossClient
        ossClient.shutdown();
        return url;// 把上传到oss的路径返回
    }

    /**
     * 实现本地文件上传
     * 备忘：需要给生成目录搭建一个虚拟目录来实现访问的服务
     * @param fileObj
     * @return
     * @throws IOException
     */
    public String localUpload(MultipartFile fileObj) throws IOException {
        //获取原始文件名
        String originalFilename = fileObj.getOriginalFilename();
        String domain = uploadConfig.getDefaultLocalDomain();
        //构造唯一的文件名 (不能重复) - uuid(通用唯一识别码) de49685b-61c0-4b11-80fa-c71e95924018
        int index = originalFilename.lastIndexOf(".");
        String extname = originalFilename.substring(index);
        String newFileName = UUID.randomUUID().toString() + extname;
        // 将文件存储在服务器的磁盘目录
        String default_os = uploadConfig.getDefaultOs();
        // 生成随机目录名
        String randomDir = UUID.randomUUID().toString().replace("-", ""); // 生成没有连字符的随机字符串
        // 构建完整的文件存储路径
        String newPathUrl = "";
        if ("win".equals(default_os)) {
            DeBugUtils.print("win系统下上传操作~");
            newPathUrl = uploadConfig.getDefaultWinPath() + File.separator + randomDir + File.separator + newFileName;
        } else if ("linux".equals(default_os)) {
            DeBugUtils.print("linux系统下上传操作~");
            newPathUrl = uploadConfig.getDefaultLinuxPath() + File.separator + randomDir + File.separator + newFileName;
        } else {
            DeBugUtils.print("不支持的系统类型，请配置win或linux。");
        }
        // 判断目录是否存在，不存在则创建
        File newFile = new File(newPathUrl);
        File parentFile = newFile.getParentFile();
        if (!parentFile.exists()) {
            if (parentFile.mkdirs()) {
                DeBugUtils.print("目录不存在，已创建目录：" + parentFile.getAbsolutePath());
            } else {
                DeBugUtils.print("目录创建失败：" + parentFile.getAbsolutePath());
            }
        }
        fileObj.transferTo(newFile);
        // 获取相对路径
        String relativePath = domain+newPathUrl.replace(uploadConfig.getDefaultWinPath(), "").replace("\\", "/").replace("//", "/"); // 替换掉绝对路径部分，得到相对路径
        return relativePath;
    }
}
