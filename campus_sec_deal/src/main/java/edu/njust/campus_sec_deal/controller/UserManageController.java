/**
 * @Class: UserManageController
 * @Date: 2022/9/21
 * @Project: campus_sec_deal
 * @Author: Colyn
 * @Builder: IntelliJ IDEA
 */

package edu.njust.campus_sec_deal.controller;

import edu.njust.campus_sec_deal.config.ReturnCodeConf;
import edu.njust.campus_sec_deal.entity.CampusUser;
import edu.njust.campus_sec_deal.service.CampusUserService;
import edu.njust.campus_sec_deal.utils.JWTUtil;
import edu.njust.campus_sec_deal.utils.JsonResultUtil;
import org.hibernate.validator.constraints.Length;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@Validated
@PropertySource("classpath:application.properties")
@RequestMapping(value = "/api/v1")
public class UserManageController {

    @Value("${campus.default.user-sign}")
    private String user_sign;

    @Value("${campus.default.user-logo}")
    private String img_url;

    @Autowired
    CampusUserService userService;

    public static Logger log = LoggerFactory.getLogger(UserManageController.class);

    /**
     * 用户注册接口
     *
     * @param user
     * @return status
     */
    @PostMapping("/register")
    public JsonResultUtil<?> userRegister(@NotNull @RequestBody @Validated(CampusUser.Register.class) CampusUser user) {
        log.info("[user] " + user);

        if (userService.insertUser(user, user_sign, img_url)) {
            return new JsonResultUtil<>();
        } else return new JsonResultUtil<>(ReturnCodeConf.OPT_FAIL, "注册信息可能已存在");
    }

    /**
     * 用户找回密码接口
     *
     * @param user
     * @return status
     */
    @PutMapping("/reset_pwd")
    public JsonResultUtil<?> resetPwd(@NotNull @RequestBody @Validated(CampusUser.ResetPwd.class) CampusUser user) {

        if (userService.isRightUser(user)) {
            if (userService.retrievePassword(user)) {
                Map<String, String> data = new HashMap<>();
                data.put("newPwd", user.getUserPwd());
                return new JsonResultUtil<>(data);
            } else return new JsonResultUtil<>(ReturnCodeConf.SYS_ERR, "系统出错，找回密码失败");
        } else return new JsonResultUtil<>(ReturnCodeConf.INFO_ERR, "填写信息错误");
    }

    /**
     * 用户登录接口
     *
     * @param user
     * @return status
     */
    @PostMapping("/login")
    public JsonResultUtil<?> userLogin(@NotNull @RequestBody Map<String, String> user) {
        System.out.println("getUser: " + user);

        //验证信息是否正确
        CampusUser get_user = userService.loginCheck(user);
        if (get_user != null) {

            //封装payload信息
            Map<String, String> payload = new HashMap<>();
            payload.put("userId", get_user.getUserId());

            //封装token到data
            Map<String, Object> data = new HashMap<>();
            data.put("token", JWTUtil.getToken(payload));
            data.put("user", get_user);

            return new JsonResultUtil<>(data);
        } else return new JsonResultUtil<>(ReturnCodeConf.INFO_ILLEGAL, "认证信息错误，登陆失败");
    }

    /**
     * 获取用户简介
     *
     * @param uid
     * @return map<User>
     */
    @GetMapping("/get_user_info")
    public JsonResultUtil<?> getUserInfo(@Length(max = 14, min = 14, message = "用户ID不合法") String uid) {

        Map<String, String> data = userService.getUserInfo(uid);
        if (data != null) {
            return new JsonResultUtil<>(data);
        } else return new JsonResultUtil<>(ReturnCodeConf.INFO_NOT_EXIST, "找不到该用户信息");
    }
}
