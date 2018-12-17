import java.net.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
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

//			long startTime = System.currentTimeMillis();
			BufferedReader bin = new BufferedReader(new InputStreamReader(connection.getInputStream()));

//			int counter = 0;
			List<String> data = new ArrayList<String>();
			while ((s = bin.readLine()) != null) {

				int start =0;
				int end=0;

				int i = 0;
				for (;i < s.length(); i++) {
					if(s.charAt(i) == '>') {
						start = i;
					}
					else if(start != 0 && s.charAt(i) == '/') {
						System.out.print(s.substring(start +1, i-1));
						end = i;
						break;
					}
				}

				if(start != 0 && end != 0) {
					for (; i < s.length(); i++) {
						if (s.charAt(i) == '>') {
							System.out.println(s.substring(end + 1, i));
							break;
						}
					}
				}


//				counter += 1;
//
//				if (s.equals("\t<MEASUREMENT>")){ counter = 0; }
//				else if (s.equals("\t</MEASUREMENT>")){}
	        }

			System.out.println(data);

			// now close the socket connection
			connection.close();
			System.err.println("Connection closing");
		}
		catch (IOException ioe) { }
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