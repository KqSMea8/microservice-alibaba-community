package com.znv.websocket;

import com.znv.bean.Subscriber;
import com.znv.utils.LogUtil;

import javax.websocket.Session;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

public final class PushService {
    // 线程安全Map，存放每个客户端对应的WebSocket对象
    public final static ConcurrentHashMap<String, WebSocket> webSocketMap = new ConcurrentHashMap<String, WebSocket>();
    // 线程安全Map，存放每个客户端订阅的设备
    public final static ConcurrentHashMap<String, Subscriber> subscriberMap = new ConcurrentHashMap<String, Subscriber>();

    private PushService() {

    }

    /**
     * 发送消息
     *
     * @param session
     * @param message
     * @throws IOException
     */
    public static void sendMessage(Session session, String message)
        throws IOException {
        if (session != null) {
            session.getBasicRemote().sendText(message);
        }
    }

    /**
     * 发送消息
     *
     * @param sessionId
     * @param message
     * @throws IOException
     */
    public static void sendMessage(String sessionId, String message)
        throws IOException {
        sendMessage(webSocketMap.get(sessionId).getSession(), message);
    }

    /**
     * 广播消息
     *
     * @param message
     * @throws IOException
     */
    public static void sendMessageToAllClient(String message) {
        for (WebSocket webSocket : webSocketMap.values()) {
            try {
                sendMessage(webSocket.getSession(), message);
            } catch (IOException e) {
                LogUtil.error(e.toString());
            }
        }
    }

    /**
     * 向指定类型客户端发送消息
     *
     * @param message
     * @throws IOException
     */
    public static void sendMessageToAllClient(String message, String param) {
        for (WebSocket webSocket : webSocketMap.values()) {
            try {
                if (webSocket.getParam().equals(param)) {
                    sendMessage(webSocket.getSession(), message);
                }
            } catch (IOException e) {
                LogUtil.error(e.toString());
            }
        }
    }
}
