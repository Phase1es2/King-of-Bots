package com.kob.backend.consumer;

import com.alibaba.fastjson.JSONObject;
import com.kob.backend.consumer.utils.Game;
import com.kob.backend.consumer.utils.JwtAuthentications;
import com.kob.backend.mapper.RecordMapper;
import com.kob.backend.mapper.UserMapper;
import com.kob.backend.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.websocket.*;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;

import java.io.IOException;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

@Component
@ServerEndpoint("/websocket/{token}")
public class WebSocketServer {
    private User user;
    /*
    It keeps track of active WebSocket connections per user in a thread-safe way.
	•	When a user connects, their userId and WebSocketServer instance are added to this map.
	•	When a message needs to be sent to a user, you can look up their WebSocketServer instance using their userId.
        */
    //  static: shared across all instances of that class (one global map for all users).
    final public static ConcurrentHashMap<Integer, WebSocketServer> users = new ConcurrentHashMap<>();
    final private static CopyOnWriteArraySet<User> matchingpool = new CopyOnWriteArraySet<>();

    private Session session = null;

    private static UserMapper userMapper;
    public static RecordMapper recordMapper;
    private Game game = null;

    @Autowired
    public void setUserMapper(UserMapper userMapper) {
        WebSocketServer.userMapper = userMapper;
    }

    @Autowired
    public void setRecordMapper(RecordMapper recordMapper) {
        WebSocketServer.recordMapper = recordMapper;
    }

    @OnOpen
    public void onOpen(Session session, @PathParam("token") String token) throws IOException {
        // build connection
        this.session = session;;
        System.out.println("Connected!");
        // use id is unsafe, use token.
        Integer userId = JwtAuthentications.getUserId(token);
        this.user = userMapper.selectById(userId);
        if (this.user != null) {
            users.put(userId, this);
            // System.out.println(user);
        } else {
            this.session.close();
        }

        System.out.println(users);
    }

    @OnClose
    public void onClose() {
        // close connections
        System.out.println("Disconnected!");
        if (this.user != null) {
            users.remove(this.user.getId());
            matchingpool.remove(this.user);
        }
    }
    private void startMatching(){
        System.out.println("Start Matching");
        matchingpool.add(this.user);
        while (matchingpool.size() >= 2) {
            Iterator<User> it = matchingpool.iterator();
            User a = it.next(), b = it.next();
            matchingpool.remove(a);
            matchingpool.remove(b);

            Game game = new Game(13, 14, 20, a.getId(), b.getId());
            game.generateMap();
            // a new thread of excecution is created
            game.start();
            users.get(a.getId()).game = game;
            users.get(b.getId()).game = game;


            JSONObject respGame = new JSONObject();
            respGame.put("a_id", game.getPlayerA().getId());
            respGame.put("a_sx", game.getPlayerA().getSx());
            respGame.put("a_sy", game.getPlayerA().getSy());
            respGame.put("b_id", game.getPlayerB().getId());
            respGame.put("b_sx", game.getPlayerB().getSx());
            respGame.put("b_sy", game.getPlayerB().getSy());
            respGame.put("map", game.getG());

            //send back to frontend;
            JSONObject respA = new JSONObject();
            respA.put("event", "start-matching");
            respA.put("opponent_username", b.getUsername());
            respA.put("opponent_photo", b.getPhoto());
            // respA.put("gamemap", game.getG());
            respA.put("game", respGame);
            users.get(a.getId()).sendMessage(respA.toJSONString());

            JSONObject respB = new JSONObject();
            respB.put("event", "start-matching");
            respB.put("opponent_username", a.getUsername());
            respB.put("opponent_photo", a.getPhoto());
            // respB.put("gamemap", game.getG());
            respB.put("game", respGame);
            users.get(b.getId()).sendMessage(respB.toJSONString());
        }
    }

    private void stopMatching(){
        System.out.println("Stop Matching");
        matchingpool.remove(this.user);
    }

    @OnMessage // use as router
    public void onMessage(String message, Session session) {
        // get message from clients
        System.out.println("Receive message!");
        JSONObject data = JSONObject.parseObject(message);
        String event = data.getString("event");
        if("start-matching".equals(event)) {
            startMatching();
        } else if ("stop-matching".equals(event)) {
            stopMatching();
        } else if ("move".equals(event)) {
            move(data.getInteger("direction"));
        }
    }

    @OnError
    public void onError(Session session, Throwable error) {
        error.printStackTrace();
    }

    private void move(int direction) {
        if (game.getPlayerA().getId().equals(user.getId())) {
            game.setNextStepA(direction);
        } else if (game.getPlayerB().getId().equals(user.getId())) {
            game.setNextStepB(direction);
        }
    }


    public void sendMessage(String message) {
        // send message to clients
        synchronized (this.session) {
            try {
                this.session.getBasicRemote().sendText(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
