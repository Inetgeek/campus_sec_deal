/**
 * @Class: UserPwd
 * @Date: 2022/9/22
 * @Project: campus_sec_deal
 * @Author: Colyn
 * @Builder: IntelliJ IDEA
 */
package edu.njust.campus_sec_deal.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@Accessors(chain = true)
public class UserPwd implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonProperty("oPwd")
    @NotNull(message = "密码不可为空")
    @NotBlank(message = "密码不可为空")
    @Length(max = 16, min = 6, message = "用户登录密码长度不合法")
    private String oPwd;

    @JsonProperty("nPwd")
    @NotNull(message = "密码不可为空")
    @NotBlank(message = "密码不可为空")
    @Length(max = 16, min = 6, message = "用户登录密码长度不合法")
    private String nPwd;
}
