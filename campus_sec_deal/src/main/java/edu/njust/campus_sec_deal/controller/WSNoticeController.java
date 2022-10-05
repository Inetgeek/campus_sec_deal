/**
 * @Class: WSNoticeController
 * @Date: 2022/9/20
 * @Project: campus_sec_deal
 * @Author: Colyn
 * @Builder: IntelliJ IDEA
 */

package edu.njust.campus_sec_deal.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import edu.njust.campus_sec_deal.entity.CampusNotice;
import edu.njust.campus_sec_deal.mapper.CampusNoticeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * 订单通知接口
 */
@Component
@ServerEndpoint("/api/v1/ws/notice/{user_id}")
public class WSNoticeController {

    private static ApplicationContext applicationContext;

    public static Logger log = LoggerFactory.getLogger(WSNoticeController.class);

    //存放所有的websocket连接
    private static final CopyOnWriteArraySet<WSNoticeController> myWebSocketServicesSet = new CopyOnWriteArraySet<>();

    //初始化
    public static void setApplicationContext(ApplicationContext applicationContext) {
        WSNoticeController.applicationContext = applicationContext;
    }

    public static CampusNoticeMapper noticeMapper() {
        return applicationContext.getBean(CampusNoticeMapper.class);
    }

    //建立websocket连接时自动调用
    @OnOpen
    public void onOpen(Session session, @PathParam("user_id") String uid) throws IOException {
        myWebSocketServicesSet.add(this);
        log.info("有新用户进入，当前连接总数为" + myWebSocketServicesSet.size());
        log.info("该用户id为: " + uid);
        if (noticeMapper().selectCount(new QueryWrapper<CampusNotice>().lambda().eq(CampusNotice::getPublisherId, uid)) > 0) {
            log.info("开始推送订单...");
            noticeMapper().delete(new QueryWrapper<CampusNotice>().lambda().eq(CampusNotice::getPublisherId, uid));
            session.getBasicRemote().sendText("您有新的订单！");
        }
    }

    //关闭websocket连接时自动调用
    @OnClose
    public void onClose() {
        myWebSocketServicesSet.remove(this);
        log.info("连接断开，当前连接总数为" + myWebSocketServicesSet.size());
    }
}