package database;

import exception.DatabaseException;
import model.*;

import java.sql.*;
import java.util.ArrayList;

import static helper.CharacterEncoding.utf8toNativeEncoding;

public class DatabaseManager {
    private static Connection conn = null;

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
            String loggedUserCreate = "CREATE TABLE loggedUser (\n" +
                    "    userId CHAR(36) NOT NULL,\n" +
                    "    username VARCHAR(255) NOT NULL,\n" +
                    "    password VARCHAR(255) NOT NULL,\n" +
                    "    email VARCHAR(255) DEFAULT NULL,\n" +
                    "    birthday DATE DEFAULT NULL,\n" +
                    "    PRIMARY KEY (userId)\n" +
                    ");";

            String userBudgetCreate = "CREATE TABLE userBudget (\n" +
                    "    budgetId CHAR(36) NOT NULL,\n" +
                    "    ownUser CHAR(36) NOT NULL,\n" +
                    "    isAvailable BIT DEFAULT TRUE,\n" +
                    "    PRIMARY KEY (budgetId),\n" +
                    "    FOREIGN KEY (ownUser) REFERENCES loggedUser(userId)\n" +
                    ");";

            String balanceListCreate = "CREATE TABLE balanceList (\n" +
                    "    balanceId CHAR(36) NOT NULL,\n" +
                    "    ownBudget CHAR(36) NOT NULL,\n" +
                    "    name VARCHAR(255) NOT NULL,\n" +
                    "    description VARCHAR(1024) DEFAULT NULL,\n" +
                    "    currentValue DOUBLE NOT NULL,\n" +
                    "    creationDate DATE NOT NULL,\n" +
                    "    isAvailable BIT DEFAULT TRUE,\n" +
                    "    PRIMARY KEY (balanceId),\n" +
                    "    FOREIGN KEY (ownBudget) REFERENCES userBudget(budgetId)\n" +
                    ");";

            String savingHistoryCreate = "CREATE TABLE savingHistory (\n" +
                    "    savingId CHAR(36) NOT NULL,\n" +
                    "    ownBudget CHAR(36) NOT NULL,\n" +
                    "    name VARCHAR(255) NOT NULL,\n" +
                    "    description VARCHAR(1024) DEFAULT NULL,\n" +
                    "    isActive INT DEFAULT 1,\n" +
                    "    creationDate DATE NOT NULL,\n" +
                    "    activeTimeSpan INT DEFAULT 0,\n" +
                    "    baseValue DOUBLE NOT NULL,\n" +
                    "    currentValue DOUBLE NOT NULL,\n" +
                    "    interestRate DOUBLE DEFAULT 0.0,\n" +
                    "    interestInterval ENUM('DAILY', 'WEEKLY', 'MONTHLY', 'QUARTERLY', 'YEARLY') DEFAULT 'MONTHLY',\n" +
                    "    lastCheckedDate DATE NOT NULL,\n" +
                    "    PRIMARY KEY (savingId),\n" +
                    "    FOREIGN KEY (ownBudget) REFERENCES userBudget(budgetId)\n" +
                    ");";

            String loanHistoryCreate = "CREATE TABLE loanHistory (\n" +
                    "    loanId CHAR(36) NOT NULL,\n" +
                    "    ownBudget CHAR(36) NOT NULL,\n" +
                    "    name VARCHAR(255) NOT NULL,\n" +
                    "    description VARCHAR(1024) DEFAULT NULL,\n" +
                    "    isActive INT DEFAULT 1,\n" +
                    "    creationDate DATE NOT NULL,\n" +
                    "    activeTimeSpan INT DEFAULT 0,\n" +
                    "    baseValue DOUBLE NOT NULL,\n" +
                    "    currentValue DOUBLE NOT NULL,\n" +
                    "    interestRate DOUBLE DEFAULT 0.0,\n" +
                    "    interestInterval ENUM('DAILY', 'WEEKLY', 'MONTHLY', 'QUARTERLY', 'YEARLY') DEFAULT 'MONTHLY',\n" +
                    "    paymentInterval ENUM('DAILY', 'WEEKLY', 'MONTHLY', 'QUARTERLY', 'YEARLY', 'ONE_TIME') DEFAULT 'MONTHLY',\n" +
                    "    lastCheckedDate DATE NOT NULL,\n" +
                    "    PRIMARY KEY (loanId),\n" +
                    "    FOREIGN KEY (ownBudget) REFERENCES userBudget(budgetId)\n" +
                    ");";

            String transCategoryCreate = "CREATE TABLE transCategory (\n" +
                    "    transCategoryId CHAR(36) NOT NULL,\n" +
                    "    transType INT NOT NULL,\n" +
                    "    name VARCHAR(255) NOT NULL,\n" +
                    "    description VARCHAR(1023) DEFAULT NULL,\n" +
                    "    iconPath VARCHAR(1023) DEFAULT NULL,\n" +
                    "    isAvailable BIT DEFAULT TRUE,\n" +
                    "    PRIMARY KEY (transCategoryId)\n" +
                    ");";

            String transHistoryCreate = "CREATE TABLE transHistory (\n" +
                    "    transId CHAR(36) NOT NULL,\n" +
                    "    applyBalance CHAR(36) NOT NULL,\n" +
                    "    description VARCHAR(1023) DEFAULT NULL,\n" +
                    "    value DOUBLE NOT NULL,\n" +
                    "    transType INT NOT NULL,\n" +
                    "    transCategoryId CHAR(36) NOT NULL,\n" +
                    "    occurDate DATE NOT NULL,\n" +
                    "    isAvailable BIT DEFAULT TRUE,\n" +
                    "    PRIMARY KEY (transId),\n" +
                    "    FOREIGN KEY (applyBalance) REFERENCES balanceList(balanceId),\n" +
                    "    FOREIGN KEY (transCategoryId) REFERENCES transCategory(transCategoryId)\n" +
                    ");";

            String financialGoalListCreate = "CREATE TABLE financialGoal (\n" +
                    "    goalId CHAR(36) NOT NULL,\n" +
                    "    ownBudget CHAR(36) NOT NULL,\n" +
                    "    description VARCHAR(1023) DEFAULT NULL,\n" +
                    "    type INT DEFAULT 1,\n" +
                    "    checkBalanceId CHAR(36) DEFAULT NULL,\n" +
                    "    threshold DOUBLE NOT NULL,\n" +
                    "    startDate DATE NOT NULL,\n" +
                    "    expireDate DATE NOT NULL,\n" +
                    "    isActive BIT DEFAULT TRUE,\n" +
                    "    PRIMARY KEY (goalId),\n" +
                    "    FOREIGN KEY (ownBudget) REFERENCES userBudget(budgetId)\n" +
                    ")";

            dbCreateStatement.execute(loggedUserCreate);
            dbCreateStatement.execute(userBudgetCreate);
            dbCreateStatement.execute(balanceListCreate);
            dbCreateStatement.execute(savingHistoryCreate);
            dbCreateStatement.execute(loanHistoryCreate);
            dbCreateStatement.execute(transCategoryCreate);
            dbCreateStatement.execute(transHistoryCreate);
            dbCreateStatement.execute(financialGoalListCreate);
        }
        catch (Exception e) {
            System.out.println(e);
        }
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
