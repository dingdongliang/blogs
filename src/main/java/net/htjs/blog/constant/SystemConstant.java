package net.htjs.blog.constant;

/**
 * blog/net.htjs.blog.constant
 *
 * @Description:
 * @Author: dingdongliang
 * @Date: 2018/8/14 10:28
 */
public class SystemConstant {
    private SystemConstant() {
    }

    /**
     * 超级管理员帐号
     */
    public static final String SYSTEM_ADMINISTRATOR = "dyenigma";

    /**
     * 测试中使用的用户ID
     */
    public static final String TEST_USER_ID = "1d55fc9860894fbc88d7d79085e2f55f";

    /**
     * Cookie 管理器加密使用
     */
    public static final String AES_KEY = "2AvVhdsgUs0FSA3SDFAdag==";
    /**
     * 同一个帐号最大会话数 默认1
     */
    public static final Integer ACCOUNT_MAX_SESSION = 1;
    public static final String KICKOUT = "kickout";
    /**
     * 同一账号多次登录的时候，踢人的顺序设置，false为踢出先登录的
     */
    public static final Boolean KICKOUT_AFTER = false;

    public static final String ANON = "anon";

    /**
     * 状态，有效的为E，无效的为I
     */
    public static final String EFFECTIVE = "E";
    public static final String INVALID = "I";

    /**
     * 一些常用的字符串常量，常量值和名称保持一致,值都是小写，用来避免魔法值
     */
    public static final String OK = "ok";
    public static final String ERROR = "error";
    public static final String SUCCESS = "success";
    public static final String FALSE = "false";
    public static final String UNKNOWN = "unknown";
    public static final String COMMA = ",";
    public static final boolean SUCCESS_RESULT = true;
    public static final boolean ERROR_RESULT = false;
    public static final String SUCCESS_MSG = "请求成功";

    public static final String ALGORITHMNAME = "MD5";
    public static final Integer HASHITERATIONS = 2;

    /**
     * 返回数据的属性命名
     */
    public static final String CODE = "code";
    public static final String MESSAGE = "message";
    public static final String DATA = "data";

    /**
     * session中存放用户信息的key值
     */
    public static final String SESSION_USER_INFO = "userInfo";
    public static final String SESSION_USER_PERMISSION = "userPermission";

    /**
     * 分页相关数据
     */
    public static final String RESULT = "data";
    public static final String TOTAL = "totalPages";
    public static final Integer PAGE_SIZE = 10;

    /**
     * ENFLAG表示已加密，或者已发送，使用字符串常量"did"
     */
    public static final String ENFLAG = "did";

    /**
     * NOFLAG表示未加密，或者未发送，使用字符串常量"undo"
     */
    public static final String NOFLAG = "undo";

    /**
     * 默认密码
     */
    public static final String DEFAULT_CREDENTIAL = "111111";
}
