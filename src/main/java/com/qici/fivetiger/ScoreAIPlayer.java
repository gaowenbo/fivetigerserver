package com.qici.fivetiger;

import java.util.Random;

public class ScoreAIPlayer extends AIPlayer {
	
	public ScoreAIPlayer(GameLogic logic, int flag) {
		this.logic = logic;
		this.flag = flag;
	}
	
	protected int getDropScore(Step step )
	{
		GameLogic tempLogic = new GameLogic(logic);
		tempLogic.moveOnBoard(step);
		int goal = tempLogic.calculateGoal(step) * 1000;
		if(goal == 0){
			if (step.getDestX() == 0 && step.getDestY() == 2)
			{
				goal += 10;
			}else if(step.getDestX() == 2 && step.getDestY() == 0){
				goal += 10;
			}else if (step.getDestX() == 4 && step.getDestY() == 2)
			{
				goal += 10;
			}else if (step.getDestX() == 2 && step.getDestY() == 4)
			{
				goal += 10;
			}
		}

		Step s = new Step();
		s.setFlag(getOtherFlag());
		s.setAction('s');
		s.setPX(step.getDestX());
		s.setPY(step.getDestY());
		tempLogic.moveOnBoard(s);
		int dropOtherGoal = tempLogic.calculateGoal(s) * 900;
		goal += dropOtherGoal;

		return goal + new Random().nextInt()%10;
	}

	public int getPickScore( Step step )
	{
		return new Random().nextInt()%1000;
	}

	public int getMoveScore( Step step )
	{
		GameLogic tempLogic = new GameLogic(logic);
		tempLogic.moveOnBoard(step);
		int goal = tempLogic.calculateGoal(step) * 1000;
		Step s = new Step();
		s.setFlag(getOtherFlag());
		s.setAction('s');
		s.setPX(step.getDestX());
		s.setPY(step.getDestY());
		tempLogic.moveOnBoard(s);
		int dropOtherGoal = tempLogic.calculateGoal(s) * 900;
		goal += dropOtherGoal;
		s.setPX(step.getPX());
		s.setPY(step.getPY());
		tempLogic.moveOnBoard(s);
		int leaveGoal = tempLogic.calculateGoal(s) *(-800);
		goal += leaveGoal;

		return goal + new Random().nextInt()%10;
	}

	@Override
	public void dropChess() {
		int maxGoal = -10000000;
		Step step = new Step();
		step.setFlag(getFlag());
		step.setAction('s');
		Step tempStep = new Step();
		tempStep.setFlag(getFlag());
		tempStep.setAction('s');
		//遍历所有可行的步数，判断每个步数的分数，取分数最大的那一步
		for(int i=0; i< 5; i++){
			for (int j=0; j< 5; j++)
			{
				if (logic.getChessBoard().getPoint(i, j) != 0)
				{
					continue;
				}
				else{
					tempStep.setPX(i);
					tempStep.setPY(j);
					int goal = getDropScore(tempStep);
					if (goal > maxGoal)
					{
						maxGoal = goal;
						step.setPX(i);
						step.setPY(j);
					}
				}
			}
		}
		if (!logic.checkStep(step).getIsLegal())
		{
			//异常
			return;
		}
		logic.go(step);
//		if (result < 0)
//		{
//		}else if (result == 0)
//		{
//			observer->updateDropChess(getFlag(), step.getPX(), step.getPY());
//			if (logic->getAvailableFlag() != getFlag())
//			{
//				observer->changePlayer(logic->getAvailableFlag());
//			}
//		}
//		else{
//			observer->updateDropChess(getFlag(), step.getPX(), step.getPY());
//			observer->showResult(result, true);
//			isOver = true;
//		}
	}

	@Override
	public void moveChess() {
		int maxGoal = -10000000;
		Step step = new Step();
		step.setFlag(getFlag());
		Step tempStep = new Step();
		tempStep.setFlag(getFlag());
//		QString action;
		//遍历所有可行的步数，判断每个步数的分数，取分数最大的那一步
		for(int i=0; i< 5; i++){
			for (int j=0; j< 5; j++)
			{
				if (logic.getChessBoard().getPoint(i, j) != getFlag())
				{
					continue;
				}
				else{
					//左右上下
					tempStep.setPX(i);
					tempStep.setPY(j);
					int goal = 0;
					if (logic.getChessBoard().getPoint(i-1,j) == 0)
					{
						tempStep.setAction('l');
						goal =  getMoveScore(tempStep);
						if (goal > maxGoal)
						{
							maxGoal = goal;
							step.setPX(tempStep.getPX());
							step.setPY(tempStep.getPY());
							step.setAction(tempStep.getAction());
						}
					}
					if (logic.getChessBoard().getPoint(i+1,j) == 0)
					{
						tempStep.setAction('r');
						goal =  getMoveScore(tempStep);
						if (goal > maxGoal)
						{
							maxGoal = goal;
							step.setPX(tempStep.getPX());
							step.setPY(tempStep.getPY());
							step.setAction(tempStep.getAction());
						}
					}
					if (logic.getChessBoard().getPoint(i,j - 1) == 0)
					{
						tempStep.setAction('u');
						goal =  getMoveScore(tempStep);
						if (goal > maxGoal)
						{
							maxGoal = goal;
							step.setPX(tempStep.getPX());
							step.setPY(tempStep.getPY());
							step.setAction(tempStep.getAction());
						}
					}
					if (logic.getChessBoard().getPoint(i,j + 1) == 0)
					{
						tempStep.setAction('d');
						goal =  getMoveScore(tempStep);
						if (goal > maxGoal)
						{
							maxGoal = goal;
							step.setPX(tempStep.getPX());
							step.setPY(tempStep.getPY());
							step.setAction(tempStep.getAction());
						}
					}
				}
			}
		}
		if (!logic.checkStep(step).getIsLegal())
		{
			return;
		}
		logic.go(step);
//		if (result < 0)
//		{
//		}else if (result == 0)
//		{
//			
//			observer->updateMoveChess(getFlag(), step.getPX(), step.getPY(), step.getDestX(), step.getDestY());
//			if (logic->getAvailableFlag() != getFlag())
//			{
//				observer->changePlayer(logic->getAvailableFlag());
//			}
//		}
//		else{
//			observer->updateMoveChess(getFlag(), step.getPX(), step.getPY(), step.getDestX(), step.getDestY());
//			observer->showResult(result, true);
//			isOver = true;
//		}
	}

	@Override
	public void pickChess() {
		int maxGoal = -10000000;
		Step step = new Step();
		step.setFlag(getFlag());
		step.setAction('p');
		Step tempStep = new Step();
		tempStep.setFlag(getFlag());
		tempStep.setAction('p');
		//遍历所有可行的步数，判断每个步数的分数，取分数最大的那一步
		for(int i=0; i< 5; i++){
			for (int j=0; j< 5; j++)
			{
				if (logic.getChessBoard().getPoint(i, j) == flag || logic.getChessBoard().getPoint(i, j) == 0)
				{
					continue;
				}
				else{
					tempStep.setPX(i);
					tempStep.setPY(j);
					int goal = getPickScore(tempStep);
					if (goal > maxGoal)
					{
						maxGoal = goal;
						step.setPX(i);
						step.setPY(j);
					}
				}
			}
		}

		if (!logic.checkStep(step).getIsLegal())
		{
			return;
		}
		logic.go(step);
//		int result = logic->run(step.toString());
//		if (result < 0)
//		{
//		}else if (result == 0)
//		{
//			observer->updatePickChess(getFlag(), step.getPX(), step.getPY());
//			if (logic->getAvailableFlag() != getFlag())
//			{
//				observer->changePlayer(logic->getAvailableFlag());
//			}
//		}
//		else{
//			observer->updatePickChess(getFlag(), step.getPX(), step.getPY());
//			observer->showResult(result, true);
//			isOver = true;
//		}
	}
}
