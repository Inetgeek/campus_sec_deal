/**
 * <p>
 * 用户信息表 Mapper 接口
 * </p>
 *
 * @author Colyn
 * @since 2022-09-18
 */

package edu.njust.campus_sec_deal.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import edu.njust.campus_sec_deal.entity.CampusUser;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface CampusUserMapper extends BaseMapper<CampusUser> {

}
