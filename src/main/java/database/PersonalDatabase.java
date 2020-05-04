package database;

import exception.DatabaseException;
import model.*;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.util.ArrayList;

public class PersonalDatabase {
    private static Connection conn = null;

    //Use this to re-encode UTF-8 string (Result of database output)
    public static String utf8toNativeEncoding(String input) throws Exception {
        Charset defaultCharset = Charset.defaultCharset();
        byte[] sourceBytes = input.getBytes(StandardCharsets.UTF_8);
        return new String(sourceBytes , defaultCharset.name());
    }

    //Initialize a connection to a database with authentication provided in parameter
    public static void initConnection(String dbURL, String userName, String password) {
        try {
            conn = DriverManager.getConnection(dbURL, userName, password);
            System.out.println("connect successfully!");
        }
        catch (Exception ex) {
            System.out.println("connect failure!");
            ex.printStackTrace();
        }
    }

    static Connection getConnection() throws DatabaseException {
        if (!checkConnection()) {
            System.out.println("Connection have yet been establish!");
            throw new DatabaseException(1);
        }
        return conn;
    }

    //Check if a connection is established
    public static boolean checkConnection() {
        return conn != null;
    }

    //Check if a certain table exists in the database
    public static boolean checkTableDatabase(String table) {
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
        finally {
            return result;
        }
    }

    //Initialize required table in the database (only if the correct database server is found)
    public static void initDatabase() {
        if (!checkConnection()) {
            System.out.println("Connection have yet been establish!");
            return;
        }
        try {
            Statement dbCreateStatement = conn.createStatement();
            String loggedUserCreate =
                    "CREATE TABLE loggedUser (\n" +
                    "    userId BIGINT NOT NULL,\n" +
                    "    username VARCHAR(255) NOT NULL,\n" +
                    "    email VARCHAR(255) DEFAULT NULL,\n" +
                    "    birthday DATE DEFAULT NULL,\n" +
                    "    PRIMARY KEY (userId)\n" +
                    ");";

            String userBudgetCreate =
                    "CREATE TABLE userBudget (\n" +
                    "\tbudgetId BIGINT NOT NULL,\n" +
                    "    ownUser BIGINT NOT NULL,\n" +
                    "    PRIMARY KEY (budgetId),\n" +
                    "    FOREIGN KEY (ownUser) REFERENCES loggedUser(userId)\n" +
                    ");;";

            String balanceListCreate =
                    "CREATE TABLE balanceList (\n" +
                    "    balanceId BIGINT NOT NULL,\n" +
                    "    ownBudget BIGINT NOT NULL,\n" +
                    "    name VARCHAR(255) NOT NULL,\n" +
                    "    description VARCHAR(1024) DEFAULT NULL,\n" +
                    "    currentValue FLOAT NOT NULL,\n" +
                    "    creationDate DATE NOT NULL,\n" +
                    "    PRIMARY KEY (balanceId),\n" +
                    "    FOREIGN KEY (ownBudget) REFERENCES userBudget(budgetId)\n" +
                    ");";

            String savingHistoryCreate =
                    "CREATE TABLE savingHistory (\n" +
                    "    savingId BIGINT NOT NULL,\n" +
                    "    ownBudget BIGINT NOT NULL,\n" +
                    "    name VARCHAR(255) NOT NULL,\n" +
                    "    description VARCHAR(1024) DEFAULT NULL,\n" +
                    "    isActive INT DEFAULT 1, \n" +
                    "    creationDate DATE NOT NULL,\n" +
                    "    activeTimeSpan INT DEFAULT 0, \n" +
                    "    baseValue FLOAT NOT NULL,\n" +
                    "    currentValue FLOAT NOT NULL, \n" +
                    "    interestRate FLOAT DEFAULT 0.0,\n" +
                    "    interestInterval ENUM('DAILY', 'WEEKLY', 'MONTHLY', 'QUARTERLY', 'YEARLY') DEFAULT 'MONTHLY',\n" +
                    "    PRIMARY KEY (savingId),\n" +
                    "    FOREIGN KEY (ownBudget) REFERENCES userBudget(budgetId)\n" +
                    ");";

            String loanHistoryCreate =
                    "CREATE TABLE loanHistory (\n" +
                    "    loanId BIGINT NOT NULL,\n" +
                    "    ownBudget BIGINT NOT NULL,\n" +
                    "    name VARCHAR(255) NOT NULL,\n" +
                    "    description VARCHAR(1024) DEFAULT NULL,\n" +
                    "    isActive INT DEFAULT 1, \n" +
                    "    creationDate DATE NOT NULL,\n" +
                    "    activeTimeSpan INT DEFAULT 0, \n" +
                    "    baseValue FLOAT NOT NULL,\n" +
                    "    currentValue FLOAT NOT NULL, \n" +
                    "    interestRate FLOAT DEFAULT 0.0,\n" +
                    "    interestInterval ENUM('DAILY', 'WEEKLY', 'MONTHLY', 'QUARTERLY', 'YEARLY') DEFAULT 'MONTHLY',\n" +
                    "    paymentInterval ENUM('DAILY', 'WEEKLY', 'MONTHLY', 'QUARTERLY', 'YEARLY') DEFAULT 'MONTHLY',\n" +
                    "    PRIMARY KEY (loanId),\n" +
                    "    FOREIGN KEY (ownBudget) REFERENCES userBudget(budgetId)\n" +
                    ");";

            String transCategoryCreate =
                    "CREATE TABLE transCategory (\n" +
                    "    transCategoryId BIGINT NOT NULL,\n" +
                    "    transType INT NOT NULL, \n" +
                    "    name VARCHAR(255) NOT NULL,\n" +
                    "    description VARCHAR(1023) DEFAULT NULL,\n" +
                    "    iconPath VARCHAR(1023) DEFAULT NULL,\n" +
                    "    PRIMARY KEY (transCategoryId)\n" +
                    ");";

            String transHistoryCreate =
                    "CREATE TABLE transHistory (\n" +
                    "    transId BIGINT NOT NULL,\n" +
                    "    applyBalance BIGINT NOT NULL,\n" +
                    "    description VARCHAR(1023) DEFAULT NULL,\n" +
                    "    value FLOAT NOT NULL,\n" +
                    "    transType INT NOT NULL,\n" +
                    "    transCategoryId BIGINT NOT NULL,\n" +
                    "    occurDate DATE NOT NULL,\n" +
                    "    PRIMARY KEY (transId),\n" +
                    "    FOREIGN KEY (applyBalance) REFERENCES balanceList(balanceId),\n" +
                    "    FOREIGN KEY (transCategoryId) REFERENCES transCategory(transCategoryId)\n" +
                    ");\n";

            dbCreateStatement.execute(loggedUserCreate);
            dbCreateStatement.execute(userBudgetCreate);
            dbCreateStatement.execute(balanceListCreate);
            dbCreateStatement.execute(savingHistoryCreate);
            dbCreateStatement.execute(loanHistoryCreate);
            dbCreateStatement.execute(transCategoryCreate);
            dbCreateStatement.execute(transHistoryCreate);
        }
        catch (Exception e) {
            System.out.println(e);
        }
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
