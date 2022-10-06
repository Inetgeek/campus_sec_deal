/**
 * @Class: OrderStatusConf
 * @Date: 2022/9/20
 * @Project: campus_sec_deal
 * @Author: Colyn
 * @Builder: IntelliJ IDEA
 */
package edu.njust.campus_sec_deal.config;

public class OrderStatusConf {
    /**
     * 订单已完成
     */
    public static final int ORDER_OVER = 0;

    /**
     * 订单已取消
     */
    public static final int ORDER_CANCEL = 1;

    /**
     * 订单待交易
     */
    public static final int ORDER_DEAL = 2;

    /**
     * 订单待付款
     */
    public static final int ORDER_PAY = 3;
}
