/**
 * <p>
 * 用户信息表 服务实现类
 * </p>
 *
 * @author Colyn
 * @since 2022-09-18
 */

package edu.njust.campus_sec_deal.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.njust.campus_sec_deal.entity.CampusUser;
import edu.njust.campus_sec_deal.mapper.CampusUserMapper;
import edu.njust.campus_sec_deal.service.CampusUserService;
import edu.njust.campus_sec_deal.utils.RandomDataUtil;
import edu.njust.campus_sec_deal.config.UserTypeConf;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;


@Service
public class CampusUserServiceImpl extends ServiceImpl<CampusUserMapper, CampusUser> implements CampusUserService {

    @Autowired
    CampusUserMapper userMapper;

    @Override
    public boolean hasTel(String tel) {  //查询是否存在该号码
        Long cnt = userMapper.selectCount(Wrappers.<CampusUser>lambdaQuery().eq(CampusUser::getUserTel, tel));
        return cnt > 0;
    }

    @Override
    public boolean hasMail(String mail) {  //查询是否存在该邮箱
        Long cnt = userMapper.selectCount(Wrappers.<CampusUser>lambdaQuery().eq(CampusUser::getUserMail, mail));
        return cnt > 0;
    }

    @Override
    public boolean hasUser(String uid) {  //查询是否存在该用户
        Long cnt = userMapper.selectCount(Wrappers.<CampusUser>lambdaQuery().eq(CampusUser::getUserId, uid));
        return cnt > 0;
    }

    @Override
    public boolean isRightUser(CampusUser user) {
        Long cnt = userMapper.selectCount(Wrappers.<CampusUser>lambdaQuery().eq(CampusUser::getUserId, user.getUserId()).eq(CampusUser::getUserTel, user.getUserTel()).eq(CampusUser::getUserMail, user.getUserMail()));
        return cnt > 0;
    }

    @Override
    public boolean insertUser(CampusUser user, String user_sign, String img_url) {
        try {
            user.setUserId(RandomDataUtil.getIDByTime())
                    .setUserTime(RandomDataUtil.getDate())
                    .setUserSign(user_sign)
                    .setImgUrl(img_url);

            userMapper.insert(user);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean retrievePassword(CampusUser user) {
        try {
            userMapper.updateById(user);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public CampusUser loginCheck(Map<String, String> map) {
        if (map.get("userId") != null) {
            try {
                return userMapper.selectById(map.get("userId"));
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        } else if (map.get("userTel") != null) {
            try {
                return userMapper.selectOne(Wrappers.<CampusUser>lambdaQuery().eq(CampusUser::getUserTel, map.get("userTel")));
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        } else if (map.get("userMail") != null) {
            try {
                return userMapper.selectOne(Wrappers.<CampusUser>lambdaQuery().eq(CampusUser::getUserMail, map.get("userMail")));
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        } else {
            return null;
        }
    }

    @Override
    public boolean modifyUserInfo(String uid, String modify, int type) {

        CampusUser user = new CampusUser().setUserId(uid);

        switch (type) {
            case UserTypeConf.USER_TEL:
                user.setUserTel(modify);
                break;
            case UserTypeConf.USER_MAIL:
                user.setUserMail(modify);
                break;
            case UserTypeConf.USER_SIGN:
                user.setUserSign(modify);
                break;
            case UserTypeConf.USER_PWD:
                user.setUserPwd(modify);
                break;
            case UserTypeConf.USER_NAME:
                user.setUserName(modify);
                break;
            default:
                break;
        }

        try {
            userMapper.updateById(user);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean isRightPwd(String uid, String pwd) {
        return pwd.equals(userMapper.selectById(uid).getUserPwd());
    }

    @Override
    public Map<String, String> getUserInfo(String uid) {

        try {
            CampusUser user = userMapper.selectById(uid);
            Map<String, String> map = new HashMap<>();
            map.put("userName", user.getUserName());
            map.put("userSign", user.getUserSign());
            map.put("imgUrl", user.getImgUrl());
            return map;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
