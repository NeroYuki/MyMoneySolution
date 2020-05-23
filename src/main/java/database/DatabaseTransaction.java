package database;

import exception.DatabaseException;
import helper.UUIDHelper;
import model.Expense;
import model.Income;
import model.Transaction;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class DatabaseTransaction {

    //Get a list of all transaction based on transaction's applied balance id
    public static ArrayList<Transaction> getTransaction(String balanceId) throws DatabaseException {
        //TODO: error check
        ArrayList<Transaction> result = new ArrayList<>();
        try {
            Connection conn = DatabaseManager.getConnection();
            PreparedStatement transactionQuery = conn.prepareStatement("SELECT * FROM Transaction WHERE applyBalance = ?");
            transactionQuery.setString(1, balanceId);
            ResultSet queryResult = transactionQuery.executeQuery();
            while (queryResult.next()) {
                //check transaction type (1 = income, 2 = expense)
                if (queryResult.getInt("transType") == 1) {
                    Income incomeEntry = new Income(
                            queryResult.getDate("occurDate").toLocalDate(),
                            queryResult.getFloat("value"),
                            queryResult.getString("description"),
                            DatabaseCategories.getCategoryById(queryResult.getString("transCategoryId"))
                    );
                    result.add(incomeEntry);
                }
                else if (queryResult.getInt("transType") == 2) {
                    Expense expenseEntry = new Expense(
                            queryResult.getDate("occurDate").toLocalDate(),
                            queryResult.getFloat("value"),
                            queryResult.getString("description"),
                            DatabaseCategories.getCategoryById(queryResult.getString("transCategoryId"))
                    );
                    result.add(expenseEntry);
                }
            }
        }
        catch (DatabaseException de) {
            throw de;
        }
        catch (Exception e) {
            //if this happen then oh god oh fuck
            throw new DatabaseException(0);
        }
        return result;
    }

    //Get a list of all income transaction based on transaction's applied balance id (Might not necessary)
    public static ArrayList<Income> getIncome(long balanceId) throws DatabaseException {
        ArrayList<Income> result = new ArrayList<>();
        try {
            Connection conn = DatabaseManager.getConnection();
        }
        catch (DatabaseException de) {
            throw de;
        }
        catch (Exception e) {
            //if this happen then oh god oh fuck
            throw new DatabaseException(0);
        }
        return result;
    }

    //Get a list of all expense transaction based on transaction's applied balance id (Might not necessary)
    public static ArrayList<Expense> getExpense(long balanceId) throws DatabaseException {
        ArrayList<Expense> result = new ArrayList<>();
        try {
            Connection conn = DatabaseManager.getConnection();
        }
        catch (DatabaseException de) {
            throw de;
        }
        catch (Exception e) {
            //if this happen then oh god oh fuck
            throw new DatabaseException(0);
        }
        return result;
    }

    public static boolean addIncome(Income trans) throws DatabaseException {
        try {
            Connection conn = DatabaseManager.getConnection();
            PreparedStatement createCall = conn.prepareCall("INSERT INTO transHistory VALUES (?, ?, ?, ?, 1, ?, ?)");
            if (trans.getId().equals("")) trans.setId(UUIDHelper.newUUIDString());
            else throw new DatabaseException(8);

            createCall.setString(1, trans.getId());
            createCall.setString(2, trans.getApplyingBalance().getId());
            createCall.setString(3, trans.getTransDescription());
            createCall.setDouble(4, trans.getTransValue());
            createCall.setString(5, trans.getCategory().getId());
            createCall.setDate(6, Date.valueOf(trans.getTransDate()));
            createCall.execute();

            int result = createCall.getUpdateCount();
            //TODO: new error code (8)
            if (result == 0) throw new DatabaseException(8);
        }
        catch (DatabaseException de) {
            throw de;
        }
        catch (Exception e) {
            throw new DatabaseException(0);
        }
        return true;
    }

    public static boolean addExpense(Expense trans) throws DatabaseException {
        try {
            Connection conn = DatabaseManager.getConnection();
            PreparedStatement createCall = conn.prepareCall("INSERT INTO transHistory VALUES (?, ?, ?, ?, 2, ?, ?)");
            if (trans.getId().equals("")) trans.setId(UUIDHelper.newUUIDString());
            else throw new DatabaseException(8);

            createCall.setString(1, trans.getId());
            createCall.setString(2, trans.getApplyingBalance().getId());
            createCall.setString(3, trans.getTransDescription());
            createCall.setDouble(4, trans.getTransValue());
            createCall.setString(5, trans.getCategory().getId());
            createCall.setDate(6, Date.valueOf(trans.getTransDate()));
            createCall.execute();

            int result = createCall.getUpdateCount();
            if (result == 0) throw new DatabaseException(8);
        }
        catch (DatabaseException de) {
            throw de;
        }
        catch (Exception e) {
            throw new DatabaseException(0);
        }
        return true;
    }
}
