package com.qici.fivetiger;

public class FlagInfo {
	private int flag;
	private int goal;
	private Boolean isOnTheOffensive;
	public FlagInfo() {
		goal = 0;
		flag = 1;
		isOnTheOffensive = false;
	}
	public FlagInfo(final FlagInfo info) {
		flag = info.getFlag();
		goal = info.getGoal();
		isOnTheOffensive = info.getIsOnTheOffensive();
	}
	public Boolean getIsOnTheOffensive() {
		return isOnTheOffensive;
	}
	public void setIsOnTheOffensive(Boolean inOnTheOffensive) {
		this.isOnTheOffensive = inOnTheOffensive;
	}
	public int getGoal() {
		return goal;
	}
	public void setGoal(int goal) {
		this.goal = goal;
	}
	public int getFlag() {
		return flag;
	}
	public void setFlag(int flag) {
		this.flag = flag;
	};
}
