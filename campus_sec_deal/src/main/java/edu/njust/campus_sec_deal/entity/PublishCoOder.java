/**
 * @Class: PublishCoOder
 * @Date: 2022/9/22
 * @Project: campus_sec_deal
 * @Author: Colyn
 * @Builder: IntelliJ IDEA
 */

package edu.njust.campus_sec_deal.entity;

import lombok.Data;

import java.time.LocalDateTime;


@Data
public class PublishCoOder {
    private static final long serialVersionUID = 1L;

    /**
     * 发布信息唯一标识符
     */
    private String publishId;

    /**
     * 发布者信息唯一标识符
     */
    private String publisherId;

    /**
     * 发布者联系电话
     */
    private String publisherTel;

    /**
     * 信息发布时间戳
     */
    private LocalDateTime publishTime;

    /**
     * 发布信息类别
     */
    private Integer publishType;

    /**
     * 信息是否显示
     */
    private Boolean publishStatus;

    /**
     * 物品名称
     */
    private String publishName;

    /**
     * 物品描述
     */
    private String publishDescribe;

    /**
     * 物品分类
     */
    private Integer publishCat;

    /**
     * 物品原价
     */
    private Float publishOprice;

    /**
     * 物品现价
     */
    private Float publishNprice;

    /**
     * 物品新旧程度
     */
    private Integer publishDegree;

    /**
     * 物品图片本地相对路径
     */
    private String imgUrl;

    /**
     * 订单唯一标识符
     */
    private String orderId;

    /**
     * 物品唯一标识符
     */
    private String goodsId;

    /**
     * 订单生成时间戳
     */
    private LocalDateTime orderTime;

    /**
     * 物品唯一标识符
     */
    private String receiverId;

    /**
     * 下单者联系电话
     */
    private String receiverTel;

    /**
     * 交易地点
     */
    private String dealAddr;

    /**
     * 订单状态
     */
    private Integer orderStatus;
}
