/**
 * @Class: CheckInfoController
 * @Date: 2022/9/20
 * @Project: campus_sec_deal
 * @author: Colyn
 * @Builder: IntelliJ IDEA
 */

package edu.njust.campus_sec_deal.controller;

import edu.njust.campus_sec_deal.config.ReturnCodeConf;
import edu.njust.campus_sec_deal.service.CampusOrderService;
import edu.njust.campus_sec_deal.service.CampusUserService;
import edu.njust.campus_sec_deal.service.CampusWalletService;
import edu.njust.campus_sec_deal.utils.JsonResultUtil;
import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Pattern;
import java.util.HashMap;
import java.util.Map;

@RestController
@Validated
@RequestMapping(value = "/api/v1/check")
public class CheckInfoController {

    @Autowired
    CampusUserService userService;

    @Autowired
    CampusOrderService orderService;

    @Autowired
    CampusWalletService walletService;

    private final String tel_reg = "^1(3[0-9]|4[01456879]|5[0-35-9]|6[2567]|7[0-8]|8[0-9]|9[0-35-9])\\d{8}$";
    private final String mail_reg = "^[A-Za-z0-9-_\\u4e00-\\u9fa5]+@[a-zA-Z0-9_-]+(\\.[a-z]+)+$";

    /**
     * 电话号验证接口
     *
     * @param tel
     * @return status
     */
    @GetMapping("/user_tel")
    public JsonResultUtil<?> checkTel(@Pattern(regexp = tel_reg, message = "手机号不合法") String tel) {

        if (!tel.isEmpty()) {   //前端传来的Tel非空时
            //查询是否存在该号码
            if (userService.hasTel(tel)) {
                return new JsonResultUtil<>(ReturnCodeConf.INFO_EXIST, "该号码已存在");
            } else return new JsonResultUtil<>(ReturnCodeConf.INFO_NOT_EXIST, "该号码不存在");
        }
        return new JsonResultUtil<>(ReturnCodeConf.INFO_WARN, "号码不可为空");
    }


    /**
     * 邮箱号验证接口
     *
     * @param mail
     * @return status
     */
    @GetMapping("/user_mail")
    public JsonResultUtil<?> checkMail(@Pattern(regexp = mail_reg, message = "用户邮箱号不合法") String mail) {

        if (!mail.isEmpty()) {   //前端传来的Mail非空时
            //查询是否存在该邮箱
            if (userService.hasMail(mail)) {
                return new JsonResultUtil<>(ReturnCodeConf.INFO_EXIST, "该邮箱已存在");
            } else return new JsonResultUtil<>(ReturnCodeConf.INFO_NOT_EXIST, "该邮箱不存在");
        }
        return new JsonResultUtil<>(ReturnCodeConf.INFO_WARN, "邮箱不可为空");
    }

    /**
     * 用户ID验证接口
     *
     * @param uid
     * @return status
     */
    @GetMapping("/user_id")
    public JsonResultUtil<?> checkUid(@Length(max = 14, min = 14, message = "用户ID不合法") String uid) {

        if (!uid.isEmpty()) {   //前端传来的uid非空时
            //查询是否存在该用户
            if (userService.hasTel(uid)) {
                return new JsonResultUtil<>(ReturnCodeConf.INFO_EXIST, "该用户已存在");
            } else return new JsonResultUtil<>(ReturnCodeConf.INFO_NOT_EXIST, "该用户不存在");
        }
        return new JsonResultUtil<>(ReturnCodeConf.INFO_WARN, "用户ID不可为空");
    }

    /**
     * 发布订单验证接口
     *
     * @param pid
     * @return status
     */
    @GetMapping("/publish_order")
    public JsonResultUtil<?> checkOrder(@Length(max = 14, min = 14, message = "物品ID不合法") String pid) {

        if (!pid.isEmpty()) {   //前端传来的物品ID非空时
            //查询是否存在该发布物品生成的订单
            if (orderService.hasOrder(pid)) {
                return new JsonResultUtil<>(ReturnCodeConf.INFO_EXIST, "已生成订单");
            } else return new JsonResultUtil<>(ReturnCodeConf.INFO_NOT_EXIST, "未生成订单");
        }
        return new JsonResultUtil<>(ReturnCodeConf.INFO_WARN, "物品ID不可为空");
    }


    /**
     * 用户余额验证接口
     *
     * @param uid
     * @return status
     */
    @GetMapping("/user_wallet")
    public JsonResultUtil<?> checkWallet(@Length(max = 14, min = 14, message = "用户ID不合法") String uid) {

        if (!uid.isEmpty()) {   //前端传来的用户ID非空时
            //查询是否存在该用户ID
            if (userService.hasUser(uid)) {
                //封装返回json的data键值对
                Map<String, Float> data = new HashMap<>();
                data.put("walletBalance", walletService.getWallet(uid).getWalletBalance());
                return new JsonResultUtil<>(data);
            } else return new JsonResultUtil<>(ReturnCodeConf.INFO_NOT_EXIST, "未找到该用户信息");
        }
        return new JsonResultUtil<>(ReturnCodeConf.INFO_WARN, "用户ID不可为空");
    }
}
