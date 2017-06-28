package fiveTigerDemo;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.apache.log4j.Logger;
import org.apache.tomcat.util.codec.binary.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.qici.fivetiger.Game;
import com.qici.fivetiger.GameLogic;
import com.qici.fivetiger.Step;

@ServerEndpoint("/game")
public class MessageEndpoint {

	private static Map<String, Map<String, Session>> roomSessions = new HashMap<String, Map<String, Session>>();
	
	private static Map<String, Game> roomLogic = new HashMap<String, Game>();
	
	private static final Logger logger = Logger.getLogger(MessageEndpoint.class);
	
    @OnOpen
    public void onOpen(Session session) {
    	String roomId = getRoomID(session.getQueryString());
    	if (roomId == null || roomId.isEmpty()) {
    		return;
    	}
    	synchronized (logger) {
    		Map<String, Session> room = roomSessions.get(roomId);
        	if (room == null) {
        		Map<String, Session> newRoom = new HashMap<String, Session>(3);
        		newRoom.put(session.getId(), session);
        		roomSessions.put(roomId, newRoom); 
        		roomLogic.put(roomId, new Game());
        	} else {
        		room.put(session.getId(), session);
        	}
		}
    	
    	logger.info("Open session " + session.getId());
    }

    @OnMessage
    public void onMessage(String message, final Session session) {

    	String roomId = getRoomID(session.getQueryString());
    	if (roomId == null || roomId.isEmpty()) {
    		return;
    	}
    	Game gm = roomLogic.get(roomId);
    	Map<String, Session> sessions =  roomSessions.get(roomId);
    	if (gm == null || sessions == null) {
    		logger.error("房间不存在");
    		return;
    	}
    	
    	String playerID = getPlayerID(session.getQueryString());
 
    	if (message.equals("come")) {
    		gm.addPlayer(playerID);
    		JSONObject result = gm.toJson();
			result.put("flag", gm.getFlag(playerID));
			try {
				session.getBasicRemote().sendText(result.toJSONString());
			} catch (IOException e) {
				logger.error(e.getMessage(), e);
			}
    	} else {
    		gm.go(playerID, new Step(message));
	    	JSONObject result = gm.toJson();
	    	for (Entry<String, Session> entry : sessions.entrySet()) {
	    		try {
					entry.getValue().getBasicRemote().sendText(result.toJSONString());
				} catch (IOException e) {
					logger.error(e.getMessage(), e);
				}
	    	}
    	}
    }

    @OnClose
    public void onClose(Session session) {
    	String roomId = getRoomID(session.getQueryString());
    	if (roomId == null || roomId.isEmpty()) {
    		return;
    	}
    	synchronized (logger) {
    		Map<String, Session> room = roomSessions.get(roomId);
        	if (room != null) {
        		room.remove(session.getId());
        		if (room.isEmpty()) {
        			roomSessions.remove(roomId);
        			roomLogic.remove(roomId);
        		}
        	}
		}
    	
    	logger.info("Session " + session.getId() + " is closed.");
    }
    
    private String getRoomID(String query){
    	if (query == null) {
    		return null;
    	}
    	
    	String[] strs = query.split(";");
    	if (strs.length >= 2) {
    		return strs[1];
    	} 
    	return null;
    }
    
    
    private String getPlayerID(String query) {
    	if (query == null) {
    		return null;
    	}
    	
    	String[] strs = query.split(";");
    	return strs[0];
    }
}