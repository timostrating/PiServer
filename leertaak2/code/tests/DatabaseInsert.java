package tests;

import java.sql.*;

public class DatabaseInsert {
    public static void main(String args[]){
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try{
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();

            conn = DriverManager.getConnection("jdbc:mysql://localhost/music?user=root&password=lol123");

            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT * FROM artist");

            String query = " insert into users (first_name, last_name, date_created, is_admin, num_points) values (?, ?, ?, ?, ?)";

            // create the mysql insert preparedstatement
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setString (1, "Barney");
            preparedStmt.setString (2, "Rubble");
            preparedStmt.setString (3, "Rubble");
            preparedStmt.setBoolean(4, false);
            preparedStmt.setInt    (5, 5000);

            // execute the preparedstatement
            preparedStmt.execute();

            conn.close();
        }catch(Exception e){ System.out.println(e);}

    }
}
