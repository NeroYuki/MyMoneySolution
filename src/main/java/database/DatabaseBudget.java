package database;

import exception.DatabaseException;
import helper.UUIDHelper;
import model.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DatabaseBudget {
    //return a budget object for a given userId
    public static Budget getBudget(User user) throws DatabaseException {
        try {
            Connection conn = DatabaseManager.getConnection();
            PreparedStatement budgetQuery = conn.prepareCall("SELECT * FROM userBudget WHERE ownUser = ?");
            budgetQuery.setString(1, user.getId());
            ResultSet budgetResult = budgetQuery.executeQuery();
            String foundBudgetId = "";
            if (budgetResult.first()) {
                foundBudgetId = budgetResult.getString("budgetId");
            }
            else throw new DatabaseException(17);
            Budget result = new Budget(
                    foundBudgetId,
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
            PreparedStatement registerCall = conn.prepareCall("INSERT INTO userbudget VALUES (?, ?, ?)");
            if (budget.getId().equals("")) budget.setId(UUIDHelper.newUUIDString());
            else throw new DatabaseException(5);

            registerCall.setString(1, budget.getId());
            registerCall.setString(2, ownUser.getId());
            registerCall.setBoolean(3, true);
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

    public static boolean removeBudget(Budget budget) throws DatabaseException {
        try {
            Connection conn = DatabaseManager.getConnection();
            PreparedStatement removeCall = conn.prepareCall("DELETE FROM userbudget WHERE budgetId = ?");
            if (budget.getId().equals("")) throw new DatabaseException(11);

            for (Loan l : budget.getActiveLoanList()) {
                DatabaseLoan.removeLoan(l);
            }

            for (Saving s : budget.getActiveSavingList()) {
                DatabaseSaving.removeSaving(s);
            }

            for (Balance b : budget.getBalanceList()) {
                DatabaseBalance.removeBalance(b);
            }

            removeCall.setString(1, budget.getId());
            removeCall.execute();
            int result = removeCall.getUpdateCount();
            if (result == 0) throw new DatabaseException(11);
        }
        catch (DatabaseException de) {
            throw de;
        }
        catch (Exception e) {
            throw new DatabaseException(0);
        }
        return true;
    }

    public static boolean softRemoveBudget(Budget budget) throws DatabaseException {
        try {
            Connection conn = DatabaseManager.getConnection();
            PreparedStatement removeCall = conn.prepareCall(
                    "UPDATE userbudget " +
                            "SET isAvailable = FALSE " +
                            "WHERE budgetId = ?"
            );
            if (budget.getId().equals("")) throw new DatabaseException(11);

            removeCall.setString(1, budget.getId());
            removeCall.execute();
            int result = removeCall.getUpdateCount();
            if (result == 0) throw new DatabaseException(11);
        }
        catch (DatabaseException de) {
            throw de;
        }
        catch (Exception e) {
            throw new DatabaseException(0);
        }
        return true;
    }

    //TODO: make update method after completing Budget model
}
