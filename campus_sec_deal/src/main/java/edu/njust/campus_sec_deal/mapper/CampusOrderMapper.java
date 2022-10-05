/**
 * <p>
 * 订单信息表 Mapper 接口
 * </p>
 *
 * @author Colyn
 * @since 2022-09-18
 */

package edu.njust.campus_sec_deal.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import edu.njust.campus_sec_deal.entity.CampusOrder;
import edu.njust.campus_sec_deal.entity.PublishCoOder;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
@Mapper
public interface CampusOrderMapper extends BaseMapper<CampusOrder> {

    @Select("select p.*, o.* from campus_publish p, campus_order o where p.publish_id = o.goods_id and p.publish_id in (select publish_id from campus_publish where publisher_id=#{id})")
    List<PublishCoOder> orderListALLByPublisher(String id);

    @Select("select p.*, o.* from campus_publish p, campus_order o where p.publish_id = o.goods_id and o.goods_id in (select goods_id from campus_order where o.receiver_id=#{id})")
    List<PublishCoOder> orderListALLByReceiver(String id);
}
