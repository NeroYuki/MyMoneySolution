package database;

import exception.DatabaseException;
import helper.UUIDHelper;
import model.Balance;
import model.Budget;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;

public class DatabaseBalance {
    //Get all balance from a given budgetId
    public static ArrayList<Balance> getBalances(String budgetId) throws DatabaseException {
        //TODO: error check
        ArrayList<Balance> result = new ArrayList<>();
        try {
            Connection conn = DatabaseManager.getConnection();
            PreparedStatement balanceQuery = conn.prepareCall("SELECT * FROM balanceList WHERE ownBudget = ?");
            balanceQuery.setString(1, budgetId);
            ResultSet balanceResult = balanceQuery.executeQuery();
            while (balanceResult.next()) {
                Balance balanceEntry = new Balance(
                        balanceResult.getString("name"),
                        balanceResult.getString("description"),
                        balanceResult.getDouble("currentValue")
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

    //Add a new balance instance to balance database
    public static boolean addBalance(Balance bal, Budget ownBudget) throws DatabaseException {
        try {
            Connection conn = DatabaseManager.getConnection();
            PreparedStatement registerCall = conn.prepareCall("INSERT INTO balanceList VALUES (?, ?, ?, ?, ?, ?)");
            if (bal.getId().equals("")) bal.setId(UUIDHelper.newUUIDString());
            else throw new DatabaseException(4);
            registerCall.setString(1, bal.getId());
            registerCall.setString(2, ownBudget.getId());
            registerCall.setString(3, bal.getName());
            registerCall.setString(4, bal.getDescription());
            registerCall.setDouble(5, bal.getValue());
            registerCall.setDate(6, Date.valueOf(LocalDate.now()));
            registerCall.execute();
            int result = registerCall.getUpdateCount();
            if (result == 0) throw new DatabaseException(4);
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
