/**
 * <p>
 * 账户钱包表
 * </p>
 *
 * @author Colyn
 * @since 2022-09-18
 */

package edu.njust.campus_sec_deal.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;


@Data
@Accessors(chain = true)
@TableName("campus_wallet")
public class CampusWallet implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户钱包唯一标识符
     */
    @TableId("wallet_id")
    private String walletId;

    /**
     * 用户信息唯一标识符
     */
    @TableField("user_id")
    private String userId;

    /**
     * 用户的钱包账户余额
     */
    @TableField("wallet_balance")
    private Float walletBalance;

    /**
     * 用户钱包支付密码
     */
    @TableField("wallet_pwd")
    private Integer walletPwd;


}
