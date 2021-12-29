
import java.io.*;

/**
 * ENSF 607 Lab Assignment 4
 * Game acts as a runnable class that runs a game of tic-tac-toe.
 * @author Brandon Quan
 *
 */
public class Game implements Constants, Runnable{

	private Board theBoard;
	private Referee theRef;
	
    /**
     * Constructor for Game.
     * Instantiates a new Board to play on.
     */
    public Game( ) {
        theBoard  = new Board();
	}
    
    public Board getTheBoard() {
		return theBoard;
	}
	
    /**
     * appointReferee() sets a referee for the game.
     * @param r, the referee for the game
     * @throws IOException
     */
    public void appointReferee(Referee r) throws IOException {
        theRef = r;
    }

	@Override
	public void run() {
		try {
			theRef.runTheGame();
		}catch (IOException e) {
			e.printStackTrace();
		}
	}

}
