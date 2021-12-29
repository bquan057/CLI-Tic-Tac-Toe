import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * ENSF 607 Lab Assignment 4
 * TicTacToeServer acts as a server that sets up a game of tic-tac-toe between two clients on a single thread.
 * Supports multiple threads.
 * @author Brandon Quan
 *
 */
public class TicTacToeServer implements Constants {

	private ServerSocket serverSocket;
	private ExecutorService pool;

	/**
	 * Constructor for the server.
	 * @param portNumber
	 */
	public TicTacToeServer(int portNumber) {
		try {
			serverSocket = new ServerSocket(portNumber);
			pool = Executors.newCachedThreadPool();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Sets up the game, and creates two player objects with sockets to communicate with the clients per thread.
	 * @throws IOException
	 */
	public void communicateWithClient() {
		
		try {
			while (true) {
				Referee theRef = new Referee();
				Game theGame = new Game();
				
				Player xPlayer = new Player(serverSocket.accept(), LETTER_X);
				theRef.setxPlayer(xPlayer);
				xPlayer.setBoard(theGame.getTheBoard());
				xPlayer.getPlayerName();
				
				Player oPlayer = new Player(serverSocket.accept(), LETTER_O);
				
				theRef.setoPlayer(oPlayer);
				oPlayer.setBoard(theGame.getTheBoard());
				oPlayer.getPlayerName();
				
				theGame.appointReferee(theRef);
				
				pool.execute(theGame);
			}
		}catch(Exception e){
			e.printStackTrace();
			pool.shutdown();
		}
		
	}
	
	/**
	 * Creates the server and starts the communication with the clients.
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException{
		TicTacToeServer server = new TicTacToeServer(9898);
		System.out.println("Server is running...");
		server.communicateWithClient();
	}
}
