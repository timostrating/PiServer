import com.sun.org.apache.xerces.internal.util.ShadowedSymbolTable;

import java.lang.reflect.Array;
import java.net.*;
import java.io.*;
import java.util.*;
//import java.util.concurrent.*;

class WorkerThread implements Runnable  {
	private Socket connection;

	private final static boolean SHOW_DEBUG = false;
	private final static boolean SHOW_ERROR = false;

	public WorkerThread(Socket connection) {
		this.connection = connection;
	}

	public void run() {
		try {
			String s;
			print("Worker thread started\n");

			BufferedReader bin = new BufferedReader(new InputStreamReader(connection.getInputStream()));

			String[] input = new String[14];

			Map<String, Integer> lookup = new HashMap<String, Integer>();
			lookup.put("STN", 0); lookup.put("DATE", 1); lookup.put("TIME", 2); lookup.put("TEMP", 3);
			lookup.put("DEWP", 4); lookup.put("STP", 5); lookup.put("SLP", 6); lookup.put("VISIB", 7);
			lookup.put("WDSP", 8); lookup.put("PRCP", 9); lookup.put("SNDP", 10); lookup.put("FRSHTT", 11);
			lookup.put("CLDC", 12); lookup.put("WNDDIR", 13);


			int counter = 0;
			List<String> data = new ArrayList<String>();

			while ((s = bin.readLine()) != null) {
				if (SHOW_DEBUG) data.add(s);
//
				if (s.equals("\t<MEASUREMENT>")) { counter = 0; continue;}
				else if (s.equals("\t</MEASUREMENT>")) {
					if (counter != 14) {
						error("INCORRECT INPUT");
						error(counter + Arrays.toString(input));
					}

					print(Arrays.toString(input));
					if (SHOW_DEBUG) print(data);
					if (SHOW_DEBUG) data.clear();
					continue;
				}

				int start = 0; // TODO: we currently only parse xml that opens and closes a tag on the same line
				int end = 0;
				int lookup_index = 0;
				String value = "";

				int i = 0;
				for (;i < s.length(); i++) {
					if(s.charAt(i) == '>') {
						start = i;
					}
					else if(start != 0 && s.charAt(i) == '/') {
						value = s.substring(start +1, i-1);
						end = i;
						break;
					}
				}

				if(start != 0 && end != 0) {
					for (; i < s.length(); i++) {
						if (s.charAt(i) == '>') {
							try {
								lookup_index = lookup.get(s.substring(end + 1, i));
							} catch (NullPointerException e) { error("UNSUPPORTED TAG in XML INPUT: " + s.substring(end + 1, i)); break; }

							if (end - start <= 2) {
								print("EMPTY DATA FOR " + s.substring(end + 1, i));
							} else {
								input[lookup_index] = value;
								counter += 1;
							}
							break;
						}
					}
				}
	        }

			print(Arrays.toString(input));

			// now close the socket connection
			connection.close();
			error("Connection closing");
		}
		catch (IOException ignored) { }
	}

	private static void print(Object o) {
		if (SHOW_DEBUG)
			System.out.println(o);
	}

	private static void error(Object o) {
		if (SHOW_ERROR)
			System.err.println(o);
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