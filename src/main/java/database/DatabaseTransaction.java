package database;

import exception.DatabaseException;
import model.Expense;
import model.Income;
import model.Transaction;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class DatabaseTransaction {

    //Get a list of all transaction based on transaction's applied balance id
    public static ArrayList<Transaction> getTransaction(long balanceId) throws DatabaseException {
        //TODO: error check
        ArrayList<Transaction> result = new ArrayList<>();
        try {
            Connection conn = PersonalDatabase.getConnection();
            PreparedStatement transactionQuery = conn.prepareStatement("SELECT * FROM Transaction WHERE applyBalance = ?");
            transactionQuery.setLong(1, balanceId);
            ResultSet queryResult = transactionQuery.executeQuery();
            while (queryResult.next()) {
                //check transaction type (1 = income, 2 = expense)
                if (queryResult.getInt(5) == 1) {
                    Income incomeEntry = new Income(
                            queryResult.getDate(7).toLocalDate(),
                            queryResult.getFloat(4),
                            queryResult.getString(2),
                            DatabaseCategories.getCategoryById(queryResult.getLong(6))
                    );
                    result.add(incomeEntry);
                }
                else if (queryResult.getInt(5) == 2) {
                    Expense expenseEntry = new Expense(
                            queryResult.getDate(7).toLocalDate(),
                            queryResult.getFloat(4),
                            queryResult.getString(2),
                            DatabaseCategories.getCategoryById(queryResult.getLong(6))
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
            Connection conn = PersonalDatabase.getConnection();
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
            Connection conn = PersonalDatabase.getConnection();
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
}
