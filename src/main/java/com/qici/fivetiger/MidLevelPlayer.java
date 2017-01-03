package com.qici.fivetiger;

import java.util.Random;

public class MidLevelPlayer extends ScoreAIPlayer {

	public static final int offsetX[] = {0,0,-1,1};
	public static final int offsetY[] = {-1,1,0,0};	
	public static final char MOVE_ACTION[] = {'u','d','l','r'};
	public static final int POSITION_SCORE[][] = {{0, 1, 5, 1, 0},
														{1, 6, 4, 6, 1},
														{4, 4, 5, 3, 4},
														{1, 6, 4, 6, 1},
														{0, 1, 5, 1, 0}};
	
	public MidLevelPlayer(GameLogic logic, int flag) {
		super(logic, flag);
	}

	public int getDropScore(Step step )
	{
		GameLogic game = new GameLogic(logic);
		
		if (game.getChessBoard().getChessCount() < 6)
		{
			return getDropMaxScore(game, step, 1, getFlag()) 
				+  POSITION_SCORE[step.getDestX()][step.getDestY()] * 10 + new Random().nextInt()%10;
		}
		return getDropMaxScore(game, step, 3, getFlag()) 
			+  POSITION_SCORE[step.getDestX()][step.getDestY()] * 10 + new Random().nextInt()%10;
	}

	public int getMoveScore( Step  step )
	{
		GameLogic game = new GameLogic(logic);
		if ( game.getChessBoard().getChessCount() < 16 && game.getChessBoard().getChessCount() > 10)
		{
			return getMoveMaxScore(game, step, 4, getFlag()) 
				+ POSITION_SCORE[step.getDestX()][step.getDestY()] * 10 + new Random().nextInt()%10;
		}
		return getMoveMaxScore(game, step, 5, getFlag()) 
			+ POSITION_SCORE[step.getDestX()][step.getDestY()] * 10 + new Random().nextInt()%10;
	}

	private int getDropMaxScore(GameLogic logic, Step step, int deepth , int flag)
	{
		GameLogic game = new GameLogic(logic);
		game.moveOnBoard(step);
		int score = game.calculateGoal(step) * 1000 * (flag==getFlag()?1:-1) * (deepth + 10) / 10;
		if (deepth == 0)
		{
			return score;
		}
		int thisFlag = flag==getFlag()?getOtherFlag():getFlag();
		Step tempStep = new Step();
		tempStep.setFlag(thisFlag);
		tempStep.setAction('s');
		int maxScore = 0;
		Boolean canDrop = false;
		for (int i = 0; i< 5; i++)
		{
			for (int j = 0; j < 5; j ++)
			{
				if (game.getChessBoard().getPoint(i, j) != 0)
				{
					continue;
				}
				else{
					tempStep.setPX(i);
					tempStep.setPY(j);
					int tempScore = getDropMaxScore(game, tempStep, deepth - 1, thisFlag);
					if (!canDrop)
					{
						maxScore = tempScore;
					}
					if (thisFlag == getFlag())
					{
						if (tempScore > maxScore)
						{
							maxScore = tempScore;
						}
					}else{
						if (tempScore < maxScore)
						{
							maxScore = tempScore;
						}
					}
					
					canDrop = true;
				}
			}
		}

		return score + (canDrop?maxScore:0);
	}

	private int getMoveMaxScore( GameLogic logic, Step step, int deepth, int flag )
	{
		GameLogic game = new GameLogic(logic);
		game.moveOnBoard(step);
		int score = game.calculateGoal(step) * 1000 * (flag==getFlag()?1:-1) * (deepth * 4 + 1) / 10;
		if (deepth == 0)
		{
			return score;
		}
		int thisFlag = flag==getFlag()?getOtherFlag():getFlag();
		Step tempStep = new Step();
		tempStep.setFlag(thisFlag);
		int maxScore = 0;
		Boolean canMove = false;
		for (int i = 0; i< 5; i++)
		{
			for (int j = 0; j < 5; j ++)
			{
				if (game.getChessBoard().getPoint(i, j) != thisFlag)
				{
					continue;
				}
				else{
					tempStep.setPX(i);
					tempStep.setPY(j);
					for (int m = 0; m < 4; m++)
					{
						if (game.getChessBoard().getPoint(i + offsetX[m],j + offsetY[m]) != 0)
						{
							continue;
						}
						tempStep.setAction(MOVE_ACTION[m]);
						int tempScore = getMoveMaxScore(game, tempStep, deepth - 1, thisFlag);
						if (!canMove)
						{
							maxScore = tempScore;
						}
						if (thisFlag == getFlag())
						{
							if (tempScore > maxScore)
							{
								maxScore = tempScore;
							}
						}else{
							if (tempScore < maxScore)
							{
								maxScore = tempScore;
							}
						}

						canMove = true;
					}
				}
			}
		}

		return score + (canMove?maxScore:0);
	}

	public int getPickScore( Step step )
	{
		return POSITION_SCORE[step.getDestX()][step.getDestY()] * 10 + new Random().nextInt()%10;
	}

}
