/**
 * @Class: UserModifyController
 * @Date: 2022/9/21
 * @Project: campus_sec_deal
 * @Author: Colyn
 * @Builder: IntelliJ IDEA
 */

package edu.njust.campus_sec_deal.controller;

import edu.njust.campus_sec_deal.config.ReturnCodeConf;
import edu.njust.campus_sec_deal.config.UserTypeConf;
import edu.njust.campus_sec_deal.service.CampusUserService;
import edu.njust.campus_sec_deal.service.CampusWalletService;
import edu.njust.campus_sec_deal.utils.JWTUtil;
import edu.njust.campus_sec_deal.utils.JsonResultUtil;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@PropertySource("classpath:application.properties")
@RequestMapping(value = "/api/v1/modify")
public class UserModifyController {

    @Autowired
    CampusUserService userService;

    @Autowired
    CampusWalletService walletService;

    /**
     * 修改电话接口
     *
     * @param tel
     * @param request
     * @return status
     */
    @PutMapping("/user_tel")
    public JsonResultUtil<?> userTel(@RequestParam("user_tel") String tel, @NotNull HttpServletRequest request) {
        String token = request.getHeader("token");
        //从token中取出用户ID
        String uid = JWTUtil.verifyToken(token, "user_id");
        if (uid == null) {
            return new JsonResultUtil<>(ReturnCodeConf.TOKEN_ERR, "token错误");
        } else {
            if (!tel.isEmpty()) {   //前端传来的Tel非空时
                //查询是否存在该号码
                if (userService.hasTel(tel)) {
                    return new JsonResultUtil<>(ReturnCodeConf.INFO_EXIST, "该号码已存在");
                } else {
                    if (userService.modifyUserInfo(uid, tel, UserTypeConf.USER_TEL)) {
                        Map<String, String> data = new HashMap<>();
                        data.put("user_tel", tel);
                        return new JsonResultUtil<>(data);
                    } else return new JsonResultUtil<>(ReturnCodeConf.SYS_ERR, "系统出错，电话修改失败");
                }
            } else return new JsonResultUtil<>(ReturnCodeConf.INFO_WARN, "号码不可为空");
        }
    }

    /**
     * 修改邮箱接口
     *
     * @param mail
     * @param request
     * @return status
     */
    @PutMapping("/user_mail")
    public JsonResultUtil<?> userMail(@RequestParam("user_mail") String mail, @NotNull HttpServletRequest request) {
        String token = request.getHeader("token");
        //从token中取出用户ID
        String uid = JWTUtil.verifyToken(token, "user_id");
        if (uid == null) {
            return new JsonResultUtil<>(ReturnCodeConf.TOKEN_ERR, "token错误");
        } else {
            if (!mail.isEmpty()) {   //前端传来的Mail非空时
                //查询是否存在该邮箱
                if (userService.hasMail(mail)) {
                    return new JsonResultUtil<>(ReturnCodeConf.INFO_EXIST, "该邮箱已存在");
                } else {
                    if (userService.modifyUserInfo(uid, mail, UserTypeConf.USER_MAIL)) {
                        Map<String, String> data = new HashMap<>();
                        data.put("user_mail", mail);
                        return new JsonResultUtil<>(data);
                    } else return new JsonResultUtil<>(ReturnCodeConf.OPT_FAIL, "邮箱修改失败");
                }
            } else return new JsonResultUtil<>(ReturnCodeConf.INFO_WARN, "邮箱不可为空");
        }
    }

    /**
     * 修改个性签名接口
     *
     * @param sign
     * @param request
     * @return status
     */
    @PutMapping("/user_sign")
    public JsonResultUtil<?> userSign(@RequestParam("user_sign") String sign, @NotNull HttpServletRequest request) {
        String token = request.getHeader("token");
        //从token中取出用户ID
        String uid = JWTUtil.verifyToken(token, "user_id");
        if (uid == null) {
            return new JsonResultUtil<>(ReturnCodeConf.TOKEN_ERR, "token错误");
        } else {
            if (!sign.isEmpty()) {   //前端传来的Sign非空时
                if (userService.modifyUserInfo(uid, sign, UserTypeConf.USER_SIGN)) {
                    Map<String, String> data = new HashMap<>();
                    data.put("user_sign", sign);
                    return new JsonResultUtil<>(data);
                } else return new JsonResultUtil<>(ReturnCodeConf.OPT_FAIL, "签名修改失败");
            } else return new JsonResultUtil<>(ReturnCodeConf.INFO_WARN, "签名不可为空");
        }
    }

    /**
     * 修改用户昵称接口
     *
     * @param name
     * @param request
     * @return status
     */
    @PutMapping("/user_name")
    public JsonResultUtil<?> userName(@RequestParam("user_name") String name, @NotNull HttpServletRequest request) {
        String token = request.getHeader("token");
        //从token中取出用户ID
        String uid = JWTUtil.verifyToken(token, "user_id");
        if (uid == null) {
            return new JsonResultUtil<>(ReturnCodeConf.TOKEN_ERR, "token错误");
        } else {
            if (!name.isEmpty()) {   //前端传来的name非空时
                if (userService.modifyUserInfo(uid, name, UserTypeConf.USER_NAME)) {
                    Map<String, String> data = new HashMap<>();
                    data.put("user_name", name);
                    return new JsonResultUtil<>(data);
                } else return new JsonResultUtil<>(ReturnCodeConf.OPT_FAIL, "昵称修改失败");
            } else return new JsonResultUtil<>(ReturnCodeConf.INFO_WARN, "昵称不可为空");
        }
    }

    /**
     * 修改登录密码接口
     *
     * @param user
     * @param request
     * @return status
     */
    @PutMapping("/user_pwd")
    public JsonResultUtil<?> loginPwd(@RequestBody Map<String, String> user, @NotNull HttpServletRequest request) {
        String token = request.getHeader("token");
        //从token中取出用户ID
        String uid = JWTUtil.verifyToken(token, "user_id");
        if (uid == null) {
            return new JsonResultUtil<>(ReturnCodeConf.TOKEN_ERR, "token错误，请登录");
        } else {
            if (userService.isRightPwd(uid, user.get("Ologin_pwd")) && user.get("Nlogin_pwd") != null && !user.get("Nlogin_pwd").equals("")) {
                if (userService.modifyUserInfo(uid, user.get("Nlogin_pwd"), UserTypeConf.USER_PWD)) {
                    return new JsonResultUtil<>();
                } else return new JsonResultUtil<>(ReturnCodeConf.SYS_ERR, "系统错误，登陆密码修改失败");
            } else return new JsonResultUtil<>(ReturnCodeConf.INFO_ERR, "信息填写错误");
        }
    }

    /**
     * 修改支付密码接口
     *
     * @param user
     * @param request
     * @return
     */
    @PutMapping("/pay_pwd")
    public JsonResultUtil<?> payPwd(@RequestBody Map<String, String> user, @NotNull HttpServletRequest request) {
        String token = request.getHeader("token");
        //从token中取出用户ID
        String uid = JWTUtil.verifyToken(token, "user_id");
        if (uid == null) {
            return new JsonResultUtil<>(ReturnCodeConf.TOKEN_ERR, "token错误，请登录");
        } else {
            if (walletService.isRightPwd(uid, Integer.parseInt(user.get("Opay_pwd"))) && user.get("Npay_pwd") != null && !user.get("Npay_pwd").equals("")) {
                if (walletService.modifyPwd(uid, Integer.parseInt(user.get("Npay_pwd")))) {
                    return new JsonResultUtil<>();
                } else return new JsonResultUtil<>(ReturnCodeConf.SYS_ERR, "系统错误，支付密码修改失败");
            } else return new JsonResultUtil<>(ReturnCodeConf.INFO_ERR, "信息填写错误");
        }
    }
}
