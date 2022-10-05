/**
 * <p>
 * 交易私聊表 服务实现类
 * </p>
 *
 * @author Colyn
 * @since 2022-09-18
 */

package edu.njust.campus_sec_deal.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.njust.campus_sec_deal.entity.CampusChat;
import edu.njust.campus_sec_deal.mapper.CampusChatMapper;
import edu.njust.campus_sec_deal.service.CampusChatService;
import org.springframework.stereotype.Service;


@Service
public class CampusChatServiceImpl extends ServiceImpl<CampusChatMapper, CampusChat> implements CampusChatService {

}
