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
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Max;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
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
    @Digits(integer = 6, fraction = 2, message = "金额设置不合法")
    private Float walletBalance;

    /**
     * 用户钱包支付密码
     */
    @TableField("wallet_pwd")
    @Pattern(regexp = "^\\d{6}$", message = "支付密码长度不合法")
    private Integer walletPwd;


}
