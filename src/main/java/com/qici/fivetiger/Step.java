package com.qici.fivetiger;

public class Step {
	private int flag;
	private char action;
	private int pX;
	private int pY;
	final static char DROP = 's';
	final static char MOVEUP = 'u';
	final static char MOVEDOWN = 'd';
	final static char MOVELEFT = 'l';
	final static char MOVERIGHT = 'r';	
	final static char PICK = 'p';
	
	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

	public char getAction() {
		return action;
	}

	public void setAction(char action) {
		this.action = action;
	}

	public int getPX() {
		return pX;
	}

	public void setPX(int pX) {
		this.pX = pX;
	}

	public int getPY() {
		return pY;
	}

	public void setPY(int pY) {
		this.pY = pY;
	}

	public Step() {
		this("");
	}
	
	public Step(String stepStr) {
		flag = -1;
		action = 0;
		pX = -1;
		pY = -1;

		if (stepStr == null){
			return;
		}
		if (stepStr.length() != 4){
			return;
		}
		flag =  Integer.parseInt(stepStr.substring(0,  1));

		setAction(stepStr.charAt(1));	
		pX = Integer.parseInt(stepStr.substring(2,  3)); 
		pY = Integer.parseInt(stepStr.substring(3,  4)); 
	}

	@Override
	public String toString() {
		StringBuilder s = new StringBuilder();
		s.append(String.valueOf(flag));
		s.append(getAction());
		s.append(String.valueOf(pX));
		s.append(String.valueOf(pY));
		return s.toString();
	}


	public int getDestX()
		{
			if (action == MOVELEFT)
			{
				return pX - 1;
			}
			else if (action == MOVERIGHT)
			{
				return pX + 1;
			}
			else{
				return pX;
			}
		}

	public int getDestY()
		{
			if (action == MOVEDOWN)
			{
				return pY + 1;
			}
			else if (action == MOVEUP)
			{
				return pY - 1;
			}
			else{
				return pY;
			}
		}

	public Boolean isLegal() {
		return flag != -1 && action != 0 && pX != -1 && pY != -1;
	}
}
