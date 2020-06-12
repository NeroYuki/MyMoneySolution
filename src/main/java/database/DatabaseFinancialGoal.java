package database;

import exception.DatabaseException;
import helper.UUIDHelper;
import model.Balance;
import model.Budget;
import model.FinancialGoal;
import model.Transaction;

import javax.xml.crypto.Data;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;

public class DatabaseFinancialGoal {
    public static ArrayList<FinancialGoal> getFinancialGoal(String budgetId) throws DatabaseException {
        //TODO: error check
        ArrayList<FinancialGoal> result = new ArrayList<>();
        try {
            Connection conn = DatabaseManager.getConnection();
            PreparedStatement goalQuery = conn.prepareCall("SELECT * FROM financialgoal WHERE ownBudget = ?");
            goalQuery.setString(1, budgetId);
            ResultSet goalResult = goalQuery.executeQuery();
            while (goalResult.next()) {
                FinancialGoal financialGoalEntry = new FinancialGoal(
                        goalResult.getString("goalId"),
                        goalResult.getString("description"),
                        goalResult.getInt("type"),
                        goalResult.getDouble("threshold"),
                        goalResult.getDate("startDate").toLocalDate(),
                        goalResult.getDate("expireDate").toLocalDate(),
                        null
                );
                if (financialGoalEntry.getType() == 3) {
                    financialGoalEntry.setCheckBalance(DatabaseBalance.getBalanceById(goalResult.getString("checkBalanceId")));
                }
                result.add(financialGoalEntry);
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

    public static boolean addFinancialGoal(FinancialGoal financialGoal, Budget ownBudget) throws DatabaseException {
        try {
            Connection conn = DatabaseManager.getConnection();
            PreparedStatement registerCall = conn.prepareCall("INSERT INTO financialgoal VALUES (?, ?, ?, ?, ?, ?, ?, ?, 1)");
            if (financialGoal.getId().equals("")) financialGoal.setId(UUIDHelper.newUUIDString());
            else throw new DatabaseException(26);
            registerCall.setString(1, financialGoal.getId());
            registerCall.setString(2, ownBudget.getId());
            registerCall.setString(3, financialGoal.getDescription());
            registerCall.setInt(4, financialGoal.getType());
            registerCall.setString(5, financialGoal.getCheckBalance().getId());
            registerCall.setDouble(6, financialGoal.getThreshold());
            registerCall.setDate(7, Date.valueOf(financialGoal.getStartDate()));
            registerCall.setDate(8, Date.valueOf(financialGoal.getExpireDate()));
            registerCall.execute();
            int result = registerCall.getUpdateCount();
            if (result == 0) throw new DatabaseException(26);
        }
        catch (DatabaseException de) {
            throw de;
        }
        catch (Exception e) {
            throw new DatabaseException(0);
        }
        return true;
    }

    public static boolean updateFinancialGoal(FinancialGoal financialGoal) throws DatabaseException {
        try {
            Connection conn = DatabaseManager.getConnection();
            PreparedStatement updateCall = conn.prepareCall(
                    "UPDATE financialgoal " +
                            "SET description = ?," +
                            "threshold = ?," +
                            "startDate = ?, " +
                            "expireDate = ? " +
                            "WHERE goalId = ?"
            );
            if (financialGoal.getId().equals("")) throw new DatabaseException(25);
            updateCall.setString(1, financialGoal.getDescription());
            updateCall.setDouble(2, financialGoal.getThreshold());
            updateCall.setDate(3, Date.valueOf(financialGoal.getStartDate()));
            updateCall.setDate(4, Date.valueOf(financialGoal.getExpireDate()));
            updateCall.setString(5, financialGoal.getId());
            updateCall.execute();
            int result = updateCall.getUpdateCount();
            if (result == 0) throw new DatabaseException(25);
        }
        catch (DatabaseException de) {
            throw de;
        }
        catch (Exception e) {
            throw new DatabaseException(0);
        }
        return true;
    }

    public static boolean softRemoveFinancialGoal(FinancialGoal financialGoal) throws DatabaseException {
        try {
            Connection conn = DatabaseManager.getConnection();
            PreparedStatement removeCall = conn.prepareCall(
                    "UPDATE financialgoal " +
                            "SET isActive = FALSE " +
                            "WHERE goalId = ?"
            );
            if (financialGoal.getId().equals("")) throw new DatabaseException(25);

            removeCall.setString(1, financialGoal.getId());
            removeCall.execute();
            int result = removeCall.getUpdateCount();
            if (result == 0) throw new DatabaseException(25);
        }
        catch (DatabaseException de) {
            throw de;
        }
        catch (Exception e) {
            throw new DatabaseException(0);
        }
        return true;
    }

    public static boolean removeFinancialGoal(FinancialGoal financialGoal) throws DatabaseException {
        try {
            Connection conn = DatabaseManager.getConnection();
            PreparedStatement removeCall = conn.prepareCall("DELETE FROM financialgoal WHERE goalId = ?");
            if (financialGoal.getId().equals("")) throw new DatabaseException(24);

            removeCall.setString(1, financialGoal.getId());
            removeCall.execute();
            int result = removeCall.getUpdateCount();
            if (result == 0) throw new DatabaseException(10);
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
