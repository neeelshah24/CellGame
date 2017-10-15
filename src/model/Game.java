package model;

/**
 * This class represents the logic of a game where a board is updated on each
 * step of the game animation. The board can also be updated by selecting a
 * board cell.
 * 
 * @author Dept of Computer Science, UMCP
 */

public abstract class Game {
	protected BoardCell[][] board;
	private int rows;
	private int cols;
	

	/**
	 * Defines a board with BoardCell.EMPTY cells.
	 * 
	 * @param maxRows
	 * @param maxCols
	 */
	public Game(int maxRows, int maxCols) {
		board = new BoardCell[maxRows][maxCols];
		//the board should be completly empty at first
		//call the enum boardcell type BoardCell.empty
		for (int row = 0; row < maxRows; row++) {
			for (int col = 0; col < maxCols; col++) {
				
				this.setBoardCell(row, col, BoardCell.EMPTY);
			}
		}
		
		this.rows = maxRows;
		this.cols = maxCols;
	}

	public int getMaxRows() {
		return this.rows;
	}

	public int getMaxCols() {
		return this.cols;
	}

	public void setBoardCell(int rowIndex, int colIndex, BoardCell boardCell) {
		this.board[rowIndex][colIndex] = boardCell;
	}

	public BoardCell getBoardCell(int rowIndex, int colIndex) {
		return board[rowIndex][colIndex];
	}

	/**
	 * Initializes row with the specified color.
	 * @param rowIndex
	 * @param cell
	 */
	public void setRowWithColor(int rowIndex, BoardCell cell) {
		//make a loop to add color to all the rows of the board
		//rows going down, set all the col going down with color
		for (int index = 0; index < this.getMaxCols(); index++) {
			setBoardCell(rowIndex, index, cell);
		}
	}
	
	/**
	 * Initializes column with the specified color.
	 * @param colIndex
	 * @param cell
	 */
	public void setColWithColor(int colIndex, BoardCell cell) {
		//same concept with rows, but now with col
		//for loop to iterate through the rows helping to add color to the rows
		for (int index = 0; index < this.getMaxRows(); index++) {
			setBoardCell(index, colIndex, cell);
		}
	
	}
	
	/**
	 * Initializes the board with the specified color.
	 * @param cell
	 */
	public void setBoardWithColor(BoardCell cell) {
		//now iterate throughout the entire board
		//nested for loop to account for every cell in the 2d array
		for(int index = 0; index < this.getMaxCols(); index++){
			this.setColWithColor(index, cell);
		}
	}	
	
	public abstract boolean isGameOver();

	public abstract int getScore();

	/**
	 * Advances the animation one step.
	 */
	public abstract void nextAnimationStep();

	/**
	 * Adjust the board state according to the current board state and the
	 * selected cell.
	 * 
	 * @param rowIndex
	 * @param colIndex
	 */
	public abstract void processCell(int rowIndex, int colIndex);
}