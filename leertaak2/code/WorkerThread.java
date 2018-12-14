import java.net.*;
import java.io.*;
//import java.util.concurrent.*;

class WorkerThread implements Runnable  {
	private Socket connection;

	public WorkerThread(Socket connection) {
		this.connection = connection;
	}

	public void run() {
		try {
			String s;
			System.err.println("Worker thread started");
			
			//lets check if we already accepted maximum number of connections
			Server.mijnSemafoor.probeer();

			BufferedReader bin = new BufferedReader(new InputStreamReader(connection.getInputStream()));

			int counter = 0;
			while ((s = bin.readLine()) != null) {
//	            pout.println(s.toUpperCase());
//				System.out.println(s);
				counter += 1;

				if (s.equals("\t<MEASUREMENT>")){ counter = 0; }
				else if (s.equals("\t</MEASUREMENT>")){ if(counter != 15){ System.out.println(" "+counter); } }
	        }


			// now close the socket connection
			connection.close();
			System.err.println("Connection closing");

			// upping the semaphore.. since the connnection is gone....
			Server.mijnSemafoor.verhoog();
		}
		catch (IOException ioe) { }
		catch (InterruptedException ie) {}
	}
}

