/**
 * <p>
 * 发布信息表 服务类
 * </p>
 *
 * @author Colyn
 * @since 2022-09-18
 */

package edu.njust.campus_sec_deal.service;

import com.baomidou.mybatisplus.extension.service.IService;
import edu.njust.campus_sec_deal.entity.CampusPublish;

import java.util.List;
import java.util.Map;


public interface CampusPublishService extends IService<CampusPublish> {

    CampusPublish getOnePublish(String pid);

    boolean updatePublish(String pid, boolean status, Map<String, String> map);

    CampusPublish getPublishByOrder(String oid);

    boolean insertPublish(String pid, boolean status, Map<String, String> map);

    List<CampusPublish> getAllPublish(String uid);

    boolean getStatus(String pid);

    boolean deletePublish(String pid);
}
