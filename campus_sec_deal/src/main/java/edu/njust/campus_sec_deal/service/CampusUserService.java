/**
 * <p>
 * 用户信息表 服务类
 * </p>
 *
 * @author Colyn
 * @since 2022-09-18
 */

package edu.njust.campus_sec_deal.service;

import com.baomidou.mybatisplus.extension.service.IService;
import edu.njust.campus_sec_deal.entity.CampusUser;

import java.util.Map;


public interface CampusUserService extends IService<CampusUser> {

    boolean hasTel(String tel);

    boolean hasMail(String mail);

    boolean hasUser(String uid);

    boolean isRightUser(CampusUser user);

    boolean isRightPwd(String uid, String pwd);

    boolean insertUser(CampusUser user, String user_sign, String img_url);

    boolean retrievePassword(CampusUser user);

    CampusUser loginCheck(Map<String, String> map);

    Map<String, String> getUserInfo(String uid);

    boolean modifyUserInfo(String uid, String modify, int type);
}
