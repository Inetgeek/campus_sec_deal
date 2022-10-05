/**
 * <p>
 * 发布信息表 Mapper 接口
 * </p>
 *
 * @author Colyn
 * @since 2022-09-18
 */

package edu.njust.campus_sec_deal.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import edu.njust.campus_sec_deal.entity.CampusPublish;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface CampusPublishMapper extends BaseMapper<CampusPublish> {

    @Select("select * from campus_publish where publish_id in (select goods_id from campus_order where order_id = #{oid})")
    CampusPublish selectPublishByOrder(String oid);

}
