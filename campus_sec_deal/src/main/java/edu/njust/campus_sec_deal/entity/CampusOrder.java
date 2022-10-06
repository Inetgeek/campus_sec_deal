/**
 * <p>
 * 订单信息表
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
import java.time.LocalDateTime;


@Data
@Accessors(chain = true)
@TableName("campus_order")
public class CampusOrder implements Serializable {

    private static final long serialVersionUID = 1L;

    public interface genOrder{}

    /**
     * 订单唯一标识符
     */
    @TableId("order_id")
    @Length(max = 32, min = 32, message = "订单ID不合法")
    private String orderId;

    /**
     * 物品唯一标识符
     */
    @TableField("goods_id")
    @NotNull(message = "物品ID不可为空")
    @Length(max = 14, min = 14, message = "物品ID不合法", groups = {genOrder.class})
    private String goodsId;

    /**
     * 订单生成时间戳
     */
    @TableField("order_time")
    private LocalDateTime orderTime;

    /**
     * 物品唯一标识符
     */
    @TableField("receiver_id")
    @NotNull(message = "用户ID不可为空")
    @Length(max = 14, min = 14, message = "用户ID不合法")
    private String receiverId;

    /**
     * 下单者联系电话
     */
    @TableField("receiver_tel")
    @NotNull(message = "用户电话不可为空")
    @Pattern(regexp = "^1(3[0-9]|4[01456879]|5[0-35-9]|6[2567]|7[0-8]|8[0-9]|9[0-35-9])\\d{8}$", message = "电话不合法", groups = {genOrder.class})
    private String receiverTel;

    /**
     * 交易地点
     */
    @TableField("deal_addr")
    @NotNull(message = "交易地址不可为空", groups = {genOrder.class})
    @NotBlank(message = "交易地址不可为空", groups = {genOrder.class})
    private String dealAddr;

    /**
     * 订单状态
     */
    @TableField("order_status")
    private Integer orderStatus;


}
