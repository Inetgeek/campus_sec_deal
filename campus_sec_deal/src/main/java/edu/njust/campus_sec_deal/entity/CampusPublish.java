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
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDateTime;


@Data
@Accessors(chain = true)
@TableName("campus_publish")
public class CampusPublish implements Serializable {

    private static final long serialVersionUID = 1L;

    public interface PublishPost {
    }

    public interface PublishModify {
    }

    /**
     * 发布信息唯一标识符
     */
    @TableId("publish_id")
    @Length(max = 14, min = 14, message = "物品ID不合法", groups = PublishModify.class)
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
    @Pattern(regexp = "^1(3[0-9]|4[01456879]|5[0-35-9]|6[2567]|7[0-8]|8[0-9]|9[0-35-9])\\d{8}$", message = "电话不合法", groups = {PublishPost.class, PublishModify.class})
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
    @Min(value = 0, message = "发布信息类别只能是0或1", groups = {PublishPost.class, PublishModify.class})
    @Max(value = 1, message = "发布信息类别只能是0或1", groups = {PublishPost.class, PublishModify.class})
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
    @NotNull(message = "物品名称不可为空", groups = {PublishPost.class, PublishModify.class})
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
    @Min(value = 0, message = "物品分类只能是0-11", groups = {PublishPost.class, PublishModify.class})
    @Max(value = 11, message = "物品分类只能是0-11", groups = {PublishPost.class, PublishModify.class})
    private Integer publishCat;

    /**
     * 物品原价
     */
    @TableField("publish_Oprice")
    @Digits(integer = 6, fraction = 2, message = "金额设置不合法", groups = {PublishPost.class, PublishModify.class})
    private Float publishOprice;

    /**
     * 物品现价
     */
    @TableField("publish_Nprice")
    @Digits(integer = 6, fraction = 2, message = "金额设置不合法", groups = {PublishPost.class, PublishModify.class})
    private Float publishNprice;

    /**
     * 物品新旧程度
     */
    @TableField("publish_degree")
    @Min(value = 1, message = "物品分类只能是1-10", groups = {PublishPost.class, PublishModify.class})
    @Max(value = 10, message = "物品分类只能是1-10", groups = {PublishPost.class, PublishModify.class})
    private Integer publishDegree;

    /**
     * 物品图片本地相对路径
     */
    @TableField("img_url")
    @NotNull(message = "物品图片URL不可为空", groups = {PublishPost.class, PublishModify.class})
    private String imgUrl;


}
