package com.cz.spring.WebSocket;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cz.spring.WebSocket.model.MesInfo;
import com.cz.spring.WebSocket.service.MesInfoService;
import com.cz.spring.system.model.User;
import com.cz.spring.system.service.UserService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@ServerEndpoint("/websocket/{userId}")
@Component
@Slf4j
public class WebSocketController {

    private static int onlineCount = 0;
    public static User user;
    private static ConcurrentHashMap<String, WebSocketController> webSocketSet = new ConcurrentHashMap<String, WebSocketController>();
    private Session session;
    private String userId="";

    public static UserService userService;

    public static MesInfoService mesInfoService;

    @OnOpen
    public void onOpen(Session session, @PathParam("userId") String userId) {
        userId = userId;
        this.session = session;
        webSocketSet.put(userId, this);
        addOnlineCount();       
        System.out.println("New coonection！"+"id"+userId+";online:" + getOnlineCount());
        if (!userId.equals("")) {
            List<MesInfo> list=mesInfoService.list(
                    new QueryWrapper<MesInfo>()
                            .eq("collect_user_id",userId)
                            .eq("is_already_read",0));
            if(list!=null && list.size()>0){
                for(MesInfo mes:list){
                    try {
                        webSocketSet.get(mes.getCollectUserId()).sendMessage(JSONObject.toJSONString(mes));
                        MesInfo m2=new MesInfo();
                        m2.setMesId(mes.getMesId());
                        m2.setIsAlreadyRead(1);
                        mesInfoService.updateById(m2);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

    }

    
    @OnClose
    public void onClose(@PathParam("userId") String userId) {
        if (!userId.equals("")) {
            User u=new User();
            u.setUserId(Integer.parseInt(userId));
            u.setUserOnlineType(0);
            userService.updateById(u);
            webSocketSet.remove(userId);  
            subOnlineCount();           
            System.out.println("online:" + getOnlineCount());
        }
    }



    @OnMessage
    public void onMessage(String requestJson, Session session, @PathParam("userId") String userId) {
    
        JSONObject messageObject = JSONObject.parseObject(requestJson);
        String username=messageObject.getString("nickName"); 
        String collectUserId=messageObject.getString("collectUserId");
        String collectUserName=messageObject.getString("collectUserName");
        String type=messageObject.getString("type");
        String content=messageObject.getString("content");
        String avatar=messageObject.getString("avatar");
        String collectAvatar =messageObject.getString("collectAvatar");
        MesInfo mesInfo=new MesInfo();
        mesInfo.setAvatar(avatar);
        mesInfo.setUsername(username);
        mesInfo.setContent(content);
        mesInfo.setMine(false);
        mesInfo.setType(type);
        mesInfo.setId(userId);
        String jsonStrs=JSONObject.toJSONString(mesInfo);
       
        try {
       
            if (webSocketSet.get(collectUserId) != null) {
                webSocketSet.get(collectUserId).sendMessage(jsonStrs);
            }else{
                MesInfo mesInfo1=new MesInfo();
                mesInfo1.setUsername(collectUserName);
                mesInfo1.setContent("offline");
                mesInfo1.setMine(false);
                mesInfo1.setType(type);
                mesInfo1.setId(collectUserId);
                mesInfo1.setAvatar(collectAvatar);
                String jsonStrss=JSONObject.toJSONString(mesInfo1);
                mesInfo.setCollectUserId(collectUserId);
                mesInfoService.save(mesInfo);
                webSocketSet.get(userId).sendMessage(jsonStrss);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
        //this.session.getAsyncRemote().sendText(message);
    }

    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    public static synchronized void addOnlineCount() {
        WebSocketController.onlineCount++;
    }

    public static synchronized void subOnlineCount() {
        WebSocketController.onlineCount--;
    }

}