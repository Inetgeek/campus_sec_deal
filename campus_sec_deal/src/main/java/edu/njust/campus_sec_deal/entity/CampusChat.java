/**
 * <p>
 * 交易私聊表
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
@TableName("campus_chat")
public class CampusChat implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 聊天房间号
     */
    @TableId("chat_id")
    private String chatId;

    /**
     * 消息ID
     */
    @TableField("msg_id")
    private Integer msgId;

    /**
     * 发消息者ID
     */
    @TableField("from_id")
    private String fromId;

    /**
     * 消息内容
     */
    @TableField("chat_content")
    private String chatContent;

    /**
     * 消息发送时间戳
     */
    @TableField("send_time")
    private LocalDateTime sendTime;

    /**
     * 收消息者ID
     */
    @TableField("to_id")
    private String toId;


}
