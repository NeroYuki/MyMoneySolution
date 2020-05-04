package database;

import exception.DatabaseException;
import model.Balance;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class DatabaseBalance {
    //Get all balance from a budgetId
    public static ArrayList<Balance> getBalances(long budgetId) throws DatabaseException {
        //TODO: error check
        ArrayList<Balance> result = new ArrayList<>();
        try {
            Connection conn = PersonalDatabase.getConnection();
            PreparedStatement balanceQuery = conn.prepareCall("SELECT * FROM balanceList WHERE ownBudget = ?");
            balanceQuery.setLong(1, budgetId);
            ResultSet balanceResult = balanceQuery.executeQuery();
            while (balanceResult.next()) {
                Balance balanceEntry = new Balance(
                        balanceResult.getString(3),
                        balanceResult.getString(4),
                        balanceResult.getDouble(5)
                );
                result.add(balanceEntry);
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

    public static void addBalance(Balance bal) {

    }
}
