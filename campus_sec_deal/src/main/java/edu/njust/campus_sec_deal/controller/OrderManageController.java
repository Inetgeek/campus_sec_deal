/**
 * @Class: OrderManageController
 * @Date: 2022/9/21
 * @Project: campus_sec_deal
 * @Author: Colyn
 * @Builder: IntelliJ IDEA
 */

package edu.njust.campus_sec_deal.controller;

import edu.njust.campus_sec_deal.config.OrderStatusConf;
import edu.njust.campus_sec_deal.config.PublishTypeConf;
import edu.njust.campus_sec_deal.entity.*;
import edu.njust.campus_sec_deal.service.CampusNoticeService;
import edu.njust.campus_sec_deal.service.CampusOrderService;
import edu.njust.campus_sec_deal.service.CampusPublishService;
import edu.njust.campus_sec_deal.service.CampusWalletService;
import edu.njust.campus_sec_deal.utils.JWTUtil;
import edu.njust.campus_sec_deal.utils.JsonResultUtil;
import edu.njust.campus_sec_deal.utils.RandomDataUtil;
import org.hibernate.validator.constraints.Length;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import edu.njust.campus_sec_deal.config.ReturnCodeConf;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Validated
@PropertySource("classpath:application.properties")
@RequestMapping(value = "/api/v1/order")
public class OrderManageController {

    @Autowired
    CampusPublishService publishService;

    @Autowired
    CampusOrderService orderService;

    @Autowired
    CampusWalletService walletService;

    @Autowired
    CampusNoticeService noticeService;


    /**
     * 生成订单接口
     *
     * @param order
     * @param request
     * @return status
     */
    @PostMapping("/gen_order")
    public JsonResultUtil<?> genOrder(@RequestBody @Validated(CampusOrder.genOrder.class) CampusOrder order, @NotNull HttpServletRequest request) {
        /**
         * 注意：这个只是订单生成接口，默认的订单状态为”待付款“，因此该接口并不能自动支付，支付请调用支付订单接口pay_order
         * 订单处于”待付款“状态30min后会被自动取消该订单
         */
        String token = request.getHeader("token");
        //从token中取出用户ID
        String uid = JWTUtil.verifyToken(token, "userId");
        if (uid == null || uid.equals("")) {
            return new JsonResultUtil<>(ReturnCodeConf.TOKEN_ERR, "token错误，请登录");
        } else {
            CampusPublish publish = publishService.getOnePublish(order.getGoodsId());
            if (publish == null) return new JsonResultUtil<>(ReturnCodeConf.INFO_NOT_EXIST, "找不到该物品信息");
            if (!publish.getPublishStatus()) return new JsonResultUtil<>(ReturnCodeConf.OPT_FAIL, "该物品已有订单，下单失败");
            //如果是发布者本人
            if (uid.equals(publish.getPublisherId())) {
                return new JsonResultUtil<>(ReturnCodeConf.INFO_ILLEGAL, "不能下单自己发布的物品");
            } else {

                String order_id = RandomDataUtil.getUUID32();
                if (orderService.insertOrder(order_id, OrderStatusConf.ORDER_PAY, order, uid) && publishService.updatePublish(publish.getPublishId(), false, null, uid)) {

                    //创建订单提醒
                    Map<String, String> map = new HashMap<>();
                    map.put("order_id", order_id);
                    map.put("publisher_id", publish.getPublisherId());
                    map.put("receiver_id", uid);
                    map.put("order_status", String.valueOf(OrderStatusConf.ORDER_PAY));
                    noticeService.insertOrderNotice(map);

                    //添加订单编号到data键里
                    Map<String, String> data = new HashMap<>();
                    data.put("order_id", order_id);
                    return new JsonResultUtil<>(data);
                } else return new JsonResultUtil<>(ReturnCodeConf.SYS_ERR, "系统错误，下单失败");
            }
        }
    }


    /**
     * 支付订单接口
     *
     * @param order
     * @param request
     * @return status
     */
    @PostMapping("/pay_order")
    public JsonResultUtil<?> payOrder(@RequestBody @Validated PayOrder order, @NotNull HttpServletRequest request) {
        /**
         * 注意：此部分是对上述生成订单进行支付，来使得订单状态变成”待交易“
         * 订单处于”待交易”状态7days后会被自动取消该订单
         * 仅付款方（征品的发布者或商品的下单者）才有权调用该接口
         */
        String token = request.getHeader("token");
        //从token中取出用户ID
        String uid = JWTUtil.verifyToken(token, "userId");
        if (uid == null || uid.equals("")) {
            return new JsonResultUtil<>(ReturnCodeConf.TOKEN_ERR, "token错误，请登录");
        } else {
            CampusWallet wallet = walletService.getWallet(uid);
            CampusPublish publish = publishService.getPublishByOrder(order.getOrderId());
            CampusOrder get_order = orderService.getOneOrder(order.getOrderId());

            //验证支付密码
            if (!wallet.getWalletPwd().equals(order.getPayPwd())) {
                return new JsonResultUtil<>(ReturnCodeConf.INFO_ERR, "支付密码错误");
            } else {         //支付密码正确

                /**对于商品和征品均只能买家付款，卖家无需付款**/
                if (get_order != null/*订单存在*/
                        && get_order.getOrderStatus().equals(OrderStatusConf.ORDER_PAY)/*待付款状态*/
                        && ((publish.getPublishType().equals(PublishTypeConf.GOODS)/*是商品*/
                        && get_order.getReceiverId().equals(uid)/*是买家*/)
                        || (publish.getPublishType().equals(PublishTypeConf.DEMAND)/*是征品*/
                        && publish.getPublisherId().equals(uid)/*是买家*/))) {

                    //查询商（征）品价格
                    if (wallet.getWalletBalance() < publish.getPublishNprice()) {     //用户余额不足以支付订单
                        return new JsonResultUtil<>(ReturnCodeConf.INFO_WARN, "余额不足，请充值");
                    } else {

                        if (orderService.updateOrder(order.getOrderId(), OrderStatusConf.ORDER_DEAL) && walletService.updateWallet(uid, -publish.getPublishNprice())) {
                            return new JsonResultUtil<>();
                        } else return new JsonResultUtil<>(ReturnCodeConf.SYS_ERR, "系统错误，支付失败");
                    }
                } else return new JsonResultUtil<>(ReturnCodeConf.OPT_FAIL, "你无需支付");
            }
        }
    }


    /**
     * 获取订单接口
     *
     * @param request
     * @return status
     */
    @GetMapping("/get_order")
    public JsonResultUtil<?> getOrder(@NotNull HttpServletRequest request) {
        String token = request.getHeader("token");

        //从token中取出用户ID
        String uid = JWTUtil.verifyToken(token, "userId");
        if (uid == null || uid.equals("")) {
            return new JsonResultUtil<>(ReturnCodeConf.TOKEN_ERR, "token错误，请登录");
        } else {

            /**
             * 分别获取用户作为发布者和下单者的信息，封装成json格式
             */
            //封装成json
            Map<String, List<PublishCoOder>> order_data = orderService.getAllOrder(uid);
            // 过滤掉超时的订单
            Map<String, List<PublishCoOder>> orderList = orderService.filterAllOrderByTime(order_data);
            if (orderList != null) {
                return new JsonResultUtil<>(orderList);
            } else return new JsonResultUtil<>(ReturnCodeConf.INFO_NOT_EXIST, "找不到相关订单");
        }
    }

    /**
     * 取消订单接口
     *
     * @param oid
     * @param request
     * @return status
     */
    @PutMapping("/cancel_order")
    public JsonResultUtil<?> cancelOrder(@Length(max = 32, min = 32, message = "订单ID不合法") String oid, @NotNull HttpServletRequest request) {
        /**
         * 取消订单是用户主动取消的接口
         * 取消的订单仅处于“待付款“或“待交易”状态可取消，如果处于“待付款“状态将直接取消，如果处于“待交易”状态则分如下2种情况：
         * 1.商品：钱退还至下单者（买家）  2.征品：钱退还至发布者（买家）
         * 注意：仅下单方可取消订单，征品为卖家下单，商品为买家下单
         */
        String token = request.getHeader("token");
        //从token中取出用户ID
        String uid = JWTUtil.verifyToken(token, "userId");
        if (uid == null || uid.equals("")) {
            return new JsonResultUtil<>(ReturnCodeConf.TOKEN_ERR, "token错误，请登录");
        } else {
            CampusOrder get_order = orderService.getOneOrder(oid);
            if(get_order == null) return new JsonResultUtil<>(ReturnCodeConf.INFO_NOT_EXIST, "找不到该订单信息");

            CampusPublish get_publish = publishService.getOnePublish(get_order.getGoodsId());

            /**仅下单者可取消订单**/
            if (!uid.equals(get_order.getReceiverId())) {
                return new JsonResultUtil<>(ReturnCodeConf.INFO_ILLEGAL, "无权操作");
            } else {
                if (get_order.getOrderStatus().equals(OrderStatusConf.ORDER_OVER)/*已完成*/) {
                    return new JsonResultUtil<>(ReturnCodeConf.INFO_WARN, "该订单已完成，不可取消订单");

                } else if (get_order.getOrderStatus().equals(OrderStatusConf.ORDER_CANCEL)/*已取消*/) {
                    return new JsonResultUtil<>(ReturnCodeConf.OPT_FAIL, "该订单已取消，不可重复操作");
                } else {

                    publishService.updatePublish(get_publish.getPublishId(), true, null, uid);//先修改发布的物品为可显示状态
                    orderService.updateOrder(get_order.getOrderId(), OrderStatusConf.ORDER_CANCEL);//修改订单为已取消
                    if (get_order.getOrderStatus().equals(OrderStatusConf.ORDER_DEAL)) {   //如果是待交易，退钱

                        //如果是商品，退钱给下单者
                        if (get_publish.getPublishType().equals(PublishTypeConf.GOODS))
                            walletService.updateWallet(uid, get_publish.getPublishNprice());

                            //如果是征品，退钱给发布者
                        else walletService.updateWallet(get_publish.getPublisherId(), get_publish.getPublishNprice());
                    }
                    return new JsonResultUtil<>();
                }
            }
        }
    }

    /**
     * 完成订单接口
     *
     * @param oid
     * @param request
     * @return status
     */
    @PutMapping("over_order")
    public JsonResultUtil<?> overOrder(@Length(max = 32, min = 32, message = "订单ID不合法") String oid, @NotNull HttpServletRequest request) {
        String token = request.getHeader("token");
        //从token中取出用户ID
        String uid = JWTUtil.verifyToken(token, "userId");
        if (uid == null || uid.equals("")) {
            return new JsonResultUtil<>(ReturnCodeConf.TOKEN_ERR, "token错误，请登录");
        } else {

            /**
             * 仅付款方可结束订单
             * 如果是征品订单，仅发布者可结束订单
             * 如果是商品订单，仅下单者可结束订单
             * 结束订单时，如果商品，则打钱给发布者（卖家）；如果是征品，则打钱给下单者（卖家）
             */
            CampusOrder get_order = orderService.getOneOrder(oid);
            if(get_order == null) return new JsonResultUtil<>(ReturnCodeConf.INFO_NOT_EXIST, "找不到该订单信息");

            CampusPublish get_publish = publishService.getOnePublish(get_order.getGoodsId());

            /**订单状态为"待交易"才可结束订单**/
            if (!get_order.getOrderStatus().equals(OrderStatusConf.ORDER_DEAL))
                return new JsonResultUtil<>(ReturnCodeConf.INFO_WARN, "订单状态不符合完成条件");
            else {
                if (get_publish.getPublishType().equals(PublishTypeConf.DEMAND)/*征品*/ && get_publish.getPublisherId().equals(uid)/*发布者*/) {

                    if (walletService.updateWallet(get_order.getReceiverId(), get_publish.getPublishNprice()) //征品，打钱给下单者（卖家）
                            && orderService.updateOrder(get_order.getOrderId(), OrderStatusConf.ORDER_OVER)) {
                        return new JsonResultUtil<>();
                    } else return new JsonResultUtil<>(ReturnCodeConf.SYS_ERR, "系统错误，操作失败");

                } else if (get_publish.getPublishType().equals(PublishTypeConf.GOODS)/*商品*/ && get_order.getReceiverId().equals(uid)/*下单者*/) {

                    if (walletService.updateWallet(get_publish.getPublisherId(), get_publish.getPublishNprice()) //商品，打钱给发布者（卖家）
                            && orderService.updateOrder(get_order.getOrderId(), OrderStatusConf.ORDER_OVER)) {
                        return new JsonResultUtil<>();
                    } else return new JsonResultUtil<>(ReturnCodeConf.SYS_ERR, "系统错误，操作失败");

                } else return new JsonResultUtil<>(ReturnCodeConf.INFO_ILLEGAL, "无权操作");
            }
        }
    }
}
