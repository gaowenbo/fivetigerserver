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

import com.qici.fivetiger.GameLogic;
import com.qici.fivetiger.Step;

@ServerEndpoint("/message-endpoint")
public class MessageEndpoint {

	private static Map<String, Map<String, Session>> roomSessions = new HashMap<String, Map<String, Session>>();
	
	private static Map<String, GameLogic> roomLogic = new HashMap<String, GameLogic>();
	
	private static final Logger logger = Logger.getLogger(MessageEndpoint.class);
	
    @OnOpen
    public void onOpen(Session session) {
    	String roomId = session.getQueryString();
    	if (roomId == null || roomId.isEmpty()) {
    		return;
    	}
    	synchronized (logger) {
    		Map<String, Session> room = roomSessions.get(roomId);
        	if (room == null) {
        		Map<String, Session> newRoom = new HashMap<String, Session>(3);
        		newRoom.put(session.getId(), session);
        		roomSessions.put(roomId, newRoom); 
        		roomLogic.put(roomId, new GameLogic());
        	} else {
        		room.put(session.getId(), session);
        	}
		}
    	
    	logger.info("Open session " + session.getId());
    }

    @OnMessage
    public void onMessage(String message, final Session session) {

    	String roomId = session.getQueryString();
    	if (roomId == null || roomId.isEmpty()) {
    		return;
    	}
    	GameLogic gm = roomLogic.get(roomId);
    	Map<String, Session> sessions =  roomSessions.get(roomId);
    	if (gm == null || sessions == null) {
    		logger.error("房间不存在");
    		return;
    	}
    	
    	if (!message.equals("come")) {
        	gm.go(new Step(message));
    	}
    	
    	String result = gm.toJson();
    	

    	for (Entry<String, Session> entry : sessions.entrySet()) {
    		try {
				entry.getValue().getBasicRemote().sendText(result);
			} catch (IOException e) {
				logger.error(e.getMessage(), e);
			}
    	}
    }

    @OnClose
    public void onClose(Session session) {
    	String roomId = session.getQueryString();
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
}