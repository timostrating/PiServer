import java.net.*;
import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.*;

class FastThread implements Runnable  {
    private Socket connection;

    private final static boolean SHOW_DEBUG = true;
    private final static boolean SHOW_ERROR = true;
    private final static boolean DATABASE = true;

    public FastThread(Socket connection) {
        this.connection = connection;
    }

    public void run() {
        print("Worker thread started\n");

        Connection conn = null;
        String query = "insert into measurements (stn, date, time, temp, dewp, stp, slp, visib, wdsp, prcp, sndp, frshtt, cldc, wnddir) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try {

//            if (DATABASE) Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
//            if (DATABASE) conn = DriverManager.getConnection("jdbc:mysql://localhost/unwdmi?user=root&password=lol123");



            int s;

            InputStream is = connection.getInputStream();
            BufferedWriter writer = new BufferedWriter(new FileWriter("leuk.txt"));

            while ((s = is.read()) != -1)  {
                writer.write((char)s);
            }
            writer.close();



            // now close the socket connection
            conn.close();
            connection.close();
            error("Connection closing");
        }
        catch (Exception e) { System.err.println("Exception: " + Arrays.toString(e.getStackTrace()) + e.getMessage());}
    }

    private static void print(Object o) {
        if (SHOW_DEBUG)
            System.out.print(o);
    }

    private static void error(Object o) {
        if (SHOW_ERROR)
            System.err.print(o);
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
