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
import edu.njust.campus_sec_deal.service.CampusOrderService;
import edu.njust.campus_sec_deal.service.CampusPublishService;
import edu.njust.campus_sec_deal.utils.JWTUtil;
import edu.njust.campus_sec_deal.utils.JsonResultUtil;
import edu.njust.campus_sec_deal.utils.RandomDataUtil;
import org.hibernate.validator.constraints.Length;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;


@RestController
@Validated
@PropertySource("classpath:application.properties")
@RequestMapping(value = "/api/v1/publish")
public class PublishManageController {

    @Autowired
    CampusPublishService publishService;

    @Autowired
    CampusOrderService orderService;

    /**
     * 发布信息接口
     *
     * @param publish
     * @param request
     * @return status
     */
    @PostMapping("/post_info")
    public JsonResultUtil<?> postPublish(@RequestBody @Validated(CampusPublish.PublishPost.class) CampusPublish publish, @NotNull HttpServletRequest request) {
        String token = request.getHeader("token");
        //从token中取出用户ID
        String uid = JWTUtil.verifyToken(token, "userId");
        if (uid == null || uid.equals("")) {
            return new JsonResultUtil<>(ReturnCodeConf.TOKEN_ERR, "token错误，请登录");
        } else {

            String publish_id = RandomDataUtil.getIDByTime();

            if (publishService.insertPublish(publish_id, true, publish, uid)) {
                HashMap<String, String> data = new HashMap<>();
                data.put("publishId", publish_id);
                return new JsonResultUtil<>(data);
            } else return new JsonResultUtil<>(ReturnCodeConf.SYS_ERR, "系统错误，发布失败");
        }
    }


    /**
     * 获取发布信息接口
     *
     * @return Map
     */
    @GetMapping("/get_info")
    public JsonResultUtil<?> getPublish(HttpServletRequest request) {

        String token = request.getHeader("token");
        //从token中取出用户ID
        String uid = JWTUtil.verifyToken(token, "userId");
        List<CampusPublish> publishList = publishService.getAllPublish(uid);
        if (publishList != null) {
            return new JsonResultUtil<>(publishList);
        } else return new JsonResultUtil<>(ReturnCodeConf.SYS_ERR, "系统出错，查询失败");
    }

    /**
     * 修改发布信息接口
     *
     * @param publish
     * @param request
     * @return status
     */
    @PutMapping("/modify_info")
    public JsonResultUtil<?> modifyInfo(@RequestBody @Validated(CampusPublish.PublishModify.class) CampusPublish publish, @NotNull HttpServletRequest request) {

        String token = request.getHeader("token");
        //从token中取出用户ID
        String uid = JWTUtil.verifyToken(token, "userId");
        if (uid == null || uid.equals("")) {
            return new JsonResultUtil<>(ReturnCodeConf.TOKEN_ERR, "token错误，请登录");
        } else {

            if (!publishService.getStatus(publish.getPublishId())) {
                return new JsonResultUtil<>(ReturnCodeConf.INFO_EXIST, "已生成订单，无权修改");
            } else {
                if (publishService.updatePublish(publish.getPublishId(), true, publish, uid)) {
                    return new JsonResultUtil<>();
                } else return new JsonResultUtil<>(ReturnCodeConf.SYS_ERR, "系统出错，修改失败");
            }
        }
    }


    /**
     * 删除发布信息接口
     *
     * @param pid
     * @param request
     * @return status
     */
    @DeleteMapping("/delete_info")
    public JsonResultUtil<?> deleteInfo(@Length(max = 14, min = 14, message = "物品ID不合法") String pid, @NotNull HttpServletRequest request) {

        String token = request.getHeader("token");
        //从token中取出用户ID
        String uid = JWTUtil.verifyToken(token, "userId");
        if (uid == null || uid.equals("")) {
            return new JsonResultUtil<>(ReturnCodeConf.TOKEN_ERR, "token错误，请登录");
        } else {

            //如果存在订单，则不允许删除
            if (orderService.hasOrder(pid)) {
                return new JsonResultUtil<>(ReturnCodeConf.INFO_ILLEGAL, "已生成订单，无权删除");
            } else {
                if (publishService.deletePublish(pid)) {
                    return new JsonResultUtil<>();
                } else return new JsonResultUtil<>(ReturnCodeConf.SYS_ERR, "系统出错，删除失败");
            }
        }
    }
}
