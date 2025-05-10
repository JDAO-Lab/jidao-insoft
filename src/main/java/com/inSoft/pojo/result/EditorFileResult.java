package com.inSoft.pojo.result;

/**
 * 同意文件上传返回参数
 */
public class EditorFileResult {
    private Integer success ;//1 成功 , 0 失败
    private String message; //提示信息
    private String url; //数据 data

    public EditorFileResult() {
    }
    public EditorFileResult(Integer success, String message, String url) {
       this.success = success;
       this.message = message;
       this.url = url;
    }

    public Integer getSuccess() {
        return success;
    }

    public void setSuccess(Integer success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public static EditorFileResult success(String message, String url){
        return new EditorFileResult(1, message, url);
    }
    public static EditorFileResult success(String message){
        return new EditorFileResult(1, message, null);
    }
    public static EditorFileResult error(String message){
        return new EditorFileResult(0, message, null);
    }

    @Override
    public String toString() {
        return "EditorFileResult{" +
                "success=" + success +
                ", message='" + message + '\'' +
                ", url='" + url + '\'' +
                '}';
    }

}
