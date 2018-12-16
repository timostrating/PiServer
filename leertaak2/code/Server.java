import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	public static final int PORT = 7789;
	private static final int maxnrofConnections=800;
	public static TelSemafoor mijnSemafoor = new TelSemafoor(maxnrofConnections);
	
	
	public static void main(String[] args) {
		Socket connection;
		try {
			ServerSocket server = new ServerSocket(PORT);
			System.err.println("Server started with a maximum of: " + maxnrofConnections + " Connections");

			while (true) {
				connection = server.accept();		
				System.err.println("New connection accepted. handing it over to worker thread");
				Thread worker = new Thread(new WorkerThread(connection));
				worker.start();
			}
		}

		catch (java.io.IOException ioe) { System.err.print("\n\nIOException\n\n"); }
	}
}
