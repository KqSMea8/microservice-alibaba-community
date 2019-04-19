package com.znv.websocket;

import com.alibaba.fastjson.JSONObject;
import com.znv.utils.LogUtil;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;

// @ServerEndpoint("/websocket/{param}")
@ServerEndpoint(value = "/websocket")
@Component
public class WebSocket {
    // 静态变量，用来记录当前在线连接数
    private static int onlineUsers = 0;
    // 与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;
    // 客户端建立websocket连接，获取url参数
    private String param;

    /**
     * 连接建立成功调用的方法
     */
    // 连接打开时执行
    @OnOpen
    public void onOpen(@PathParam("param") String param, Session session) {
        this.session = session;
        this.param = param;
        if (session.isOpen()) {
            PushService.webSocketMap.put(session.getId(), this);
            addOnlineUsers();

            JSONObject sessionInfo = new JSONObject();
            sessionInfo.put("type", "session");
            sessionInfo.put("sessionid", session.getId());
            try {
                session.getBasicRemote().sendText(sessionInfo.toJSONString());
            } catch (IOException e) {
                LogUtil.error(e.toString());
            }

            // LogUtil.info("get connection:sessionid is:{},param is:{}",
            // session.getId(), param);
            System.out.println();
            LogUtil.info(
                    "get connection...sessionid is:{},current users count is:{}",
                    session.getId(), onlineUsers);
        }
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        PushService.webSocketMap.remove(this.getSession().getId());
        PushService.subscriberMap.remove(this.getSession().getId());
        removeOnlineUsers();
        System.out.println();
        LogUtil.info("close connection:sessionid is:" + this.session.getId());
        System.out.println();
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        LogUtil.info("WebScoket onMessage({})", message);
    }

    /**
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) {
        PushService.webSocketMap.remove(this.getSession().getId());
        PushService.subscriberMap.remove(this.getSession().getId());
        removeOnlineUsers();
        LogUtil.info("error connection:sessionid is:" + session.getId());
    }

    /**
     * 获得session
     *
     * @return
     */
    public Session getSession() {
        return this.session;
    }

    /**
     * 获得url参数
     *
     * @return
     */
    public String getParam() {
        return this.param;
    }

    /**
     * webSocket在线用户
     *
     * @return
     */
    public static synchronized int getOnlineUsers() {
        return onlineUsers;
    }

    /**
     * webSocket在线用户
     */
    private static synchronized void addOnlineUsers() {
        onlineUsers++;
        // LogUtil.info("WebSocket:add Online Users({})", onlineUsers);
    }

    /**
     * webSocket在线用户
     */
    private static synchronized void removeOnlineUsers() {
        onlineUsers--;
        // LogUtil.info("WebSocket:remove Online Users({})", onlineUsers);
    }
}
