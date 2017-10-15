package model;

import java.util.Random;

/* This class must extend Game */
public class ClearCellGame extends Game {
	
	private Random randomIn; 
	private int strategyIn; 
	private int scoreIn;
	private boolean gameOver;

	public ClearCellGame(int maxRows, int maxCols, Random random, int strategy) {
		
		super(maxRows, maxCols);
		
		this.randomIn = random;
		this.strategyIn = strategy;
		this.scoreIn = 0;
		
	}

	@Override
	
	public boolean isGameOver() {
		//iterate through the cols of the board
		for (int index = 0; index < this.getMaxCols(); index++) {
			//check to see if the last row, with the iterating cols is EMPTY
			if (!(this.getBoardCell(this.getMaxRows() - 1, index).equals(BoardCell.EMPTY))) {
				gameOver = true;
				
			} else {
				gameOver = false;
				
			}
		}
		return gameOver;
	}

	@Override
	public int getScore() {
		return this.scoreIn;
	}

	@Override
	public void nextAnimationStep() {
		//if the game isnt over we know that the last row is empty
		if (this.isGameOver() == false) {
			//create a new array that will add ramdom values to the first row of the grid
			BoardCell[][] temp = new BoardCell[this.getMaxRows()][this.getMaxCols()];
			//the first row will be kept empty in order to add random values to it
				for (int row = 1; row < temp.length; row++) {
					for (int col = 0; col < temp[0].length; col++) {
						
						temp[row][col] = board[row - 1][col];
						
						}
					}
				//iterating through the top row adding different values to the columns
				for (int index = 0; index < this.getMaxCols(); index++) {
					temp[0][index] = BoardCell.getNonEmptyRandomBoardCell(randomIn);
					
				}
				//assign the temp array to the regular board array
				board = temp;

		}
	}

	@Override
	//mkae loops looking for similar colors in every direction
	public void processCell(int rowIndex, int colIndex) {
		
		//TOP
		//check to see if the row above the selected tile has the same color as the one being clicked
		//starts at the row one above the cell that is being processed and keeps on incermenting up
		for(int row = rowIndex - 1; row >= 0; row--) {
			if(board[row][colIndex] == board[rowIndex][colIndex]) {
				//if this is true, the cell is set to empty
				this.setBoardCell(row, colIndex, BoardCell.EMPTY);
				
				scoreIn++;
				
			} else {
				
				break;
				
			}
		
		}
		//BOTTOM
		//check to see if the row below the selected cell has the same color as the one being clicked
		for(int row = rowIndex + 1; row < this.getMaxRows(); row++) {
			if(board[row][colIndex] == board[rowIndex][colIndex]) {
				this.setBoardCell(row, colIndex, BoardCell.EMPTY);
		
				scoreIn++;
				
			} else {
				
				break;
				
			}
		}
		//RIGHT
		//same concept as rows but now checking the right row to compare
		for(int col = colIndex + 1; col < this.getMaxCols(); col++) {
			if(board[rowIndex][col] == board[rowIndex][colIndex]) {
				this.setBoardCell(rowIndex, col, BoardCell.EMPTY);
				
				scoreIn++;
				
			} else {
				
				break; 
				
			}
		}
		//LEFT
		//checking the column to the left of the clicked cell
		for(int col = colIndex - 1; col >= 0; col--) {
			if(board[rowIndex][col] == board[rowIndex][colIndex]) {
				this.setBoardCell(rowIndex, col, BoardCell.EMPTY);
		
				scoreIn++;
				
			} else {
				
				break;
				
			}
		}
		
		//DOWN RIGHT
		//this is the same concept but will the row DOWN and to the 
		//RIGHT of the cell that is being clicked on
		
		//changing int to account for the change in row as the column shifts
		int rowBelow = rowIndex + 1; 
		
		//this loop will keep on going over a column
		for(int col = colIndex + 1; col < this.getMaxCols(); col++) {
			//make sure that you dont go outside of the game 2d array
			if(rowBelow < this.getMaxRows()) {
				if(board[rowBelow][col] == board[rowIndex][colIndex]) {
					this.setBoardCell(rowBelow, col, BoardCell.EMPTY);
					scoreIn++;
					//the incermentation of the next row up top
					rowBelow++;
					
				} else {
					
					break;
				}
			}
		}
		
		//DOWN LEFT
		//still looking at the same counter, row below but now to the left
		rowBelow = rowIndex + 1;
		//accounts for the column shift to the left
		for(int col = colIndex - 1; col >= 0; col--) {
			if(rowBelow < this.getMaxRows()) {
				if(board[rowBelow][col] == board[rowIndex][colIndex]) {
					this.setBoardCell(rowBelow, col, BoardCell.EMPTY);
					scoreIn++;
					//shift a row down while the column shifts over one 
					rowBelow++;
					
				} else {
					
					break;
				}
			}
		}
		
		//TOP RIGHT
		//the counter now decrements to account for rows above the cell that is clicked
		int rowAbove = rowIndex - 1;
		for(int col = colIndex + 1; col < this.getMaxCols(); col++) {
			//avoid array out of bounds error
			if(rowAbove >= 0){
				if(board[rowAbove][col] == board[rowIndex][colIndex]) {
					this.setBoardCell(rowAbove, col, BoardCell.EMPTY);
					scoreIn++;
					//shift a row up
					rowAbove--;
					
				} else {
					
					break;
					
				}
			}
		}
		
		//TOP LEFT
		//counter still decrementing to look at the other side of the clicked cell
		rowAbove = rowIndex - 1;
		//move over to the left, move every column over
		for(int col = colIndex - 1; col >= 0; col--) {
			if(rowAbove >= 0) {
				if(board[rowAbove][col] == board[rowIndex][colIndex]) {
					this.setBoardCell(rowAbove, col, BoardCell.EMPTY);
					scoreIn++;
					rowAbove--;
					
				} else {
					
					break;
					
				}
			}
		}
		
		//everyting around the cell clicked processed, make the processed cell 
		//empty
		this.setBoardCell(rowIndex, colIndex, BoardCell.EMPTY);
			scoreIn++;
		
		//iterate through every row to see if the row is empty
		for (int row = 0; row < board.length; row++) {
			if (checkForEmptyRow(row) && row != board.length -1) {
				collapseRow(row);
			}
		}
		
	}
	
	// boolean will return true if there is a completely empty row
	// false if it contains a colored cell
	private boolean checkForEmptyRow(int rowPos) {
		boolean emptyRow = false;

		for (int col = 0; col < board[rowPos].length; col++) {
			if (board[rowPos][col] != BoardCell.EMPTY) {
				emptyRow = false;
				break;
			} else {

				emptyRow = true;
			}
		}
		return emptyRow;
	}

	// if the row is empty the method will go through all the values and shift
	// them up a row
	private void collapseRow(int rowPos) {
		// stopping case
		if (rowPos == board.length - 1) {
			return;

		} else {
			// go through the columns and assign the row above all the values of the row
			// below
			for (int col = 0; col < board[rowPos].length; col++) {
				board[rowPos][col] = board[rowPos + 1][col];
			}
			// call the method itself on the next row
			collapseRow(rowPos + 1);
		}
	}

}