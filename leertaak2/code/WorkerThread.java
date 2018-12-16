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
			char[] s;
			System.err.println("Worker thread started");
			
			//lets check if we already accepted maximum number of connections
			Server.mijnSemafoor.probeer();

			long startTime = System.currentTimeMillis();
			BufferedReader bin = new BufferedReader(new InputStreamReader(connection.getInputStream()));

			int counter = 0;
			while ((s = bin.readLine().toCharArray()) != null) {
				counter += 1;

				if (s.equals("\t<MEASUREMENT>")){ counter = 0; }
				else if (s.equals("\t</MEASUREMENT>")){}
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

/*
<!--Het weatherdata-element bevat meerdere measurement-elementen-->
<WEATHERDATA>

<MEASUREMENT>
	<STN>123456</STN> 			<!-- Het station waarvan deze gegevens zijn  -->
	<DATE>2009-09-13</DATE>		<!-- Datum van versturen van deze gegevens, formaat: yyyy-mm-dd -->
	<TIME>15:59:46</TIME> 		<!-- Tijd van versturen van deze gegevens, formaat: hh:mm:ss -->
	<TEMP>-60.1</TEMP> 			<!-- Temperatuur in graden Celsius, geldige waardes van -9999.9 t/m 9999.9 met 1 decimaal -->
	<DEWP>-58.1</DEWP> 			<!-- Dauwpunt in graden Celsius, geldige waardes van -9999.9 t/m 9999.9 met 1 decimaal -->
	<STP>1034.5</STP> 			<!-- Luchtdruk op stationsniveau in millibar, geldige waardes van 0.0 t/m 9999.9 met 1 decimaal -->
	<SLP>1007.6</SLP> 			<!-- Luchtdruk op zeeniveau in millibar, geldige waardes van 0.0 t/m 9999.9 met 1 decimaal -->
	<VISIB>123.7</VISIB>		<!-- Zichtbaarheid in kilometers, geldige waardes van 0.0 t/m 999.9 met 1 decimaal -->
	<WDSP>10.8</WDSP> 			<!-- Windsnelheid in kilometers per uur, geldige waardes van 0.0 t/m 999.9 met 1 decimaal-->
	<PRCP>11.28</PRCP>			<!-- Neerslag in centimeters, geldige waardes van 0.00 t/m 999.99 met 2 decimalen -->
	<SNDP>11.1</SNDP>			<!-- Gevallen sneeuw in centimeters, geldige waardes van -9999.9 t/m 9999.9 met 1 decimaal -->
	<FRSHTT>010101</FRSHTT>		<!-- Binair gevroren, geregend, gesneeuwd, gehageld, onweer, Tornado/windhoos -->
	<CLDC>87.4</CLDC>			<!-- Bewolking in procenten, geldige waardes van 0.0 t/m 99.9 met 1 decimaal -->
	<WNDDIR>342</WNDDIR>		<!-- Windrichting in graden, geldige waardes van 0 t/m 359 alleen gehele getallen -->
</MEASUREMENT>

</WEATHERDATA>

*/