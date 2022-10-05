/**
 * <p>
 * 订单通知表 Mapper 接口
 * </p>
 *
 * @author Colyn
 * @since 2022-10-02
 */

package edu.njust.campus_sec_deal.mapper;

import edu.njust.campus_sec_deal.entity.CampusNotice;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface CampusNoticeMapper extends BaseMapper<CampusNotice> {

}
