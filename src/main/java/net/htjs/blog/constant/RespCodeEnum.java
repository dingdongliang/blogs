package net.htjs.blog.constant;

import lombok.Getter;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * blog/net.htjs.blog.constant
 *
 * @Description: Http请求响应码以及登录状态枚举,常用的有200，201，400，401，403，404，409，500
 * @Author: dingdongliang
 * @Date: 2018/8/13 16:53
 */
@Getter
public enum RespCodeEnum {
    //前面是错误码,后面是错误说明
    CONTINUE("100", "Continue"),
    SWITCHING_PROTOCOL("101", "Switching Protocol"),
    PROCESSING("102", "Processing"),
    OK("200", "OK"),
    CREATED("201", "Created"),
    ACCEPTED("202", "Accepted"),
    NO_CONTENT("204", "No Content"),
    RESET_CONTENT("205", "Reset Content"),
    PARTIAL_CONTENT("206", "Partial Content"),
    MOVED_PERMANENTLY("301", "Moved Permanently"),
    FOUND("302", "Found"),
    SEE_OTHER("303", "See Other"),
    NOT_MODIFIED("304", "Not Modified"),
    USE_PROXY("305", "Use Proxy"),
    TEMPORARY_REDIRECT("307", "Temporary Redirect"),
    PERMANENT_REDIRECT("308", "Permanent Redirect"),
    BAD_REQUEST("400", "Bad Request"),
    UNAUTHORIZED("401", "Unauthorized"),
    PAYMENT_REQUIRED("402", "Payment Required"),
    FORBIDDEN("403", "Forbidden"),
    NOT_FOUND("404", "Not Found"),
    METHOD_NOT_ALLOWED("405", "Method Not Allowed"),
    NOT_ACCEPTABLE("406", "Not Acceptable"),
    PROXY_AUTHENTICATION_REQUIRED("407", "Proxy Authentication Required"),
    REQUEST_TIMEOUT("408", "Request Timeout"),
    CONFLICT("409", "Conflict"),
    GONE("410", "Gone"),
    LENGTH_REQUIRED("411", "Length Required"),
    PRECONDITION_FAILED("412", "Precondition Failed"),
    REQUEST_ENTITY_TOO_LARGE("413", "Request Entity Too Large"),
    REQUEST_URI_TOO_LONG("414", "Request-URI Too Long"),
    UNSUPPORTED_MEDIA_TYPE("415", "Unsupported Media Type"),
    REQUESTED_RANGE_NOT_SATISFIABLE("416", "Requested Range Not Satisfiable"),
    EXPECTATION_FAILED("417", "Expectation Failed"),
    TOO_MANY_REQUESTS("429", "Too Many Requests"),
    INTERNAL_SERVER_ERROR("500", "Internal Server Error"),
    NOT_IMPLEMENTED("501", "Not Implemented"),
    BAD_GATEWAY("502", "Bad Gateway"),
    SERVICE_UNAVAILABLE("503", "Service Unavailable"),
    GATEWAY_TIMEOUT("504", "Gateway Timeout"),
    HTTP_VERSION_NOT_SUPPORTED("505", "HTTP Version Not Supported"),
    NOT_ENOUGH_PARAMS("1001", "not enough params"),
    CAN_NOT_DELETE_HAVE_CHILDREN("1002", "Have children,can't delete"),
    ACCOUNT_ALREADY_EXISTS("1003", "Account already exists"),
    LOGOUT_PLEASE_RE_LOGIN("1004", "Login has expired, please re-login"),

    ILLEGAL_PARAMS("ILLEGAL_PARAMS", "非法请求参数"),
    SERVER_ERROR("SERVER_ERROR", "服务正忙"),
    NO_ACCOUNT("NO_ACCOUNT", "未知账户"),
    ACCOUNT_LOCKED("ACCOUNT_LOCKED", "账户已锁定"),
    TRY_MORE_FIVE("TRY_MORE_FIVE", "错误次数大于5次,账户已锁定"),
    ACCOUNT_FORBID("ACCOUNT_FORBID", "帐号已经禁止登录"),
    STH_ERROR("STH_ERROR", "用户名或密码不正确"),
    PWD_ERROR("PWD_ERROR", "密码不正确");

    private String code;
    private String message;

    RespCodeEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
