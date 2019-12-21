package net.lantrack.framework.common.utils;

/**
 * @Description 系统状态码
 * @Author dahuzi
 * @Date 2019/11/7 22:17
 */
public class ServerStatus {

    /**
     * 正常
     */
    public static final int SUC = 200;
    /**
     *错误
     */
    public static final int ERR = 500;
    /**
     *未认证
     */
    public static final int UN_AUTH = 401;
    /**
     *未授权
     */
    public static final int UN_PERMISSION = 402;
    /**
     *禁用
     */
    public static final int FORBID = 403;
    /**
     *未发现
     */
    public static final int NOTFIND = 404;
    /**
     *被锁定
     */
    public static final int LOCK = 423;
}
