package Model;

import java.sql.Connection;
import java.sql.DriverManager;

public class Db_Connect {

    public static Connection Connect_Db(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/marathon_database", "root", "");
            return con;
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
}}
