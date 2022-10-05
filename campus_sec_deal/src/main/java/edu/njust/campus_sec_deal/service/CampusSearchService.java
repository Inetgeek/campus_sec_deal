/**
 * <p>
 * 发布信息表 服务类
 * </p>
 *
 * @author Colyn
 * @since 2022-09-18
 */

package edu.njust.campus_sec_deal.service;

import edu.njust.campus_sec_deal.entity.CampusPublish;
import edu.njust.campus_sec_deal.entity.PublishCoOder;

import java.util.List;

public interface CampusSearchService {
    List<CampusPublish> filterPublishList(List<CampusPublish> list, String keys);
    List<PublishCoOder> filterOrderList(List<PublishCoOder> list, String keys);
}
