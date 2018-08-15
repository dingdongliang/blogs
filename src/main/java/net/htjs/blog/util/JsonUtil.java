package net.htjs.blog.util;

import com.alibaba.fastjson.JSONObject;
import net.htjs.blog.constant.RespCodeEnum;
import net.htjs.blog.constant.SystemConstant;
import net.htjs.blog.exception.ApiException;
import net.htjs.blog.exception.GlobalException;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

/**
 * blog/net.htjs.blog.util
 *
 * @Description:
 * @Author: dingdongliang
 * @Date: 2018/8/14 10:39
 */
public class JsonUtil {
    /**
     * 返回一个returnData为空对象的成功消息的json
     *
     * @return com.alibaba.fastjson.JSONObject
     * @author dingdongliang
     * @date 2018/4/18 14:40
     */
    public static JSONObject successJson() {
        return successJson(new JSONObject());
    }

    /**
     * 返回一个返回码为1的json
     *
     * @param returnData
     * @return com.alibaba.fastjson.JSONObject
     * @author dingdongliang
     * @date 2018/4/18 14:40
     */
    public static JSONObject successJson(Object returnData) {
        JSONObject resultJson = new JSONObject();
        resultJson.put(SystemConstant.CODE, SystemConstant.SUCCESS_RESULT);
        resultJson.put(SystemConstant.MESSAGE, SystemConstant.SUCCESS_MSG);
        resultJson.put(SystemConstant.DATA, returnData);
        return resultJson;
    }

    /**
     * 将request转JSON并且验证非空字段
     *
     * @param request
     * @param requiredColumns
     * @return com.alibaba.fastjson.JSONObject
     * @author dingdongliang
     * @date 2018/4/18 14:40
     */
    public static JSONObject convert2JsonAndCheckRequiredColumns(HttpServletRequest request, String requiredColumns)
            throws GlobalException {
        JSONObject jsonObject = request2Json(request);
        hasAllRequired(jsonObject, requiredColumns);
        return jsonObject;
    }

    /**
     * 将request参数值转为json
     *
     * @param request
     * @return com.alibaba.fastjson.JSONObject
     * @author dingdongliang
     * @date 2018/4/18 14:40
     */
    public static JSONObject request2Json(HttpServletRequest request) {
        JSONObject requestJson = new JSONObject();
        Enumeration paramNames = request.getParameterNames();
        while (paramNames.hasMoreElements()) {
            String paramName = (String) paramNames.nextElement();
            String[] pv = request.getParameterValues(paramName);
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < pv.length; i++) {
                if (pv[i].length() > 0) {
                    if (i > 0) {
                        sb.append(",");
                    }
                    sb.append(pv[i]);
                }
            }
            requestJson.put(paramName, sb.toString());
        }
        return requestJson;
    }

    /**
     * 验证是否含有全部必填字段
     *
     * @param jsonObject
     * @param requiredColumns 必填的参数字段名称 逗号隔开 比如"userId,userName,userBirth"
     * @return void
     * @author dingdongliang
     * @date 2018/4/18 14:40
     */

    public static void hasAllRequired(final JSONObject jsonObject, String requiredColumns) throws GlobalException {
        if (!StringUtil.isNullOrEmpty(requiredColumns)) {
            //验证字段非空
            String[] columns = requiredColumns.split(",");
            String missCol = "";
            for (String column : columns) {
                Object val = jsonObject.get(column.trim());
                if (StringUtil.isNullOrEmpty(val)) {
                    missCol += column + "  ";
                }
            }
            if (!StringUtil.isNullOrEmpty(missCol)) {
                throw new ApiException(RespCodeEnum.NOT_ENOUGH_PARAMS);
            }
        }
    }

    /**
     * 返回错误信息JSON
     *
     * @param respCodeEnum
     * @return com.alibaba.fastjson.JSONObject
     * @author dingdongliang
     * @date 2018/4/18 14:41
     */
    public static JSONObject errorJson(RespCodeEnum respCodeEnum) {
        JSONObject resultJson = new JSONObject();
        resultJson.put(SystemConstant.CODE, respCodeEnum.getCode());
        resultJson.put(SystemConstant.MESSAGE, respCodeEnum.getMessage());
        resultJson.put(SystemConstant.DATA, new JSONObject());
        return resultJson;
    }

}
