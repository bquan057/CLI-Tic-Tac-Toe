import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

/**
 * ENSF 607 Lab Assignment 4
 * Player represents a player within a game of tic-tac-toe.
 * @author Brandon Quan
 *
 */
public class Player implements Constants{

	private String name;
	private Board board;
	private Player opponent;
	private char mark;
	private Socket aSocket;
	private PrintWriter socketOut;
	private BufferedReader socketIn;
	
	/**
	 * Constructor method for Player.
	 * Sets the socket and a mark, either X or O.
	 * @param socket, the socket that will be used by the player
	 * @param mark, the mark for the player, either X or O
	 */
	public Player(Socket socket, char mark) {
		this.aSocket = socket;
		this.mark = mark;
		try {
			// Used to read the input from socket (BufferedReader), or write the output to the socket (PrintWriter).
			socketIn = new BufferedReader(new InputStreamReader(aSocket.getInputStream()));
			socketOut = new PrintWriter(aSocket.getOutputStream(), true);
		} catch (IOException e) {
			e.getStackTrace();
		}
	}
	
	
	/**
	 * Prints the string to the socket to be read by the client.
	 * @param toClient, a string message to be sent to the client
	 */
	private void sendString(String toClient) {
		socketOut.println(toClient);
		socketOut.flush();
	}
	
	/**
	 * Prompts the player for their name.
	 */
	public void getPlayerName() {
		try {
			sendString("You are player '" + mark + "'. Please enter your name: \0");
			name = socketIn.readLine();
			while (name == null) {
				sendString("Please try again: \0");
				name = socketIn.readLine();
			}
			sendString(name);
			if (mark == LETTER_X) {
				sendString("Waiting for your opponent to connect.");
			}
			else if (mark == LETTER_O) {
				sendString("Please wait for your opponent to make the first move.");
			}
			
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * play() checks if a win condition or draw condition is met, and either ends or continues the game.
	 * @throws IOException
	 */
	public void play() throws IOException {
		
		// Checks if a win condition or draw condition has been met.
		if (board.xWins() == false
				&& board.oWins() == false 
				&& board.isFull() == false) {
			// If all checks are false, the game continues with the next move.
			sendString(board.display());
			makeMove();
			sendString(board.display());
			if (board.xWins() || board.oWins()) {
				sendString("Game Over - The winner is " + this.name + ".");
				sendString("QUIT");
			}
			else if (board.isFull()) {
				sendString("Game Over - The game ended in a tie.");
				sendString("QUIT");
			}
			else {
				sendString("Please wait for your opponent to make a move.");
			}
			opponent.play();
		}
		// If X wins.
		else if (board.xWins()) {
			if (this.mark == 'X') {
				sendString(board.display());
				sendString("Game Over - The winner is " + this.name + ".");
				sendString("QUIT");
			}
			else {
				sendString(board.display());
				sendString("Game Over - The winner is " + this.opponent.name + ".");
				sendString("QUIT");
			}
			return;
		}
		// If O wins.
		else if (board.oWins()) {
			if (this.mark == 'O') {
				sendString(board.display());
				sendString("Game Over - The winner is " + this.name + ".");
				sendString("QUIT");
			}
			else {
				sendString(board.display());
				sendString("Game Over - The winner is " + this.opponent.name + ".");
				sendString("QUIT");
			}
			return;
		}
		// If all spaces are taken, the game ends in a draw.
		else if (board.isFull()) {
			sendString(board.display());
			sendString("Game Over - The game ended in a tie.");
			sendString("QUIT");
			return;
		}
	}
	
	/**
	 * makeMove() prompts the player for a row and column number.
	 * @throws IOException
	 */
	public void makeMove() throws IOException {
		try {
			sendString("Please make a move by entering a row and column number.");
			Scanner moveScanner = new Scanner(socketIn);
			
			// Takes in a row number as input.
			sendString("\n" + name + ", which row should your next mark be placed? \0");
			int rowNumber = -1;
			rowNumber = moveScanner.nextInt();
			// Checks if row number is within valid values.
			while (rowNumber < 0 || rowNumber > 2) {
				sendString("Please enter a valid integer: \0");
				rowNumber = moveScanner.nextInt();
			}
			
			// Takes in column number as input.
			sendString("\n" + name + ", which column should your next mark be placed? \0");
			int columnNumber = -1;
			columnNumber = moveScanner.nextInt();
			// Checks if column number is within valid values.
			while (columnNumber < 0 || columnNumber > 2) {
				sendString("Please enter a valid integer: \0");
				columnNumber = moveScanner.nextInt();
			}
			
			// Adds mark if the space is empty.
			if (board.getMark(rowNumber, columnNumber) == ' ') {
				board.addMark(rowNumber, columnNumber, this.mark);
			}
			else {
				sendString("This space has already been marked. Try another space.");
				this.play();
			}
		}
		catch (Exception e){
			sendString("Please enter a valid integer.");
			this.play();
		}
	}
	
	/**
	 * setOpponent() sets the player's opponent.
	 * @param opponent, the player's opponent
	 */
	public void setOpponent(Player opponent) {
		this.opponent = opponent;
	}
	
	/**
	 * setBoard() sets the board for the game.
	 * @param theBoard, the board for the game
	 */
	public void setBoard(Board theBoard) {
		this.board = theBoard;
		sendString(board.display());
		sendString("Welcome to the game!"); 
	}
	
	/**
	 * getBoard() returns the board for the game.
	 * @return board, the board for the game.
	 */
	public Board getBoard() {
		return board;
	}
}
