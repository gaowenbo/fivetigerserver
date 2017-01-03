package com.qici.fivetiger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameRecorder extends Object {
	public List<ChessHistory> states = new ArrayList<ChessHistory>();
	public Map<String, Integer> goalMap = new HashMap<String, Integer>();
	public List<Step> steps = new ArrayList<Step>();
	
	public List<Step> getSteps() {
		return steps;
	}

	public List<ChessHistory> getStates() {
		return states;
	}

	public void setStates(List<ChessHistory> states) {
		this.states = states;
	}

	public Map<String, Integer> getGoalMap() {
		return goalMap;
	}

	public void setGoalMap(Map<String, Integer> goalMap) {
		this.goalMap = goalMap;
	}

    public GameRecorder clone() {
    	GameRecorder recorder = new GameRecorder();
//    	for (ChessHistory history: recorder.getStates()) {
//    		//TODO
////    		recorder.getStates().add(history.clone());
//    	}
    	recorder.getSteps().addAll(steps);
//    	recorder.getStates().addAll(states);
    	recorder.getGoalMap().putAll(goalMap);
        return recorder;
    }

	public void addHistory() {
		// TODO Auto-generated method stub
		
	}
}
