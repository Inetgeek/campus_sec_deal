/**
 * @Class: ReturnCodeConf
 * @Date: 2022/9/23
 * @Project: campus_sec_deal
 * @Author: Colyn
 * @Builder: IntelliJ IDEA
 */

package edu.njust.campus_sec_deal.config;

public class ReturnCodeConf {
    /**
     * 此类用于设置RESTful API返回状态码规范
     */

    /**
     * 操作成功
     **/
    public static final int OPT_SUCCESS = 200;

    /**
     * token校验失败
     **/
    public static final int TOKEN_ERR = 401;

    /**
     * 信息错误
     **/
    public static final int INFO_ERR = 402;

    /**
     * 信息警告
     **/
    public static final int INFO_WARN = 403;

    /**
     * 信息非法
     **/
    public static final int INFO_ILLEGAL = 404;

    /**
     * 信息提示
     **/
    public static final int INFO_TIPS = 405;

    /**
     * 信息存在
     **/
    public static final int INFO_EXIST = 406;

    /**
     * 信息不存在
     **/
    public static final int INFO_NOT_EXIST = 407;

    /**
     * 操作失败
     **/
    public static final int OPT_FAIL = 408;

    /**
     * 系统错误
     **/
    public static final int SYS_ERR = 500;
}
