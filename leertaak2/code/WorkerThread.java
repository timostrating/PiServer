import java.net.*;
import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.*;

class WorkerThread implements Runnable  {
	private Socket connection;

	private final static boolean SHOW_DEBUG = false;
	private final static boolean SHOW_ERROR = false;
	private final static boolean DATABASE = true;

	public WorkerThread(Socket connection) {
		this.connection = connection;
	}

	public void run() {
		print("Worker thread started\n");

		Connection conn = null;
		String query = "insert into measurements (stn, date, time, temp, dewp, stp, slp, visib, wdsp, prcp, sndp, frshtt, cldc, wnddir) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		final int batchSize = 10; //Batch size is important
		int insertCount=0;

		try {

			if (DATABASE) Class.forName("com.mysql.jdbc.Driver").newInstance();
			if (DATABASE) conn = DriverManager.getConnection("jdbc:mysql://localhost/unwdmi?user=root&password=lol123");


			String s;

			BufferedReader bin = new BufferedReader(new InputStreamReader(connection.getInputStream()));

			String[] input = new String[14];

			Map<String, Integer> lookup = new HashMap<String, Integer>();
			lookup.put("STN", 0);
			lookup.put("DATE", 1);
			lookup.put("TIME", 2);
			lookup.put("TEMP", 3);
			lookup.put("DEWP", 4);
			lookup.put("STP", 5);
			lookup.put("SLP", 6);
			lookup.put("VISIB", 7);
			lookup.put("WDSP", 8);
			lookup.put("PRCP", 9);
			lookup.put("SNDP", 10);
			lookup.put("FRSHTT", 11);
			lookup.put("CLDC", 12);
			lookup.put("WNDDIR", 13);



//			int counter = 0;
			while ((s = bin.readLine()) != null) {
//
//				if (s.equals("\t</MEASUREMENT>")) { counter++; continue;}
				if (s.equals("\t</MEASUREMENT>")) {
//					counter %= 10;

					if (DATABASE) {
						PreparedStatement preparedStmt = conn.prepareStatement(query);
						for (int i=1; i <= input.length; i++)
							preparedStmt.setString (i, input[i-1]);

						preparedStmt.addBatch();
						if (++insertCount % batchSize == 0) {
							preparedStmt.executeBatch();
						}
					}

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
//								counter += 1;
							}
							break;
						}
					}
				}
	        }

			print(Arrays.toString(input));

			// now close the socket connection
			conn.close();
			connection.close();
			error("Connection closing");
		}
		catch (Exception e) { System.err.println("Exception: " + Arrays.toString(e.getStackTrace()) + e.getMessage());}
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

    10 x  <MEASUREMENT>
        <STN>123456</STN>
        <DATE>2009-09-13</DATE>
        <TIME>15:59:46</TIME>
        <TEMP>-60.1</TEMP>
        <DEWP>-58.1</DEWP>
        <STP>1034.5</STP>
        <SLP>1007.6</SLP>
        <VISIB>123.7</VISIB>
        <WDSP>10.8</WDSP>
        <PRCP>11.28</PRCP>
        <SNDP>11.1</SNDP>
        <FRSHTT>010101</FRSHTT>
        <CLDC>87.4</CLDC>
        <WNDDIR>342</WNDDIR>
    </MEASUREMENT>

    </WEATHERDATA>

*/