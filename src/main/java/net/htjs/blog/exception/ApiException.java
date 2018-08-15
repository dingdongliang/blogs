package net.htjs.blog.exception;

import net.htjs.blog.constant.RespCodeEnum;

/**
 * blog/net.htjs.blog.exception
 *
 * @Description: RESTFULL API异常类
 * @Author: dingdongliang
 * @Date: 2018/8/13 16:58
 */
public class ApiException extends GlobalException {

    public ApiException(RespCodeEnum respCodeEnum) {
        super(respCodeEnum.getMessage(), respCodeEnum.getCode());
    }
}
