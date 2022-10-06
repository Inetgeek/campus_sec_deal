/**
 * @Class: PublishManageController
 * @Date: 2022/9/21
 * @Project: campus_sec_deal
 * @Author: Colyn
 * @Builder: IntelliJ IDEA
 */

package edu.njust.campus_sec_deal.controller;

import edu.njust.campus_sec_deal.config.ReturnCodeConf;
import edu.njust.campus_sec_deal.entity.CampusPublish;
import edu.njust.campus_sec_deal.entity.PublishCoOder;
import edu.njust.campus_sec_deal.service.CampusOrderService;
import edu.njust.campus_sec_deal.service.CampusPublishService;
import edu.njust.campus_sec_deal.service.CampusSearchService;
import edu.njust.campus_sec_deal.utils.JWTUtil;
import edu.njust.campus_sec_deal.utils.JsonResultUtil;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@PropertySource("classpath:application.properties")
@RequestMapping(value = "/api/v1/search")
public class SearchController {

    @Autowired
    CampusPublishService publishService;

    @Autowired
    CampusSearchService searchService;

    @Autowired
    CampusOrderService orderService;

    public static Logger log = LoggerFactory.getLogger(SearchController.class);

    /**
     * 根据关键词返回物品列表接口
     *
     * @param keywords
     * @return status
     */
    @GetMapping("/get_publish")
    public JsonResultUtil<?> getPublishByKeywords(String keywords) {
        List<CampusPublish> originList = publishService.getAllPublish(null);
        if (originList != null) {
            List<CampusPublish> publishList = searchService.filterPublishList(originList, keywords);
            return new JsonResultUtil<>(publishList);
        } else return new JsonResultUtil<>(ReturnCodeConf.SYS_ERR, "系统出错，查询失败");
    }

    /**
     * 根据关键词返回订单列表接口
     *
     * @param keywords
     * @param request
     * @return status
     */

    @GetMapping("/get_order")
    public JsonResultUtil<?> getOrderByKeywords(String keywords, @NotNull HttpServletRequest request) {
        log.info(keywords);
        String token = request.getHeader("token");
        String uid = JWTUtil.verifyToken(token, "userId");
        if (uid == null || uid.equals("")) {
            return new JsonResultUtil<>(ReturnCodeConf.TOKEN_ERR, "token错误");
        }
        Map<String, List<PublishCoOder>> origin_order_data = orderService.getAllOrder(uid);
        if (origin_order_data != null) {
            List<PublishCoOder> as_publisher = origin_order_data.get("publish");
            List<PublishCoOder> as_receiver = origin_order_data.get("receive");
            List<PublishCoOder> pub = searchService.filterOrderList(as_publisher, keywords);
            List<PublishCoOder> rec = searchService.filterOrderList(as_receiver, keywords);
            Map<String, List<PublishCoOder>> map = new HashMap<>();
            if (pub == null || !pub.isEmpty()) {
                map.put("publish", pub);
            } else map.put("publish", null);

            if (rec == null || !rec.isEmpty()) {
                map.put("receive", rec);
            } else map.put("receive", null);
            return new JsonResultUtil<>(map);
        } else return new JsonResultUtil<>(ReturnCodeConf.INFO_NOT_EXIST, "找不到相关订单");
    }
}
