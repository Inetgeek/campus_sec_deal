/**
 * <p>
 * 用户信息表
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

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.time.LocalDate;


@Data
@Accessors(chain = true)
@TableName("campus_user")
public class CampusUser implements Serializable {

    private static final long serialVersionUID = 1L;

    public interface Register {
    }

    public interface ResetPwd {
    }

    /**
     * 用户信息唯一标识符
     */
    @TableId("user_id")
    @Length(max = 14, min = 14, message = "用户ID不合法", groups = ResetPwd.class)
    private String userId;

    /**
     * 用户名
     */
    @TableField("user_name")
    @NotNull(message = "用户名不可为空", groups = Register.class)
    @NotBlank(message = "用户名不可为空", groups = Register.class)
    private String userName;

    /**
     * 用户信息唯一标识符
     */
    @TableField("user_time")
    private LocalDate userTime;

    /**
     * 用户电话号码
     */
    @TableField("user_tel")
    @Pattern(regexp = "^1(3[0-9]|4[01456879]|5[0-35-9]|6[2567]|7[0-8]|8[0-9]|9[0-35-9])\\d{8}$", message = "手机号不合法", groups = {Register.class, ResetPwd.class})
    private String userTel;

    /**
     * 用户邮箱号
     */
    @TableField("user_mail")
    @Pattern(regexp = "^[A-Za-z0-9-_\\u4e00-\\u9fa5]+@[a-zA-Z0-9_-]+(\\.[a-z]+)+$", message = "用户邮箱号不合法", groups = {Register.class, ResetPwd.class})
    private String userMail;

    /**
     * 用户个性签名
     */
    @TableField("user_sign")
    private String userSign;

    /**
     * 用户头像本地相对路径
     */
    @TableField("img_url")
    private String imgUrl;

    /**
     * 用户登录密码
     */
    @TableField("user_pwd")
    @Length(max = 16, min = 6, message = "用户登录密码长度不合法", groups = {Register.class, ResetPwd.class})
    private String userPwd;
}
