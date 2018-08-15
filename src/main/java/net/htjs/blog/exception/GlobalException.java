package net.htjs.blog.exception;

/**
 * blog/net.htjs.blog.exception
 *
 * @Description: 自定义全局异常类，code属性，表示请求的响应状态码
 * @Author: dingdongliang
 * @Date: 2018/8/13 16:57
 */
public class GlobalException extends Exception {

    private String code;

    public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public GlobalException(String message) {
        super(message);
    }

    public GlobalException(String message, String code) {
        super(message);
        this.code = code;
    }
}
