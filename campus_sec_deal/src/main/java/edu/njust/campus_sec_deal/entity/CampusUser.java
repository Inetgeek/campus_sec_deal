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

import java.io.Serializable;
import java.time.LocalDate;


@Data
@Accessors(chain = true)
@TableName("campus_user")
public class CampusUser implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户信息唯一标识符
     */
    @TableId("user_id")
    private String userId;

    /**
     * 用户名
     */
    @TableField("user_name")
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
    private String userTel;

    /**
     * 用户邮箱号
     */
    @TableField("user_mail")
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
    private String userPwd;
}
