package database;

import exception.DatabaseException;
import helper.UUIDHelper;
import model.Budget;
import model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DatabaseBudget {
    //return a budget object for a given userId
    public static Budget getBudget(long userId) throws DatabaseException {
        try {
            Connection conn = DatabaseManager.getConnection();
            PreparedStatement budgetQuery = conn.prepareCall("SELECT * FROM userBudget WHERE ownUser = ?");
            budgetQuery.setLong(1, userId);
            ResultSet budgetResult = budgetQuery.executeQuery();
            String foundBudgetId = "";
            if (budgetResult.first()) {
                foundBudgetId = budgetResult.getString("budgetId");
            }
            Budget result = new Budget(
                    DatabaseBalance.getBalances(foundBudgetId),
                    DatabaseSaving.getActiveSaving(foundBudgetId),
                    DatabaseLoan.getActiveLoan(foundBudgetId)
            );
            return result;
        }
        catch (DatabaseException de) {
            throw de;
        }
        catch (Exception e) {
            //if this happen then oh god oh fuck
            throw new DatabaseException(0);
        }
    }
    public static boolean addBudget(Budget budget, User ownUser) throws DatabaseException {
        try {
            Connection conn = DatabaseManager.getConnection();
            PreparedStatement registerCall = conn.prepareCall("INSERT INTO budgetList VALUES (?, ?)");
            if (budget.getId().equals("")) budget.setId(UUIDHelper.newUUIDString());
            else throw new DatabaseException(5);

            registerCall.setString(1, budget.getId());
            registerCall.setString(2, ownUser.getId());
            registerCall.execute();
            int result = registerCall.getUpdateCount();
            if (result == 0) throw new DatabaseException(5);
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
