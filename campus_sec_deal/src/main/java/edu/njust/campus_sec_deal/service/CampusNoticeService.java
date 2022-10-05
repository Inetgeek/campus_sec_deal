/**
 * <p>
 * 订单通知表 服务类
 * </p>
 *
 * @author Colyn
 * @since 2022-10-02
 */

package edu.njust.campus_sec_deal.service;

import edu.njust.campus_sec_deal.entity.CampusNotice;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;


public interface CampusNoticeService extends IService<CampusNotice> {

    void insertOrderNotice(Map<String, String> map);

    boolean getOrderNotice(String uid);
}
