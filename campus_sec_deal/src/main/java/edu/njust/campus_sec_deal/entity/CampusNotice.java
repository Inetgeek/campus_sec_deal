/**
 * <p>
 * 订单通知表
 * </p>
 *
 * @author Colyn
 * @since 2022-10-02
 */

package edu.njust.campus_sec_deal.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Data;
import lombok.experimental.Accessors;


@Data
@Accessors(chain = true)
@TableName("campus_notice")
public class CampusNotice implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 订单唯一标识符
     */
    @TableId("order_id")
    private String orderId;

    /**
     * 操作时间戳
     */
    @TableField("opt_time")
    private LocalDateTime optTime;

    /**
     * 发布者信息唯一标识符
     */
    @TableField("publisher_id")
    private String publisherId;

    /**
     * 物品唯一标识符
     */
    @TableField("receiver_id")
    private String receiverId;

    /**
     * 订单状态
     */
    @TableField("order_status")
    private Integer orderStatus;
}
