package database;

import exception.DatabaseException;
import model.Budget;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DatabaseBudget {
    //return a budget object for a given userId
    public static Budget getBudget(long userId) throws DatabaseException {
        try {
            Connection conn = PersonalDatabase.getConnection();
            PreparedStatement budgetQuery = conn.prepareCall("SELECT * FROM transCategory WHERE transType = 2");
            ResultSet budgetResult = budgetQuery.executeQuery();
            long foundBudgetId = 0;
            if (budgetResult.first()) {
                foundBudgetId = budgetResult.getLong(1);
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
    
}
