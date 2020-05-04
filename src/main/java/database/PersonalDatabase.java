package database;

import model.*;

import java.nio.charset.Charset;
import java.sql.*;
import java.util.ArrayList;

public class PersonalDatabase {
    private static Connection conn = null;

    //Use this to re-encode UTF-8 string (Result of database output)
    public static String utf8toNativeEncoding(String input) throws Exception {
        Charset defaultCharset = Charset.defaultCharset();
        byte[] sourceBytes = input.getBytes("UTF-8");
        return new String(sourceBytes , defaultCharset.name());
    }

    //Initialize a connection to a database with authentication provided in parameter
    public static void initConnection(String dbURL, String userName, String password) {
        try {
            conn = DriverManager.getConnection(dbURL, userName, password);
            System.out.println("connect successfully!");
        } catch (Exception ex) {
            System.out.println("connect failure!");
            ex.printStackTrace();
        }
    }

    static Connection getConnection() {
        if (!checkConnection()) {
            System.out.println("Connection have yet been establish!");
            return null;
        }
        return conn;
    }

    //Check if a connection is established
    public static boolean checkConnection() {
        if (conn == null) return false;
        return true;
    }

    //Check if a certain table exists in the database
    public static boolean checkDatabase(String table) {
        boolean result = false;
        if (!checkConnection()) {
            System.out.println("Connection have yet been establish!");
            return false;
        }
        try {
            //Show table have the given name
            PreparedStatement dbCheckStatement = conn.prepareStatement(
                    "SHOW TABLES LIKE ?"
            );
            dbCheckStatement.setString(1, table);
            ResultSet dbCheckResult = dbCheckStatement.executeQuery();

            if (dbCheckResult.first()) result = true;
            //System.out.println(result);
            return result;
        }
        catch (Exception e) {
            System.out.println(e);
        }
        return result;
    }

    //Initialize required table in the database (UserInfo)
    public static void initDatabase() {
        if (!checkConnection()) {
            System.out.println("Connection have yet been establish!");
            return;
        }
        try {
            Statement dbCreateStatement = conn.createStatement();
            //TODO: Create all required database
        }
        catch (Exception e) {
            System.out.println(e);
        }
    }

    //Get all balance
    public static ArrayList<Balance> getBalances(String budgetId) {
        ArrayList<Balance> result = new ArrayList<>();
        return result;
    }

    //Get all active loan
    public static ArrayList<Loan> getActiveLoan(String budgetId) {
        ArrayList<Loan> result = new ArrayList<>();
        return result;
    }

    //Get all active saving
    public static ArrayList<Saving> getActiveSaving(String budgetId) {
        ArrayList<Saving> result = new ArrayList<>();
        return result;
    }

    //Get user info
    public static User getUserInfo(String username) {
        User result = new User();
        return result;
    }

    //Terminate current connection to database, only call when program is shut down
    public static void terminateConnection() {
        if (!checkConnection()) {
            System.out.println("Connection have yet been establish!");
            return;
        }
        try {
            conn.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    //Only for testing, delete this method when project is completed
    public static ArrayList<String[]> testLoad(String tableName, int col) {
        ArrayList<String[]> result = new ArrayList<>();
        try {
            PreparedStatement loadStatement = conn.prepareStatement("SELECT * FROM ?");
            loadStatement.setString(1, tableName);
            ResultSet loadResult = loadStatement.executeQuery();
            while (loadResult.next()) {
                String[] entry = new String[col];
                entry[0] = String.valueOf(loadResult.getInt(1));
                entry[1] = loadResult.getString(2);
                entry[2] = loadResult.getString(3);
                result.add(entry);
            }

            for (String[] entry : result) {
                System.out.println(entry[0] + "\t " + utf8toNativeEncoding(entry[1]) + "\t " + utf8toNativeEncoding(entry[2]));
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return result;
    }
}
