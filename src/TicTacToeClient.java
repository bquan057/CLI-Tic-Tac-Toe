import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.io.BufferedReader;

/**
 * ENSF 607 Lab Assignment 4
 * TicTacToeClient acts as the client-side that displays the board and prompts for a game of tic-tac-toe.
 * @author Brandon Quan
 *
 */
public class TicTacToeClient {
	
	private Socket aSocket;
	private PrintWriter socketOut;
	private BufferedReader socketIn;
	private BufferedReader stdIn;
	
	/**
	 * Constructor for the client.
	 * @param serverName
	 * @param portNumber
	 */
	public TicTacToeClient(String serverName, int portNumber) {
		try {
			// Creates a socket.
			aSocket = new Socket(serverName, portNumber);
			
			// Used to read in the keyboard input stream.
			stdIn = new BufferedReader(new InputStreamReader(System.in));
			
			// Used to read the input from socket (BufferedReader), or write the output to the socket (PrintWriter).
			socketIn = new BufferedReader(new InputStreamReader(aSocket.getInputStream()));
			socketOut = new PrintWriter(aSocket.getOutputStream(), true);
		} catch (IOException e) {
			e.getStackTrace();
		}
	}
	
	/**
	 * Reads an input from the socket, and prints it to the client.
	 * Supports reading an input from the client and sending it to the server.
	 */
	public void communicateWithServer() {
		try {
			while(true) {
				// Creates a string to store the lines received by the server.
				String read = "";
				
				while(true) {
					// Reads in a line from the socket.
					read = socketIn.readLine();
					
					// If a terminating character is found, the line without the terminating character is stored in the string and printed.
					if (read.contains("\0")) {
						read = read.replace("\0", "");
						System.out.println(read);
						break;
					}
					
					// Breaks out of the loops if the game is over.
					if (read.equals("QUIT")) {
						return;
					}
					
					System.out.println(read);
				}
				
				// If a terminating character was found, client reads in the next input from the keyboard and sends it to the server.
				read = stdIn.readLine();
				socketOut.println(read);
				socketOut.flush();
			}
			
		}catch (IOException e){
			e.printStackTrace();
		}finally {
			try {
				stdIn.close();
				aSocket.close();
			}catch (IOException e){
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Creates a new client and starts the communication with the server.
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		
		TicTacToeClient myClient = new TicTacToeClient("localhost", 9898);
		myClient.communicateWithServer();
	}
}
