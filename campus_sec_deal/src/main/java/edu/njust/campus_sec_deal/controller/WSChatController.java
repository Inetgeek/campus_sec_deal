/**
 * @Class: WSChatController
 * @Date: 2022/9/20
 * @Project: campus_sec_deal
 * @Author: Colyn
 * @Builder: IntelliJ IDEA
 */

package edu.njust.campus_sec_deal.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import edu.njust.campus_sec_deal.entity.CampusChat;
import edu.njust.campus_sec_deal.mapper.CampusChatMapper;
import edu.njust.campus_sec_deal.mapper.CampusUserMapper;
import edu.njust.campus_sec_deal.utils.RandomDataUtil;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 交易私聊接口
 */
@ServerEndpoint("/api/v1/ws/chat/{user_id}")
@Component
@Data
public class WSChatController {

    private static ApplicationContext applicationContext;

    //初始化
    public static void setApplicationContext(ApplicationContext applicationContext) {
        WSChatController.applicationContext = applicationContext;
    }

    public static CampusChatMapper chatMapper() {
        return applicationContext.getBean(CampusChatMapper.class);
    }

    public static CampusUserMapper userMapper() {
        return applicationContext.getBean(CampusUserMapper.class);
    }

    private static int onlineCount = 0;
    public static Logger log = LoggerFactory.getLogger(WSChatController.class);
    private static ConcurrentHashMap<String, Session> mapUS = new ConcurrentHashMap<>();//根据用户找session
    private static ConcurrentHashMap<Session, String> mapSU = new ConcurrentHashMap<>();//根据session找用户
    public Session session;


    @OnOpen
    public void onOpen(Session session, @PathParam("user_id") String uid) throws IOException {
        this.session = session;
        mapUS.put(uid, this.session);//添加唯一标识的用户ID为key，session为值
        mapSU.put(this.session, uid);//添加session为key，唯一标识的用户ID为值
        addOnlineCount();           //在线数加1

        log.info("有新连接用户:" + uid + "加入！当前在线人数为:" + getOnlineCount());
        List<CampusChat> msgs = chatMapper().selectList(new QueryWrapper<CampusChat>().lambda().eq(CampusChat::getToId, uid));
        if (msgs.size() != 0) {
            chatMapper().deleteBatchIds(msgs);
            log.info("用户" + uid + "上线,推送离线消息");
            for (CampusChat msg : msgs) {
                session.getBasicRemote().sendText("[" + msg.getSendTime().toString().replaceAll("T", " ") + "] " + userMapper().selectById(msg.getFromId()).getUserName() + ": " + msg.getChatContent());
            }
        }
    }

    @OnClose
    public void onClose(Session session) {
        String uid = mapSU.get(session);
        mapUS.remove(uid);
        mapSU.remove(session);
        subOnlineCount();           //在线数减1
        log.info("有一连接关闭！当前在线人数为" + getOnlineCount());
    }

    @OnMessage
    public void onMessage(String message) {
        log.info("收到来自窗口的信息:" + message);
        String[] split = message.split("@");
        String fromId = split[0];
        String toId = split[1];
        String msg = split[2];
        Session to = mapUS.get(toId);

        if (to == null) {//用户不在线
            CampusChat chat = new CampusChat()
                    .setChatId(RandomDataUtil.getIDByTime())
                    .setFromId(String.valueOf(fromId))
                    .setToId(String.valueOf(toId))
                    .setChatContent(msg);
            int insert = chatMapper().insert(chat);
            if (Integer.valueOf(insert).equals(1)) {
                log.info("用户" + toId + "离线,将消息存入数据库");
            }
        }

        if (to == null || msg == null || msg.equals("")) {
            return;
        }
        to.getAsyncRemote().sendText("[" + RandomDataUtil.getSendTime() + "] " + userMapper().selectById(fromId).getUserName() + ": " + msg);

    }

    @OnError
    public void onError(Throwable error) {
        log.error("发生错误");
        error.printStackTrace();
    }

    public static synchronized int getOnlineCount() {
        return onlineCount;//返回在线人数
    }

    public static synchronized void addOnlineCount() {
        WSChatController.onlineCount++;//在线人数+1
    }

    public static synchronized void subOnlineCount() {
        WSChatController.onlineCount--;//在线人数-1
    }
}

