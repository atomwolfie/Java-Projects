import com.mysql.jdbc.Connection;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DecimalFormat;

/**
 * Class to be used for connecting with the database
 */
public class DBConnection {
    public static final String dbUrl = "jdbc:mysql://localhost:3306/demo?autoReconnect=true&useSSL=false";
    public static final String dbUser = "root";
    public static final String dbPassword = "W01fp@ck";

    /**
     * Get the amount of records in a given table
     */
    public static int dbGetRecordCountForTable(String table) {
        try {
            Connection con = (Connection) DriverManager.getConnection(dbUrl, dbUser, dbPassword);
            Statement myStmt = con.createStatement();
            ResultSet rs = myStmt.executeQuery("SELECT COUNT(*) FROM " + table);
            while (rs.next()) {
                return rs.getInt(1);
            }
        }
        catch (Exception e1) {
            e1.printStackTrace();
        }
        return -1;
    }

    /**
     * Get the column data for a given table
     */
    public static ResultSet dbGetColumnDataFromTable(String table, String columns) {
        try {
            Connection con = (Connection) DriverManager.getConnection(dbUrl, dbUser, dbPassword);
            Statement myStmt = con.createStatement();
            return myStmt.executeQuery("SELECT " + columns + " FROM " + table);
        }
        catch (Exception e1) {
            e1.printStackTrace();
        }
        return null;
    }

    /**
     * Select all the records from a given table
     */
    public static ResultSet dbSelectAllFromTable(String table) {
        try {
            Connection con = (Connection) DriverManager.getConnection(dbUrl, dbUser, dbPassword);
            Statement myStmt = con.createStatement();
            return myStmt.executeQuery("SELECT * FROM " + table);
        }
        catch (Exception e1) {
            e1.printStackTrace();
        }
        return null;
    }

    /**
     * Select all the records from a table that satisfy the given constraint
     */
    public static ResultSet dbSelectAllFromTableWhere(String table, String constraint) {
        try {
            Connection con = (Connection) DriverManager.getConnection(dbUrl, dbUser, dbPassword);
            Statement myStmt = con.createStatement();
            return myStmt.executeQuery("SELECT * FROM " + table + " WHERE " + constraint);
        }
        catch (Exception e1) {
            e1.printStackTrace();
        }
        return null;
    }

    public static ResultSet dbSelectAllFromTableOrderBy(String table, String orderBy) {
        try {
            Connection con = (Connection) DriverManager.getConnection(dbUrl, dbUser, dbPassword);
            Statement myStmt = con.createStatement();
            return myStmt.executeQuery("SELECT * FROM " + table + " ORDER BY " + orderBy);
        }
        catch (Exception e1) {
            e1.printStackTrace();
        }
        return null;
    }

    public static boolean dbInsertInto(String tableAndCols, String values) {
        try {
            Connection con = (Connection) DriverManager.getConnection(dbUrl, dbUser, dbPassword);
            Statement myStmt = con.createStatement();
            DecimalFormat dec = new DecimalFormat("#.00");
            myStmt.executeUpdate("INSERT INTO " + tableAndCols + " VALUES (" + values + ")");
        }
        catch (Exception e1) {
            e1.printStackTrace();
        }
        return false;
    }

    public static boolean dbUpdateRecord(String table, String setValue, String constraint) {
        try {
            Connection con = (Connection) DriverManager.getConnection(dbUrl, dbUser, dbPassword);
            Statement myStmt = con.createStatement();
            DecimalFormat dec = new DecimalFormat("#.00");
            System.out.println("UPDATE " + table + " SET " + setValue + " WHERE " + constraint);
            myStmt.executeUpdate("UPDATE " + table + " SET " + setValue + " WHERE " + constraint);
            return true;
        }
        catch (Exception e1) {
            e1.printStackTrace();
        }
        return false;
    }
}
