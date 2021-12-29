import java.io.IOException;

/**
 * ENSF 607 Lab Assignment 4
 * Referee runs a game of tic-tac-toe.
 * @author Brandon Quan
 */
public class Referee {

	private Player xPlayer;
	private Player oPlayer;
	
	/**
	 * Constructor method for Referee.
	 */
	public Referee() {
		
	}
	
	/**
	 * runTheGame() sets the opponents of the game and starts the game with player X.
	 * @throws IOException
	 */
	public void runTheGame() throws IOException {
		xPlayer.setOpponent(oPlayer);
		oPlayer.setOpponent(xPlayer);
		xPlayer.play();
	}
	
	/**
	 * setoPlayer() sets the player with the O mark in the game.
	 * @param oPlayer, the player with the O mark
	 */
	public void setoPlayer(Player oPlayer) {
		this.oPlayer = oPlayer;
	}
	
	/**
	 * setxPlayer() sets the player with the X mark in the game.
	 * @param xPlayer, the player with the X mark
	 */
	public void setxPlayer(Player xPlayer) {
		this.xPlayer = xPlayer;
	}
}
