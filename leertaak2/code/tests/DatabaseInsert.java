package tests;

import java.sql.*;

public class DatabaseInsert {
    public static void main(String args[]){
        Connection conn = null;

        try{
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();

            conn = DriverManager.getConnection("jdbc:mysql://localhost/unwdmi?user=root&password=lol123");

            String query = "insert into measurements (stn, date, time, temp, dewp, stp, slp, visib, wdsp, prcp, sndp, frshtt, cldc, wnddir) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setString (1, "123456"); // stn
            preparedStmt.setString (2, "2009-09-13"); // date
            preparedStmt.setString (3, "15:59:46"); // time
            preparedStmt.setString (4, "-60.1"); // temp
            preparedStmt.setString (5, "-58.1"); // dewp
            preparedStmt.setString (6, "1034.5"); // stp
            preparedStmt.setString (7, "1007.6"); // slp
            preparedStmt.setString (8, "123.7"); // visib
            preparedStmt.setString (9, "10.8"); // wdsp
            preparedStmt.setString (10, "11.28"); // prcp
            preparedStmt.setString (11, "11.1"); // sndp
            preparedStmt.setString (12, "010101"); // frshtt
            preparedStmt.setString (13, "87.4"); // cldc,
            preparedStmt.setString (14, "342"); // wnddi

            preparedStmt.execute();

            conn.close();
        }catch(Exception e){ System.out.println(e);}

    }
}