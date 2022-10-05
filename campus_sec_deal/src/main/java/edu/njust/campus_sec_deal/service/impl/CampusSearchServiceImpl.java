/**
 * <p>
 * 搜索
 * </p>
 *
 * @author Colyn
 * @since 2022-09-18
 */

package edu.njust.campus_sec_deal.service.impl;

import edu.njust.campus_sec_deal.entity.CampusPublish;
import edu.njust.campus_sec_deal.entity.PublishCoOder;
import edu.njust.campus_sec_deal.service.CampusSearchService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CampusSearchServiceImpl implements CampusSearchService {

    boolean isKeyInPublish(CampusPublish pub, String key) {
        // 在所有字符串字段中检索关键字
        return pub.getPublishDescribe().contains(key)
                || pub.getPublishName().contains(key)
                || pub.getPublisherTel().contains(key)
                || pub.getPublisherId().contains(key)
                || pub.getPublishId().contains(key)
                || pub.getPublishTime().toString().contains(key);
    }

    boolean isKeyInOrder(PublishCoOder order, String key) {
        return order.getOrderId().contains(key)
                || order.getGoodsId().contains(key)
                || order.getOrderTime().toString().contains(key)
                || order.getPublishDescribe().contains(key)
                || order.getDealAddr().contains(key)
                || order.getPublisherId().contains(key)
                || order.getPublisherTel().contains(key)
                || order.getPublishName().contains(key);
    }

    @Override
    public List<CampusPublish> filterPublishList(List<CampusPublish> list, String keys) {
        if(keys == null || "".equals(keys)) {
            return list;
        }
        // 根据关键词剔除不满足条件的项目
        list.removeIf(pub -> !isKeyInPublish(pub, keys));
        return list;
    }

    @Override
    public List<PublishCoOder> filterOrderList(List<PublishCoOder> list, String keys) {
        if(list == null) {
            return list;
        }
        if(keys == null || "".equals(keys)) {
            return list;
        }
        list.removeIf(ord -> !isKeyInOrder(ord, keys));
        return list;
    }
}
