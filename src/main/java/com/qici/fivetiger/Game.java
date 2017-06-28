package com.qici.fivetiger;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;

public class Game {
	private Map<String, Integer> playerMap = new HashMap<String, Integer>(3);
	private Boolean isStart = false;
	private GameLogic logic = null;
	
	public void addPlayer(String player) {
		if (playerMap.size() == 2) {
			return;
		}
		if (logic == null) {
			logic = new GameLogic();
		}
		if (!playerMap.containsKey(player)) {
			playerMap.put(player, playerMap.size() + 1);
		}
		//两个人自动开始
		if (playerMap.size() == 2) {
			isStart = true;
		}
	}
	
	public Boolean isStart() {
		return isStart;
	}

	public void go(String playerid, Step step) {
		Integer flag = playerMap.get(playerid);
		if (flag.equals(step.getFlag()) && logic != null) {
			logic.go(step);
		}
	}

	public JSONObject toJson() {
		if (logic != null) {
			return logic.toJson();
		}
		return new JSONObject();
	}
	
	public Integer getFlag(String playerID) {
		return playerMap.get(playerID);
	}
}
