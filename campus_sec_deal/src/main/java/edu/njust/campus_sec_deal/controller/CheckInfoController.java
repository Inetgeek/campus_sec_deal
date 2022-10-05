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
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/v1/check")
public class CheckInfoController {

    @Autowired
    CampusUserService userService;

    @Autowired
    CampusOrderService orderService;

    @Autowired
    CampusWalletService walletService;

    /**
     * 电话号验证接口
     *
     * @param request
     * @return status
     */
    @GetMapping("/user_tel")
    public JsonResultUtil<?> checkTel(@NotNull HttpServletRequest request) {

        //获取请求头中的参数uer_tel的值
        String tel = request.getHeader("user_tel");

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
     * @param request
     * @return status
     */
    @GetMapping("/user_mail")
    public JsonResultUtil<?> checkMail(@NotNull HttpServletRequest request) {

        //获取请求头中的参数user_mail的值
        String mail = request.getHeader("user_mail");

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
     * @param request
     * @return status
     */
    @GetMapping("/user_id")
    public JsonResultUtil<?> checkUid(@NotNull HttpServletRequest request) {

        //获取请求头中的参数user_id的值
        String uid = request.getHeader("user_id");

        if (!uid.isEmpty()) {   //前端传来的Uid非空时
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
     * @param request
     * @return status
     */
    @GetMapping("/publish_order")
    public JsonResultUtil<?> checkOrder(@NotNull HttpServletRequest request) {

        //获取请求头中的参数publish_id的值
        String pid = request.getHeader("publish_id");

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
     * @param request
     * @return status
     */
    @GetMapping("/user_wallet")
    public JsonResultUtil<?> checkWallet(@NotNull HttpServletRequest request) {

        //获取请求头中的参数user_id的值
        String uid = request.getHeader("user_id");

        if (!uid.isEmpty()) {   //前端传来的用户ID非空时
            //查询是否存在该用户ID
            if (userService.hasUser(uid)) {
                //封装返回json的data键值对
                Map<String, Float> data = new HashMap<>();
                data.put("wallet_balance", walletService.getWallet(uid).getWalletBalance());
                return new JsonResultUtil<>(data);
            } else return new JsonResultUtil<>(ReturnCodeConf.INFO_NOT_EXIST, "未找到该用户信息");
        }
        return new JsonResultUtil<>(ReturnCodeConf.INFO_WARN, "用户ID不可为空");
    }
}
