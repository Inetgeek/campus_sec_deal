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

import java.io.Serializable;
import java.time.LocalDateTime;


@Data
@Accessors(chain = true)
@TableName("campus_order")
public class CampusOrder implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 订单唯一标识符
     */
    @TableId("order_id")
    private String orderId;

    /**
     * 物品唯一标识符
     */
    @TableField("goods_id")
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
    private String receiverId;

    /**
     * 下单者联系电话
     */
    @TableField("receiver_tel")
    private String receiverTel;

    /**
     * 交易地点
     */
    @TableField("deal_addr")
    private String dealAddr;

    /**
     * 订单状态
     */
    @TableField("order_status")
    private Integer orderStatus;


}
