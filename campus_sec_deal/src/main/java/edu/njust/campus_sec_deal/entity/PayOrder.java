/**
 * @Class: PayOrder
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

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@Accessors(chain = true)
public class PayOrder implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonProperty("orderId")
    @Length(max = 32, min = 32, message = "订单ID不合法")
    private String orderId;

    @JsonProperty("payPwd")
    @NotNull(message = "密码不可为空")
    @Min(value = 100000, message = "用户登录密码长度不合法")
    @Max(value = 999999, message = "用户登录密码长度不合法")
    private Integer payPwd;
}