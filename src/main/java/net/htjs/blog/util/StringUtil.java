package net.htjs.blog.util;

import net.htjs.blog.constant.SystemConstant;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

import java.util.UUID;

/**
 * blog/net.htjs.blog.util
 *
 * @Description: 字符串工具类，包含生成32位UUID方法
 * @Author: dingdongliang
 * @Date: 2018/8/13 17:36
 */
public class StringUtil {
    private StringUtil() {
    }

    public static boolean isNullOrEmpty(String str) {
        return null == str || "".equals(str) || "null".equals(str);
    }

    public static boolean isNullOrEmpty(Object obj) {
        return null == obj || "".equals(obj);
    }

    /**
     * 根据用户名和密码进行两次MD5加密
     *
     * @param pwd  原始密码
     * @param salt 盐
     * @return java.lang.String
     * @author dingdongliang
     * @date 2018/4/17 8:42
     */
    public static String encryptPassword(String pwd, String salt) {
        return new SimpleHash(SystemConstant.ALGORITHMNAME, pwd, ByteSource.Util.bytes(salt),
                SystemConstant.HASHITERATIONS).toHex();
    }

    /**
     * 获取一个32位UUID主键
     *
     * @return java.lang.String
     * @author dingdongliang
     * @date 2018/4/17 8:41
     */
    public static String getUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    /**
     * 生成number个主键
     *
     * @param number
     * @return java.lang.String[]
     * @author dingdongliang
     * @date 2018/4/18 14:43
     */
    public static String[] getUUID(int number) {
        if (number < 1) {
            return null;
        }
        String[] ss = new String[number];
        for (int i = 0; i < number; i++) {
            ss[i] = getUUID();
        }
        return ss;
    }
}
