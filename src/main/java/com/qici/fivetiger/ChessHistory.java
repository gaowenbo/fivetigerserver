package com.qici.fivetiger;

public class ChessHistory {
	private Step step;
	private int availableFlag;
	private Boolean dropFinished;
	private int result;
	private int phase;
	private Boolean startMove;
	private String goalInBoard;
	private FlagInfo fensiveInfo;
	private FlagInfo defensiveInfo;
	
	public void setAvailableFlag(int availableFlag) {
		this.availableFlag = availableFlag;
	}

	public void setDropFinished(Boolean dropFinished) {
		this.dropFinished = dropFinished;
	}

	public void setResult(int result) {
		this.result = result;
	}

	public void setPhase(int phase) {
		this.phase = phase;
	}

	public int getAvailableFlag() {
		return availableFlag;
	}

	public Boolean getDropFinished() {
		return dropFinished;
	}

	public int getResult() {
		return result;
	}

	public int getPhase() {
		return phase;
	}

	public Boolean getStartMove() {
		return startMove;
	}

	public void setStartMove(Boolean startMove) {
		this.startMove = startMove;
	}

	public Step getStep() {
		return step;
	}

	public void setStep(Step step) {
		this.step = step;
	}

	public String getGoalInBoard() {
		return goalInBoard;
	}

	public void setGoalInBoard(String goalInBoard) {
		this.goalInBoard = goalInBoard;
	}

	public FlagInfo getFensiveInfo() {
		return fensiveInfo;
	}

	public void setFensiveInfo(FlagInfo fensiveInfo) {
		this.fensiveInfo = fensiveInfo;
	}

	public FlagInfo getDefensiveInfo() {
		return defensiveInfo;
	}

	public void setDefensiveInfo(FlagInfo defensiveInfo) {
		this.defensiveInfo = defensiveInfo;
	}
}
