/**
 * <p>
 * 发布信息表
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
@TableName("campus_publish")
public class CampusPublish implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 发布信息唯一标识符
     */
    @TableId("publish_id")
    private String publishId;

    /**
     * 发布者信息唯一标识符
     */
    @TableField("publisher_id")
    private String publisherId;

    /**
     * 发布者联系电话
     */
    @TableField("publisher_tel")
    private String publisherTel;

    /**
     * 信息发布时间戳
     */
    @TableField("publish_time")
    private LocalDateTime publishTime;

    /**
     * 发布信息类别
     */
    @TableField("publish_type")
    private Integer publishType;

    /**
     * 信息是否显示
     */
    @TableField("publish_status")
    private Boolean publishStatus;

    /**
     * 物品名称
     */
    @TableField("publish_name")
    private String publishName;

    /**
     * 物品描述
     */
    @TableField("publish_describe")
    private String publishDescribe;

    /**
     * 物品分类
     */
    @TableField("publish_cat")
    private Integer publishCat;

    /**
     * 物品原价
     */
    @TableField("publish_Oprice")
    private Float publishOprice;

    /**
     * 物品现价
     */
    @TableField("publish_Nprice")
    private Float publishNprice;

    /**
     * 物品新旧程度
     */
    @TableField("publish_degree")
    private Integer publishDegree;

    /**
     * 物品图片本地相对路径
     */
    @TableField("img_url")
    private String imgUrl;


}
