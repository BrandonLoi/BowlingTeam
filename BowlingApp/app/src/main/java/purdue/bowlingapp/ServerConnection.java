package purdue.bowlingapp;

/**
 * Created by Hunter on 9/26/2017.
 */
//TODO: See if this works correctly
import java.sql.*;
public class ServerConnection {
    public static void main(String args[]){
            try{
                Class.forName("com.mysql.jdbc.Driver");
                Connection con=DriverManager.getConnection(
                        "jdbc:mysql://35.202.206.172:3306/purdue-bowling","root","brandon");
//here purdue-bowling is database name, root is username and brandon is the password
                Statement stmt=con.createStatement();
                ResultSet rs=stmt.executeQuery("select * from players"); //Test line to see if it works
                while(rs.next())
                    System.out.println(rs.getInt(1)+"  "+rs.getString(2)+"  "+rs.getString(3));
                con.close();
            }catch(Exception e){ System.out.println(e);}
        }
}
