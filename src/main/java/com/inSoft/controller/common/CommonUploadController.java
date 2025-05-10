package com.inSoft.controller.common;

import com.inSoft.config.UploadConfig;
import com.inSoft.controller.BaseController;
import com.inSoft.mapper.SysFileMapper;
import com.inSoft.pojo.SysFile;
import com.inSoft.pojo.result.EditorFileResult;
import com.inSoft.pojo.result.FileResult;
import com.inSoft.utils.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Slf4j
@RestController
public class CommonUploadController extends BaseController {

    @Autowired
    private UploadConfig uploadConfig;

    @Autowired
    private UploadUtils uploadUtils;

    @Autowired
    private SysFileMapper sysFileMapper;

    @Autowired
    private HttpServletRequest request;

    private String upload(MultipartFile fileObj, String defaultLocal) throws Exception {
        // 获取文件后缀
        String fileExt = FileUtils.getFileSuffix(fileObj.getOriginalFilename());
        // 整理获取md5 密钥
        String fileMd5 = MD5Util.md5(fileObj.getOriginalFilename() + fileExt + fileObj.getSize() + fileObj.getSize() / 1024 / 1024 + defaultLocal);
        // 检索查询数据是否存在，如果存在则直接返回url，不存在则进行上传操作
        String url = sysFileMapper.getFilePathByMd5(fileMd5);
        if (url != null && !url.isEmpty()) {
            return url;
        } else {
            switch (defaultLocal) {
                case "alioss":
                    url = uploadUtils.aliOssUpload(fileObj);
                    break;
                case "tencos":
                    url = uploadUtils.tencentCosUpload(fileObj);
                    break;
                default:
                    url = uploadUtils.localUpload(fileObj);
                    break;
            }
            return url;
        }
    }

    private void saveFileInfo(String url, MultipartFile fileObj, String defaultLocal) {
        // 整理数据信息
        SysFile sysFile = new SysFile();
        sysFile.setFileName(fileObj.getOriginalFilename());
        sysFile.setFilePath(url);
        sysFile.setFileMd5(MD5Util.md5(fileObj.getOriginalFilename()));
        sysFile.setCreatedAt(new Date());
        sysFile.setUpdatedAt(new Date());
        sysFile.setIp(request.getRemoteAddr()); // 获取请求的IP地址
        sysFile.setSize(fileObj.getSize());
        sysFile.setExt(FileUtils.getFileSuffix(fileObj.getOriginalFilename()));
        sysFile.setLocal(defaultLocal);
        sysFileMapper.add(sysFile); // 调用保存方法
    }

    //判断文件是否过大
    private boolean isFileSizeNotAllowed(MultipartFile fileObj) {
        long fileSize = fileObj.getSize();
        long fileSizeMb = fileSize / 1024 / 1024;
        return fileSizeMb > uploadConfig.getDefaultMaxSize();
    }

    //验证文件格式是否不符
    private boolean isFileTypeNotAllowed(MultipartFile fileObj) {
        return FileUtils.isFileTypeAllowed(fileObj.getOriginalFilename(), uploadConfig.getDefaultLimitSuffix());
    }

    @RequestMapping("/editor_upload")
    public EditorFileResult editorUpload(@RequestParam("editormd-image-file") MultipartFile fileObj) throws Exception {
        //验证文件大小和格式
        if(isFileSizeNotAllowed(fileObj)){
            return EditorFileResult.error("文件过大，请重新上传！");
        }
        //验证文件格式是否不符
        if(isFileTypeNotAllowed(fileObj)){
            return EditorFileResult.error("文件格式错误，请重新上传！");
        }
        String url = upload(fileObj, uploadConfig.getDefaultLocal());
        if (!url.isEmpty()) {
            saveFileInfo(url, fileObj, uploadConfig.getDefaultLocal());
            return EditorFileResult.success("上传成功！", url);
        }
        return EditorFileResult.error("上传失败");
    }

    @PostMapping("/upload")
    public FileResult upload(@RequestParam("file") MultipartFile fileObj) throws Exception {
        //验证文件大小和格式
        if(isFileSizeNotAllowed(fileObj)){
            return FileResult.error("文件过大，请重新上传！");
        }
        //验证文件格式是否不符
        if(isFileTypeNotAllowed(fileObj)){
            return FileResult.error("文件格式错误，请重新上传！");
        }
        String url = upload(fileObj, uploadConfig.getDefaultLocal());
        if (!url.isEmpty()) {
            saveFileInfo(url, fileObj, uploadConfig.getDefaultLocal());
            return FileResult.success("上传成功！", url);
        }
        return FileResult.error("上传失败!");
    }
}
