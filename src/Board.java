/**
 * ENSF 607 Lab Assignment 4
 * Board represents a 3x3 board used for a game of tic-tac-toe.
 * @author Brandon Quan
 * 
 */
public class Board implements Constants {
	private char theBoard[][];
	private int markCount;

	/**
	 * Constructor method for Board.
	 * Generates a 3x3 board with empty spaces and no marks.
	 */
	public Board() {
		markCount = 0;
		theBoard = new char[3][];
		for (int i = 0; i < 3; i++) {
			theBoard[i] = new char[3];
			for (int j = 0; j < 3; j++)
				theBoard[i][j] = SPACE_CHAR;
		}
	}

	/**
	 * getMark() returns the mark at the specified row and column.
	 * @param row, the row of the mark
	 * @param col, the column of the mark
	 * @return mark at the specified row and column
	 */
	public char getMark(int row, int col) {
		return theBoard[row][col];
	}

	/**
	 * isFull() checks if 9 marks are on the board.
	 * @return true, if there are 9 marks, or false, if there aren't 9 marks
	 */
	public boolean isFull() {
		return markCount == 9;
	}

	/**
	 * xWins() checks if xPlayer won.
	 * @return true, if the winner is xPlayer, or false if the winner isn't xPlayer. 
	 */
	public boolean xWins() {
		if (checkWinner(LETTER_X) == 1)
			return true;
		else
			return false;
	}

	/**
	 * oWins() checks if oPlayer won.
	 * @return true, if the winner is oPlayer, or false if the winner isn't oPlayer. 
	 */
	public boolean oWins() {
		if (checkWinner(LETTER_O) == 1)
			return true;
		else
			return false;
	}
	
	/**
	 * display() prints the board's current configuration of marks.
	 * @return board, a string representation of the board's current configuration
	 */
	public String display() {
		String board = "";
		board = board + displayColumnHeaders();
		board = board + addHyphens();
		for (int row = 0; row < 3; row++) {
			board = board + addSpaces();
			board = board + "    row " + row + ' ';
			for (int col = 0; col < 3; col++)
				board = board + "|  " + getMark(row, col) + "  ";
			board = board + "|\n";
			board = board + addSpaces();
			board = board + addHyphens();
		}
		return board;
	}

	/**
	 * addMark() adds a mark at the specified row and column of the board.
	 * @param row, the row where the mark is to be inserted
	 * @param col, the column where the mark is to be inserted
	 * @param mark, the mark to be inserted
	 */
	public void addMark(int row, int col, char mark) {
		
		theBoard[row][col] = mark;
		markCount++;
	}

	/**
	 * clear() replaces the marks on the board with an empty space.
	 */
	public void clear() {
		for (int i = 0; i < 3; i++)
			for (int j = 0; j < 3; j++)
				theBoard[i][j] = SPACE_CHAR;
		markCount = 0;
	}

	/**
	 * checkWinner() checks the board to see if the specified mark has a winning combination of 3 marks in a row, column, or diagonal.
	 * @param mark, the mark to be checked for a winning combination
	 * @return 1, if there is a winning combination, or 0 if there is not
	 */
	int checkWinner(char mark) {
		int row, col;
		int result = 0;

		for (row = 0; result == 0 && row < 3; row++) {
			int row_result = 1;
			for (col = 0; row_result == 1 && col < 3; col++)
				if (theBoard[row][col] != mark)
					row_result = 0;
			if (row_result != 0)
				result = 1;
		}

		
		for (col = 0; result == 0 && col < 3; col++) {
			int col_result = 1;
			for (row = 0; col_result != 0 && row < 3; row++)
				if (theBoard[row][col] != mark)
					col_result = 0;
			if (col_result != 0)
				result = 1;
		}

		if (result == 0) {
			int diag1Result = 1;
			for (row = 0; diag1Result != 0 && row < 3; row++)
				if (theBoard[row][row] != mark)
					diag1Result = 0;
			if (diag1Result != 0)
				result = 1;
		}
		if (result == 0) {
			int diag2Result = 1;
			for (row = 0; diag2Result != 0 && row < 3; row++)
				if (theBoard[row][3 - 1 - row] != mark)
					diag2Result = 0;
			if (diag2Result != 0)
				result = 1;
		}
		return result;
	}

	/**
	 * displayColumnHeaders() prints out a header displaying the column names.
	 * @return header displaying the column names
	 */
	String displayColumnHeaders() {
		String temp = "";
		temp = temp + ("          ");
		for (int j = 0; j < 3; j++)
			temp = temp + "|col " + j;
		temp = temp + "\n";
		return temp;
	}

	/**
	 * addHyphens() prints out hyphens to simulate the lines separating the sections of the board.
	 * @return hyphens to simulate the lines separating the sections of the board
	 */
	String addHyphens() {
		String temp = "";
		temp = temp + "          ";
		for (int j = 0; j < 3; j++)
			temp = temp + "+-----";
		temp = temp + "+\n";
		return temp;
	}


	/**
	 * addSpaces() adds whitespace to pad out the sections of the board.
	 * @return whitespace to pad out the sections of the board
	 */
	String addSpaces() {
		String temp = "";
		temp = temp + "          ";
		for (int j = 0; j < 3; j++)
			temp = temp + "|     ";
		temp = temp + "|\n";
		return temp;
	}
	
}
