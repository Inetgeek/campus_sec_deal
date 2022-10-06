/**
 * <p>
 * 搜索
 * </p>
 *
 * @author Colyn
 * @since 2022-10-02
 */

package edu.njust.campus_sec_deal.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.njust.campus_sec_deal.config.OrderStatusConf;
import edu.njust.campus_sec_deal.entity.CampusOrder;
import edu.njust.campus_sec_deal.entity.PublishCoOder;
import edu.njust.campus_sec_deal.mapper.CampusOrderMapper;
import edu.njust.campus_sec_deal.service.CampusOrderService;
import edu.njust.campus_sec_deal.utils.RandomDataUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

/**
 * <p>
 * 订单信息表 服务实现类
 * </p>
 *
 * @author Colyn
 * @since 2022-09-18
 */
@Service
public class CampusOrderServiceImpl extends ServiceImpl<CampusOrderMapper, CampusOrder> implements CampusOrderService {

    @Autowired
    CampusOrderMapper orderMapper;

    @Value("${campus.default.time-to-pay}")
    private int time_to_pay;
    @Value("${campus.default.time-to-ex}")
    private int time_to_ex;

    public static Logger log = LoggerFactory.getLogger(CampusOrderServiceImpl.class);

    public boolean hasOrder(String pid) {
        Long cnt = orderMapper.selectCount(Wrappers.<CampusOrder>lambdaQuery().eq(CampusOrder::getGoodsId, pid));
        return cnt > 0;
    }

    @Override
    public boolean insertOrder(String oid, int status, CampusOrder order, String uid) {

        order.setOrderId(oid)
                .setOrderTime(RandomDataUtil.getDateTime())
                .setOrderStatus(status)
                .setReceiverId(uid);
        try {
            orderMapper.insert(order);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean updateOrder(String oid, int status) {
        CampusOrder order = new CampusOrder()
                .setOrderId(oid)
                .setOrderStatus(status);
        try {
            orderMapper.updateById(order);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Map<String, List<PublishCoOder>> getAllOrder(String uid) {

        try {
            List<PublishCoOder> as_publisher = orderMapper.orderListALLByPublisher(uid);
            List<PublishCoOder> as_receiver = orderMapper.orderListALLByReceiver(uid);

            //封装成Map信息
            Map<String, List<PublishCoOder>> map = new HashMap<>();
            if (!as_publisher.isEmpty()) {
                map.put("publish", as_publisher);
            } else map.put("publish", null);

            if (!as_receiver.isEmpty()) {
                map.put("receive", as_receiver);
            } else map.put("receive", null);

            return map;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Map<String, List<PublishCoOder>> filterAllOrderByTime(Map<String, List<PublishCoOder>> map) {

        List<PublishCoOder> as_publisher = map.get("publish");
        List<PublishCoOder> as_receiver = map.get("receive");

        // 过滤 publisher中的过期订单
        do {
            if (as_publisher == null) {
                break;
            }
            Iterator<PublishCoOder> iter = as_publisher.iterator();
            while (iter.hasNext()) {
                PublishCoOder order = iter.next();
                if (order.getOrderStatus().equals(OrderStatusConf.ORDER_PAY)) {  // 如果是待付款订单
                    LocalDateTime ddl = order.getOrderTime().plus(time_to_pay, ChronoUnit.MINUTES);

                    LocalDateTime now = RandomDataUtil.getDateTime();

                    long time = now.until(ddl, ChronoUnit.SECONDS);
                    if (time <= 0) {
                        // 取消订单  将status设置为1
                        order.setOrderStatus(OrderStatusConf.ORDER_CANCEL);
                        // 把数据库的值也同步修改
                        updateOrder(order.getOrderId(), OrderStatusConf.ORDER_CANCEL);
                    }
                } else if (order.getOrderStatus().equals(OrderStatusConf.ORDER_DEAL)) {  // 如果是待交易订单
                    LocalDateTime ddl = order.getOrderTime().plus(time_to_ex, ChronoUnit.MINUTES);

                    LocalDateTime now = RandomDataUtil.getDateTime();

                    long time = now.until(ddl, ChronoUnit.SECONDS);

                    if (time <= 0) {
                        // 取消订单  将status设置为1
                        order.setOrderStatus(OrderStatusConf.ORDER_CANCEL);
                        // 把数据库的值也同步修改
                        updateOrder(order.getOrderId(), OrderStatusConf.ORDER_CANCEL);
                    }
                }
            }
        } while (false);

        // 过滤 receiver 中的过期订单
        do {
            if (as_receiver == null) {
                break;
            }
            Iterator<PublishCoOder> iter = as_receiver.iterator();
            while (iter.hasNext()) {
                PublishCoOder order = iter.next();
                if (order.getOrderStatus() == 3) {  // 如果是待付款订单
                    LocalDateTime ddl = order.getOrderTime().plus(time_to_pay, ChronoUnit.MINUTES);

                    LocalDateTime now = RandomDataUtil.getDateTime();

                    long time = now.until(ddl, ChronoUnit.SECONDS);
                    if (time <= 0) {
                        // 取消订单  将status设置为1
                        order.setOrderStatus(OrderStatusConf.ORDER_CANCEL);
                        // 把数据库的值也同步修改
                        updateOrder(order.getOrderId(), OrderStatusConf.ORDER_CANCEL);
                    }
                } else if (order.getOrderStatus().equals(OrderStatusConf.ORDER_DEAL)) {  // 如果是待交易订单
                    LocalDateTime ddl = order.getOrderTime().plus(time_to_ex, ChronoUnit.MINUTES);

                    LocalDateTime now = RandomDataUtil.getDateTime();

                    long time = now.until(ddl, ChronoUnit.SECONDS);

                    if (time <= 0) {
                        // 取消订单  将status设置为1
                        order.setOrderStatus(OrderStatusConf.ORDER_CANCEL);
                        // 把数据库的值也同步修改
                        updateOrder(order.getOrderId(), OrderStatusConf.ORDER_CANCEL);
                    }
                }
            }
        } while (false);

        Map<String, List<PublishCoOder>> result = new HashMap<>();
        if (as_publisher == null || !as_publisher.isEmpty()) {
            result.put("publish", as_publisher);
        } else result.put("publish", null);

        if (as_receiver == null || !as_receiver.isEmpty()) {
            result.put("receive", as_receiver);
        } else result.put("receive", null);

        return result;
    }

    @Override
    public CampusOrder getOneOrder(String oid) {
        try {
            return orderMapper.selectById(oid);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
