/**
 * @Class: WalletManageController
 * @Date: 2022/9/21
 * @Project: campus_sec_deal
 * @Author: Colyn
 * @Builder: IntelliJ IDEA
 */

package edu.njust.campus_sec_deal.controller;

import edu.njust.campus_sec_deal.config.ReturnCodeConf;
import edu.njust.campus_sec_deal.entity.OptWallet;
import edu.njust.campus_sec_deal.service.CampusWalletService;
import edu.njust.campus_sec_deal.utils.JWTUtil;
import edu.njust.campus_sec_deal.utils.JsonResultUtil;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@Validated
@PropertySource("classpath:application.properties")
@RequestMapping(value = "/api/v1/wallet")
public class WalletManageController {

    @Autowired
    CampusWalletService walletService;

    /**
     * 余额充值接口
     *
     * @param wallet
     * @param request
     * @return status
     */
    @PostMapping("/charge_wallet")
    public JsonResultUtil<?> chargeWallet(@RequestBody @Validated OptWallet wallet, @NotNull HttpServletRequest request) {
        String token = request.getHeader("token");
        //从token中取出用户ID
        String uid = JWTUtil.verifyToken(token, "userId");
        if (uid == null || uid.equals("")) {
            return new JsonResultUtil<>(ReturnCodeConf.TOKEN_ERR, "token错误，请登录");
        } else {
            //验证信息是否正确
            if (walletService.isRightPwd(uid, wallet.getWalletPwd())) {
                if (walletService.updateWallet(uid, wallet.getWalletMoney())) {
                    return new JsonResultUtil<>();
                } else return new JsonResultUtil<>(ReturnCodeConf.SYS_ERR, "系统错误，充值失败");
            } else return new JsonResultUtil<>(ReturnCodeConf.INFO_ERR, "支付密码错误");
        }
    }

    /**
     * 余额提现接口
     *
     * @param wallet
     * @param request
     * @return status
     */
    @PostMapping("/withdraw_wallet")
    public JsonResultUtil<?> withdrawWallet(@RequestBody @Validated OptWallet wallet, @NotNull HttpServletRequest request) {
        String token = request.getHeader("token");
        //从token中取出用户ID
        String uid = JWTUtil.verifyToken(token, "userId");
        if (uid == null || uid.equals("")) {
            return new JsonResultUtil<>(ReturnCodeConf.TOKEN_ERR, "token错误，请登录");
        } else {
            //验证信息是否正确
            if (walletService.isRightPwd(uid, wallet.getWalletPwd())) {
                //如果余额小于提现金额
                if (walletService.getWallet(uid).getWalletBalance() > wallet.getWalletMoney()) {
                    //如果顺利提现
                    if (walletService.updateWallet(uid, -wallet.getWalletMoney())) {
                        return new JsonResultUtil<>();
                    } else return new JsonResultUtil<>(ReturnCodeConf.SYS_ERR, "系统错误，充值失败");
                } else return new JsonResultUtil<>(ReturnCodeConf.INFO_WARN, "余额不足");
            } else return new JsonResultUtil<>(ReturnCodeConf.INFO_ERR, "支付密码错误");
        }
    }
}