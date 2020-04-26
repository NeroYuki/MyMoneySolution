package database;

import java.nio.charset.Charset;
import java.sql.*;
import java.util.ArrayList;

public class PersonalDatabase {
    private static Connection conn = null;

    //Use this to re-encode UTF-8 string (Result of database output
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

    //Check if a connection is established
    public static boolean checkConnection() {
        if (conn == null) return false;
        return true;
    }

    //Check if a certain table exists in the database
    public static boolean checkDatabase(String table) {
        boolean result = false;
        if (!checkConnection()) {
            //TODO: throw exception here
            return false;
        }
        try {
            //Show table have the name "UserInfo"
            PreparedStatement dbCheckStatement = conn.prepareStatement(
                    "SHOW TABLES LIKE ?"
            );
            dbCheckStatement.setString(1, table);
            ResultSet dbCheckResult = dbCheckStatement.executeQuery();

            if (dbCheckResult.first()) result = true;
            System.out.println(result);
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
            //TODO: throw exception here
            return;
        }
        try {
            Statement dbCreateStatement = conn.createStatement();
            String userInfoCreateSql =
                    "CREATE TABLE UserInfo (" +
                        "id VARCHAR(20) NOT NULL, " +
                        "username VARCHAR(255) DEFAULT NULL, " +
                        "birthday DATE DEFAULT NULL," +
                        "PRIMARY KEY (id)" +
                    ") DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;";

            String expenseCreateSql =
                    "CREATE TABLE Expense (" +
                        "user_id VARCHAR(20) NOT NULL, " +
                        "expense_id VARCHAR(20) NOT NULL, " +
                        "name VARCHAR(255) DEFAULT NULL, " +
                        "description VARCHAR(1024) DEFAULT NULL, " +
                        "PRIMARY KEY (expense_id)" +
                    ") DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci";

            dbCreateStatement.execute(userInfoCreateSql);
            dbCreateStatement.execute(expenseCreateSql);
        }
        catch (Exception e) {
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
