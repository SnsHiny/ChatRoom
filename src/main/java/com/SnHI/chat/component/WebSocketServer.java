package com.SnHI.chat.component;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * webSocket 服务
 */
@Slf4j
@Component
@ServerEndpoint("/chatServer/{mName}")
public class WebSocketServer {

    /**
     * 用来存储每一个客户端对象对应的session对象
     * ConcurrentHashMap：并发编程，以实现线程安全
     */
    public static final Map<String, Session> sessionMap = new ConcurrentHashMap<>();

    /**
     * 连接建立成功调用该方法
     * @param session
     * @param mName
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("mName") String mName) {
        log.info("{}连接建立成功", mName);
        sessionMap.put(mName, session);
        JSONObject result = new JSONObject();
        JSONArray arrays = new JSONArray();
        result.set("members", arrays);
        for (String key: sessionMap.keySet()) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.set("member", key);
            arrays.add(jsonObject);
        }
        result.set("isSystem", true);
        sendAllMessage(JSONUtil.toJsonStr(result));
    }

    /**
     * 连接关闭调用该方法
     * @param session
     * @param mName
     */
    @OnClose
    public void onClose(Session session, @PathParam("mName") String mName) {
        log.info("{}连接关闭", mName);
        sessionMap.remove(mName);
    }

    /**
     * 收到客户端消息后调用的方法
     * @param message
     * @param session
     * @param mName
     */
    @OnMessage
    public void onMessage(String message, Session session, @PathParam("mName") String mName) {
        log.info("收到{}的消息", mName);
        //将传来的消息解析为json格式
        JSONObject jsonMessage = JSONUtil.parseObj(message);
        String toMember = jsonMessage.getStr("toUser");
        String text = jsonMessage.getStr("information");
        Session toSession = sessionMap.get(toMember);
        //组装消息，包括发送人和发送内容
        JSONObject jsonObject = new JSONObject();
        jsonObject.set("from", mName);
        jsonObject.set("text", text);
        if (toSession != null) {
            jsonObject.set("isSystem", false);
            this.sendMessage(jsonObject.toString(), toSession);
        } else if ("共享群聊".equals(toMember)) {
            jsonObject.set("isSystem", true);
            this.sendAllMessage(jsonObject.toString());
        } else {
            log.error("消息发送失败");
        }
    }

    /**
     * 连接发送错误时调用的方法
     * @param session
     * @param throwable
     */
    @OnError
    public void onError(Session session, Throwable throwable) {
        log.error("连接发生错误", throwable);
    }

    /**
     * 服务端发送消息给指定客户端
     * @param message
     * @param toSession
     */
    public void sendMessage(String message, Session toSession) {

        try {
            log.info("消息已发送");
            toSession.getBasicRemote().sendText(message);
        } catch (IOException e) {
            log.error("服务端向单点客户端发送消息失败", e);
        }
    }

    /**
     * 服务端发送消息给所有客户端
     * @param message
     */
    public void sendAllMessage(String message) {
        log.info("消息已群发");
        for (Session session: sessionMap.values()) {
            try {
                session.getBasicRemote().sendText(message);
            } catch (IOException e) {
                log.error("服务器向所有客户端发送消息失败", e);
            }
        }
    }
}
