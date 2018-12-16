package tests;

import java.sql.*;

public class Database {

    public static void main(String args[]){
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try{
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();

            conn = DriverManager.getConnection("jdbc:mysql://localhost/music?user=root&password=lol123");

            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT * FROM artist");

            while (rs.next()) {
                System.out.println(rs.getInt(1) + " " + rs.getString(2));
            }

            conn.close();
        }catch(Exception e){ System.out.println(e);}

    }
}
