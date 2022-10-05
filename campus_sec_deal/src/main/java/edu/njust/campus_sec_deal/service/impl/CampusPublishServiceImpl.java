/**
 * <p>
 * 发布信息表 服务实现类
 * </p>
 *
 * @author Colyn
 * @since 2022-09-18
 */

package edu.njust.campus_sec_deal.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.njust.campus_sec_deal.entity.CampusPublish;
import edu.njust.campus_sec_deal.mapper.CampusPublishMapper;
import edu.njust.campus_sec_deal.service.CampusPublishService;
import edu.njust.campus_sec_deal.utils.RandomDataUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service
public class CampusPublishServiceImpl extends ServiceImpl<CampusPublishMapper, CampusPublish> implements CampusPublishService {

    @Autowired
    CampusPublishMapper publishMapper;

    @Override
    public CampusPublish getOnePublish(String pid) {
        return publishMapper.selectById(pid);
    }

    @Override
    public boolean updatePublish(String pid, boolean status, Map<String, String> map) {

        if (map == null) {
            CampusPublish publish = new CampusPublish()
                    .setPublishId(pid)
                    .setPublishStatus(status);
            try {
                publishMapper.updateById(publish);
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        } else {
            CampusPublish publish = new CampusPublish()
                    .setPublishId(pid)
                    .setPublisherId(map.get("publisher_id"))
                    .setPublisherTel(map.get("publisher_tel"))
                    .setPublishTime(RandomDataUtil.getDateTime())
                    .setPublishType(Integer.parseInt(map.get("publish_type")))
                    .setPublishStatus(status)
                    .setPublishName(map.get("publish_name"))
                    .setPublishDescribe(map.get("publish_describe"))
                    .setPublishCat(Integer.parseInt(map.get("publish_cat")))
                    .setPublishOprice(Float.parseFloat(map.get("publish_Oprice")))
                    .setPublishNprice(Float.parseFloat(map.get("publish_Nprice")))
                    .setPublishDegree(Integer.parseInt(map.get("publish_degree")))
                    .setImgUrl(map.get("img_url"));
            try {
                publishMapper.updateById(publish);
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
    }

    @Override
    public CampusPublish getPublishByOrder(String oid) {
        return publishMapper.selectPublishByOrder(oid);
    }

    @Override
    public boolean insertPublish(String pid, boolean status, Map<String, String> map) {
        CampusPublish publish = new CampusPublish()
                .setPublishId(pid)
                .setPublisherId(map.get("publisher_id"))
                .setPublisherTel(map.get("publisher_tel"))
                .setPublishTime(RandomDataUtil.getDateTime())
                .setPublishType(Integer.parseInt(map.get("publish_type")))
                .setPublishStatus(status)
                .setPublishName(map.get("publish_name"))
                .setPublishDescribe(map.get("publish_describe"))
                .setPublishCat(Integer.parseInt(map.get("publish_cat")))
                .setPublishOprice(Float.parseFloat(map.get("publish_Oprice")))
                .setPublishNprice(Float.parseFloat(map.get("publish_Nprice")))
                .setPublishDegree(Integer.parseInt(map.get("publish_degree")))
                .setImgUrl(map.get("img_url"));

        try {
            publishMapper.insert(publish);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<CampusPublish> getAllPublish(String uid) {
        if (uid != null) {
            try {
                return publishMapper.selectList(Wrappers.<CampusPublish>lambdaQuery().eq(CampusPublish::getPublisherId, uid));
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        } else {
            try {
                return publishMapper.selectList(Wrappers.<CampusPublish>lambdaQuery().eq(CampusPublish::getPublishStatus, true));
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    @Override
    public boolean getStatus(String pid) {
        return publishMapper.selectById(pid).getPublishStatus();
    }

    @Override
    public boolean deletePublish(String pid) {
        try {
            publishMapper.deleteById(pid);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
