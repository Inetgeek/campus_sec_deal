/**
 * <p>
 * 订单通知表 服务实现类
 * </p>
 *
 * @author Colyn
 * @since 2022-10-02
 */

package edu.njust.campus_sec_deal.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import edu.njust.campus_sec_deal.entity.CampusNotice;
import edu.njust.campus_sec_deal.mapper.CampusNoticeMapper;
import edu.njust.campus_sec_deal.service.CampusNoticeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service
public class CampusNoticeServiceImpl extends ServiceImpl<CampusNoticeMapper, CampusNotice> implements CampusNoticeService {

    @Autowired
    CampusNoticeMapper noticeMapper;

    public void insertOrderNotice(Map<String, String> map) {

        CampusNotice notice = new CampusNotice()
                .setOrderId(map.get("order_id"))
                .setPublisherId(map.get("publisher_id"))
                .setReceiverId(map.get("receiver_id"))
                .setOrderStatus(Integer.parseInt(map.get("order_status")));

        try {
            noticeMapper.insert(notice);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean getOrderNotice(String uid) {
        Long cnt = noticeMapper.selectCount(Wrappers.<CampusNotice>lambdaQuery().eq(CampusNotice::getPublisherId, uid));
        if (cnt > 0) noticeMapper.delete(Wrappers.<CampusNotice>lambdaQuery().eq(CampusNotice::getPublisherId, uid));
        return cnt > 0;
    }
}
