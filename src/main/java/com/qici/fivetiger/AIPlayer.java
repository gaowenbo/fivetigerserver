package com.qici.fivetiger;

public abstract class AIPlayer implements IPlayer {
	protected GameLogic logic;
	protected int flag;
	
	public int getOtherFlag() {
		if (flag == GlobalConst.FENSIVE_FLAG) {
			return GlobalConst.DEFENSIVE_FLAG;
		} else {
			return GlobalConst.FENSIVE_FLAG;
		}
	}

	@Override
	public void go() {
		//sleep(11)
		if (logic.getPhase() == GlobalConst.DROP_PHASE)
		{
			dropChess();
		}else if(logic.getPhase() == GlobalConst.MOVE_PHASE){
			moveChess();
		}else{
			pickChess();
		}
		if (logic.getMovingFlagInfo().getFlag() == getFlag() && logic.getResult() <= 0)
		{
			go();
		}
	} 
	
	public int getFlag() {
		return flag;
	}
	
	abstract public void dropChess();
	abstract  public void moveChess();
	abstract public void pickChess();
	
	
}
