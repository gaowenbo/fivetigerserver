package com.qici.fivetiger;

import com.alibaba.fastjson.JSONObject;

public class GameLogic {

	private FlagInfo movingFlagInfo;
	private FlagInfo waitingFlagInfo;
	private ChessBoard chessBoard;
	private int phase;
	private Boolean dropFinished;
	private int result;
	private Boolean startMove;
	public GameRecorder recorder;

	public ChessBoard getChessBoard() {
		return chessBoard;
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

	public GameLogic() {
		chessBoard = new ChessBoard();
		movingFlagInfo = new FlagInfo();
		waitingFlagInfo = new FlagInfo();
		recorder = new GameRecorder();
		movingFlagInfo.setFlag(GlobalConst.FENSIVE_FLAG);
		waitingFlagInfo.setFlag(GlobalConst.DEFENSIVE_FLAG);
		phase = GlobalConst.DROP_PHASE;
		result = GlobalConst.RESULT_UNKNOW_WIN;
		dropFinished = false;
		startMove = false;
	}

	public GameLogic(GameLogic gameLogic) {
		phase = gameLogic.phase;
		dropFinished = gameLogic.dropFinished;
		startMove = gameLogic.startMove;
		result = gameLogic.result;
		movingFlagInfo = new FlagInfo(gameLogic.movingFlagInfo);
		waitingFlagInfo = new FlagInfo(gameLogic.waitingFlagInfo);
		chessBoard = new ChessBoard(gameLogic.chessBoard);
		recorder = gameLogic.recorder.clone();
	}
	
	public void reset() {
		chessBoard = new ChessBoard();
		movingFlagInfo = new FlagInfo();
		waitingFlagInfo = new FlagInfo();
		recorder = new GameRecorder();
		movingFlagInfo.setFlag(GlobalConst.FENSIVE_FLAG);
		waitingFlagInfo.setFlag(GlobalConst.DEFENSIVE_FLAG);
		phase = GlobalConst.DROP_PHASE;
		result = GlobalConst.RESULT_UNKNOW_WIN;
		dropFinished = false;
		startMove = false;
	}

	public void go(Step step) {
		if (allowMove(step)) {
			moveOnBoard(step);
			movingFlagInfo.setGoal(movingFlagInfo.getGoal() + calculateGoal(step));
			judgePhase(movingFlagInfo, waitingFlagInfo);
			judgeWin(movingFlagInfo, waitingFlagInfo);
			recordStep(step);
		}
	}
	
	public StepCheckResult checkStep(Step step){
		if (allowMove(step)) {
			return new StepCheckResult(true, "");
		} else {
			return new StepCheckResult(false, "");
		}
	}
	
	public FlagInfo getMovingFlagInfo() {
		return movingFlagInfo;
	}

	public void setMovingFlagInfo(FlagInfo movingFlagInfo) {
		this.movingFlagInfo = movingFlagInfo;
	}

	public FlagInfo getWaitingFlagInfo() {
		return waitingFlagInfo;
	}

	public void setWaitingFlagInfo(FlagInfo waitingFlagInfo) {
		this.waitingFlagInfo = waitingFlagInfo;
	}
	
	public void changeFlag() {
		FlagInfo tmp = movingFlagInfo;
		movingFlagInfo = waitingFlagInfo;
		waitingFlagInfo = tmp;
	}
	
	public void backStep(){
		GameRecorder saveRecorder = recorder;
		reset();
		for(int i = 0; i < saveRecorder.getSteps().size() - 1; i++) {
			go(saveRecorder.getSteps().get(i));
		}
	}

	private void recordStep(Step step) {
		recorder.getSteps().add(step);
	}

	public Boolean allowMove(Step step) {
		if (step.getFlag() != movingFlagInfo.getFlag()) {
			result = GlobalConst.RESULT_FLAG_ERR;
			return false;
		}

		if (GlobalConst.DROP_PHASE == phase) {
			if (step.getAction() != Step.DROP) {
				result = GlobalConst.RESULT_PHRASE_DROP;
				return false;
			}

			if (chessBoard.getPoint(step.getPX(), step.getPY()) != 0) {
				result = GlobalConst.RESULT_PHRASE_DROP_EXIST;
				return false;
			}
		}

		if (GlobalConst.PICK_PHASE == phase) {
			if (Step.PICK != step.getAction()) {
				result = GlobalConst.RESULT_PHRASE_PICK;
				return false;
			}

			// 不是对方的子
			if (chessBoard.getPoint(step.getPX(), step.getPY()) != waitingFlagInfo.getFlag()) {
				result = GlobalConst.RESULT_PHRASE_PICK_NOTOTHER;
				return false;
			}
		}

		if (GlobalConst.MOVE_PHASE == phase) {
			if (Step.PICK != step.getAction()
					&& Step.MOVEDOWN != step.getAction()
					&& Step.MOVELEFT != step.getAction()
					&& Step.MOVERIGHT != step.getAction()
					&& Step.MOVEUP != step.getAction()) {
				result = GlobalConst.RESULT_PHRASE_MOVE;
				return false;
			}

			// 不是自己的子
			if (chessBoard.getPoint(step.getPX(), step.getPY()) != movingFlagInfo.getFlag()) {
				result = GlobalConst.RESULT_PHRASE_MOVE_NOTSELF;
				return false;
			}

			// 不是空地
			if (chessBoard.getPoint(step.getDestX(), step.getDestY()) != 0) {
				result = GlobalConst.RESULT_PHRASE_MOVE_NOTBLANK;
				return false;
			}
		}
		return true;
	}

	public void moveOnBoard(Step step) {

		if (Step.DROP == step.getAction()) {
			chessBoard.setPoint(step.getPX(), step.getPY(), step.getFlag());
		} else if (Step.MOVEUP == step.getAction()) {
			chessBoard.setPoint(step.getPX(), step.getPY(), 0);
			chessBoard.setPoint(step.getPX(), step.getPY() - 1, step.getFlag());
		} else if (Step.MOVEDOWN == step.getAction()) {
			chessBoard.setPoint(step.getPX(), step.getPY(), 0);
			chessBoard.setPoint(step.getPX(), step.getPY() + 1, step.getFlag());
		} else if (Step.MOVELEFT == step.getAction()) {
			chessBoard.setPoint(step.getPX(), step.getPY(), 0);
			chessBoard.setPoint(step.getPX() - 1, step.getPY(), step.getFlag());
		} else if (Step.MOVERIGHT == step.getAction()) {
			chessBoard.setPoint(step.getPX(), step.getPY(), 0);
			chessBoard.setPoint(step.getPX() + 1, step.getPY(), step.getFlag());
		} else if (Step.PICK == step.getAction()) {
			chessBoard.setPoint(step.getPX(), step.getPY(), 0);
		}
	}

	public int calculateGoal(Step step) {
		if (step.getAction() == Step.PICK) {
			return -1;
		} else {
			int goal = 0;
			int x = step.getDestX();
			int y = step.getDestY();
			int flag = step.getFlag();

			// 宝井 1
			if (chessBoard.getPoint(x - 1, y - 1) == flag
					&& chessBoard.getPoint(x - 1, y) == flag
					&& chessBoard.getPoint(x, y - 1) == flag) {
				if (judgeAndRememberGoalStep(flag, 1, x - 1, y - 1)) {
					goal = goal + 1;
				}
			}
			if (chessBoard.getPoint(x - 1, y + 1) == flag
					&& chessBoard.getPoint(x - 1, y) == flag
					&& chessBoard.getPoint(x, y + 1) == flag) {
				if (judgeAndRememberGoalStep(flag, 1, x - 1, y)) {
					goal = goal + 1;
				}
			}
			if (chessBoard.getPoint(x + 1, y - 1) == flag
					&& chessBoard.getPoint(x + 1, y) == flag
					&& chessBoard.getPoint(x, y - 1) == flag) {
				if (judgeAndRememberGoalStep(flag, 1, x, y - 1)) {
					goal = goal + 1;
				}
			}
			if (chessBoard.getPoint(x + 1, y + 1) == flag
					&& chessBoard.getPoint(x + 1, y) == flag
					&& chessBoard.getPoint(x, y + 1) == flag) {
				if (judgeAndRememberGoalStep(flag, 1, x, y)) {
					goal = goal + 1;
				}

			}
			// 三斜 1
			if (chessBoard.getToEdgeLength(x, y, flag, 2) == 3) {
				// 获得第一个点
				int pX = x;
				int pY = y;
				while (pX > 0) {
					pX--;
					pY++;
				}
				if (judgeAndRememberGoalStep(flag, 2, pX, pY)) {
					goal = goal + 1;
				}
			}
			if (chessBoard.getToEdgeLength(x, y, flag, 3) == 3) {
				// 获得第一个点
				int pX = x;
				int pY = y;
				while (pX > 0 && pY > 0) {
					pX--;
					pY--;
				}

				if (judgeAndRememberGoalStep(flag, 3, pX, pY)) {
					goal = goal + 1;
				}
			}
			// 四斜 2
			if (chessBoard.getToEdgeLength(x, y, flag, 2) == 4) {
				// 获得第一个点
				int pX = x;
				int pY = y;
				while (pX > 0) {
					pX--;
					pY++;
				}
				if (judgeAndRememberGoalStep(flag, 4, pX, pY)) {
					goal = goal + 2;
				}
			}
			if (chessBoard.getToEdgeLength(x, y, flag, 3) == 4) {
				// 获得第一个点
				int pX = x;
				int pY = y;
				while (pX > 0 && pY > 0) {
					pX--;
					pY--;
				}
				if (judgeAndRememberGoalStep(flag, 5, pX, pY)) {
					goal = goal + 2;
				}
			}
			// 通天 3
			if (chessBoard.getToEdgeLength(x, y, flag, 2) == 5) {
				// 获得第一个点
				int pX = x;
				int pY = y;
				while (pX > 0) {
					pX--;
					pY++;
				}

				if (judgeAndRememberGoalStep(flag, 6, pX, pY)) {
					goal = goal + 3;
				}
			}
			if (chessBoard.getToEdgeLength(x, y, flag, 3) == 5) {
				// 获得第一个点
				int pX = x;
				int pY = y;
				while (pX > 0 && pY > 0) {
					pX--;
					pY--;
				}
				if (judgeAndRememberGoalStep(flag, 7, pX, pY)) {
					goal = goal + 3;
				}
			}
			// 五虎 5
			if (chessBoard.getToEdgeLength(x, y, flag, 0) == 5) {
				if (judgeAndRememberGoalStep(flag, 8, 0, y)) {
					goal = goal + 5;
				}
			}
			if (chessBoard.getToEdgeLength(x, y, flag, 1) == 5) {
				if (judgeAndRememberGoalStep(flag, 9, x, 0)) {
					goal = goal + 5;
				}
			}
			return goal;
		}
	}

	private boolean judgeAndRememberGoalStep(int flag, int type, int x, int y) {
		String s = String.valueOf(flag) + String.valueOf(type)
				+ String.valueOf(x) + String.valueOf(y);
		if (!recorder.getGoalMap().containsKey(s)) {
			recorder.getGoalMap().put(s, 0);
			return true;
		}

		return false;
	}

	public int run(String runStr) {
		Step step = new Step(runStr);
		go(step);
		return result;
	}

	public int getGoal(int flag) {
		if (movingFlagInfo.getFlag() == flag) {
			return movingFlagInfo.getGoal();
		} else {
			return waitingFlagInfo.getGoal();
		}
	}

	public void judgePhase(FlagInfo flagInfo, FlagInfo otherFlagInfo) {
		if (phase == GlobalConst.DROP_PHASE) {
			// 已走满
			if (chessBoard.isFull()) {
				if (flagInfo.getGoal() == 0) {
					if (otherFlagInfo.getGoal() == 0) {
						// 没人赢
					} else {
						phase = GlobalConst.PICK_PHASE;
						changeFlag();
					}
				} else {
					phase = GlobalConst.PICK_PHASE;
					if (otherFlagInfo.getGoal() != 0) {
						changeFlag();
					}
				}
				dropFinished = true;
			} else {
				changeFlag();
			}
		} else if (phase == GlobalConst.PICK_PHASE) {
			if (flagInfo.getGoal() == 0) {
				if (!chessBoard.canMove(otherFlagInfo.getFlag())) {
					if (otherFlagInfo.getGoal() != 0) {
						changeFlag();
					} else {
						phase = GlobalConst.MOVE_PHASE;
					}
				} else if (flagInfo.getFlag() == GlobalConst.FENSIVE_FLAG) {
					// 先手
					phase = GlobalConst.MOVE_PHASE;
					changeFlag();
				} else {
					// 后手
					if (otherFlagInfo.getGoal() == 0) {
						phase = GlobalConst.MOVE_PHASE;
						if (startMove) {
							changeFlag();
						}
					} else {
						changeFlag();
					}
				}
			}
		} else {
			startMove = true;
			if (flagInfo.getGoal() == 0) {
				if (chessBoard.canMove(otherFlagInfo.getFlag())) {
					changeFlag();
				}
			} else {
				phase = GlobalConst.PICK_PHASE;
			}
		}

	}

	public void judgeWin(FlagInfo flagInfo, FlagInfo otherFlagInfo) {
		result = GlobalConst.RESULT_UNKNOW_WIN;
		// 如果是提子阶段或走子阶段，有一方没有子了
		if (phase == GlobalConst.PICK_PHASE || phase == GlobalConst.MOVE_PHASE) {
			if (!chessBoard.containFlag(otherFlagInfo.getFlag())) {
				// 赢了
				if (flagInfo.getFlag() == GlobalConst.FENSIVE_FLAG) {
					result = GlobalConst.RESULT_FENSIVE_WIN;
				} else {
					result = GlobalConst.RESULT_DEFENSIVE_WIN;
				}
			}
		} else {
			// 走棋阶段满了
			if (chessBoard.isFull()) {
				result = GlobalConst.RESULT_NOBODY_WIN;
			}
		}
	}
	
	public String toString() {
		return chessBoard.toString() + "phase:" + phase + ", dropFinished: " + dropFinished + ", movingFlag:" + movingFlagInfo.getFlag() + ", goal:" + movingFlagInfo.getGoal() + "," + waitingFlagInfo.getGoal() + ", result:" + result;
	}
	
	public JSONObject toJson() {
		JSONObject object = new JSONObject();
		object.put("chessBoard", chessBoard.toString());
		object.put("phase", phase);
		object.put("dropFinished", dropFinished);
		object.put("movingFlag", movingFlagInfo.getFlag());
		if ( movingFlagInfo.getFlag() == GlobalConst.FENSIVE_FLAG) {
			object.put("fensiveGoal", movingFlagInfo.getGoal());
			object.put("defensiveGoal", waitingFlagInfo.getGoal());
		} else {
			object.put("fensiveGoal", waitingFlagInfo.getGoal());
			object.put("defensiveGoal", movingFlagInfo.getGoal());			
		}
//		object.put("goal", phase);
		object.put("result", result);
		return object;
	}

}
