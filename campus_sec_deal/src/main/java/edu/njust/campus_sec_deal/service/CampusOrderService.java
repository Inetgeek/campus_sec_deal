/**
 * <p>
 * 订单信息表 服务类
 * </p>
 *
 * @author Colyn
 * @since 2022-09-18
 */

package edu.njust.campus_sec_deal.service;

import com.baomidou.mybatisplus.extension.service.IService;
import edu.njust.campus_sec_deal.entity.CampusOrder;
import edu.njust.campus_sec_deal.entity.PublishCoOder;

import java.util.List;
import java.util.Map;


public interface CampusOrderService extends IService<CampusOrder> {

    boolean hasOrder(String pid);

    boolean insertOrder(String oid, int status, CampusOrder order, String uid);

    boolean updateOrder(String oid, int status);

    Map<String, List<PublishCoOder>> getAllOrder(String uid);

    CampusOrder getOneOrder(String oid);

    Map<String, List<PublishCoOder>> filterAllOrderByTime(Map<String, List<PublishCoOder>> map);
}
