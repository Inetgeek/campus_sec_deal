/**
 * @Class: WalletManageController
 * @Date: 2022/9/21
 * @Project: campus_sec_deal
 * @Author: Colyn
 * @Builder: IntelliJ IDEA
 */

package edu.njust.campus_sec_deal.controller;

import edu.njust.campus_sec_deal.config.ReturnCodeConf;
import edu.njust.campus_sec_deal.service.CampusWalletService;
import edu.njust.campus_sec_deal.utils.JWTUtil;
import edu.njust.campus_sec_deal.utils.JsonResultUtil;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
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
    public JsonResultUtil<?> chargeWallet(@RequestBody Map<String, String> wallet, @NotNull HttpServletRequest request) {
        String token = request.getHeader("token");
        //从token中取出用户ID
        String uid = JWTUtil.verifyToken(token, "user_id");
        if (uid == null) {
            return new JsonResultUtil<>(ReturnCodeConf.TOKEN_ERR, "token错误，请登录");
        } else {
            //验证信息是否正确
            if (walletService.isRightPwd(uid, Integer.parseInt(wallet.get("wallet_pwd")))) {
                if (walletService.updateWallet(uid, Float.parseFloat(wallet.get("charge_money")))) {
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
    public JsonResultUtil<?> withdrawWallet(@RequestBody Map<String, String> wallet, @NotNull HttpServletRequest request) {
        String token = request.getHeader("token");
        //从token中取出用户ID
        String uid = JWTUtil.verifyToken(token, "user_id");
        if (uid == null) {
            return new JsonResultUtil<>(ReturnCodeConf.TOKEN_ERR, "token错误，请登录");
        } else {
            //验证信息是否正确
            if (walletService.isRightPwd(uid, Integer.parseInt(wallet.get("wallet_pwd")))) {
                //如果余额小于提现金额
                if (walletService.getWallet(uid).getWalletBalance() > Float.parseFloat(wallet.get("withdraw_money"))) {
                    //如果顺利提现
                    if (walletService.updateWallet(uid, -Float.parseFloat(wallet.get("withdraw_money")))) {
                        return new JsonResultUtil<>();
                    } else return new JsonResultUtil<>(ReturnCodeConf.SYS_ERR, "系统错误，充值失败");
                } else return new JsonResultUtil<>(ReturnCodeConf.INFO_WARN, "余额不足");
            } else return new JsonResultUtil<>(ReturnCodeConf.INFO_ERR, "支付密码错误");
        }
    }
}