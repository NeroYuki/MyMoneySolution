package database;

import exception.DatabaseException;
import helper.IntervalEnum;
import helper.UUIDHelper;
import model.Budget;
import model.Loan;
import model.Saving;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.sql.Date;

public class DatabaseSaving {
    //Get all active saving
    public static ArrayList<Saving> getActiveSaving(String budgetId) throws DatabaseException {
        ArrayList<Saving> result = new ArrayList<>();
        try {
            Connection conn = DatabaseManager.getConnection();
            PreparedStatement savingQuery = conn.prepareCall("SELECT * FROM savingHistory WHERE isActive = 1 AND ownBudget = ?");
            savingQuery.setString(1, budgetId);
            ResultSet activeSavingResult = savingQuery.executeQuery();
            while (activeSavingResult.next()) {
                Saving savingEntry = new Saving(
                        activeSavingResult.getString("savingId"),
                        activeSavingResult.getString("name"),
                        activeSavingResult.getString("description"),
                        activeSavingResult.getDouble("interestRate"),
                        activeSavingResult.getDate("creationDate").toLocalDate(),
                        activeSavingResult.getInt("activeTimeSpan"),
                        IntervalEnum.INTERVAL.valueOf(activeSavingResult.getString("interestInterval")),
                        activeSavingResult.getDouble("baseValue"),
                        activeSavingResult.getDouble("currentValue"),
                        activeSavingResult.getDate("lastCheckedDate").toLocalDate()
                );
                result.add(savingEntry);
            }
        } catch (DatabaseException de) {
            throw de;
        }
        catch (Exception e) {
            //if this happen then oh god oh fuck
            throw new DatabaseException(0);
        }
        return result;
    }

    public static boolean addSaving(Saving saving, Budget ownBudget) throws DatabaseException {
        try {
            Connection conn = DatabaseManager.getConnection();
            PreparedStatement registerCall = conn.prepareCall("INSERT INTO savingHistory VALUES (?, ?, ?, ?, 1, ?, ?, ?, ?, ?, ?, ?)");
            if (saving.getId().equals("")) saving.setId(UUIDHelper.newUUIDString());
            else throw new DatabaseException(7);

            registerCall.setString(1, saving.getId());
            registerCall.setString(2, ownBudget.getId());
            registerCall.setString(3, saving.getName());
            registerCall.setString(4, saving.getDescription());
            registerCall.setDate(5, Date.valueOf(saving.getCreationDate()));
            registerCall.setInt(6, saving.getActiveTimeSpan());
            registerCall.setDouble(7, saving.getBaseValue());
            registerCall.setDouble(8, saving.getCurrentValue());
            registerCall.setDouble(9, saving.getInterestRate());
            registerCall.setString(10, saving.getInterestInterval().toString());
            registerCall.setDate(11, Date.valueOf(saving.getLastCheckedDate()));
            registerCall.execute();
            int result = registerCall.getUpdateCount();
            if (result == 0) throw new DatabaseException(7);
        }
        catch (DatabaseException de) {
            throw de;
        }
        catch (Exception e) {
            throw new DatabaseException(0);
        }
        return true;
    }

    //not recommend, use deactivate instead

    public static boolean removeSaving(Saving saving) throws DatabaseException {
        try {
            Connection conn = DatabaseManager.getConnection();
            PreparedStatement removeCall = conn.prepareCall("DELETE FROM savingHistory WHERE savingId = ?");
            if (saving.getId().equals("")) throw new DatabaseException(14);

            removeCall.setString(1, saving.getId());
            removeCall.execute();
            int result = removeCall.getUpdateCount();
            if (result == 0) throw new DatabaseException(14);
        }
        catch (DatabaseException de) {
            throw de;
        }
        catch (Exception e) {
            throw new DatabaseException(0);
        }
        return true;
    }

    public static boolean deactivateSaving(Saving saving) throws DatabaseException {
        try {
            Connection conn = DatabaseManager.getConnection();
            PreparedStatement removeCall = conn.prepareCall(
                    "UPDATE savinghistory " +
                            "SET isActive = FALSE " +
                            "WHERE savingId = ?"
            );
            if (saving.getId().equals("")) throw new DatabaseException(20);

            removeCall.setString(1, saving.getId());
            removeCall.execute();
            int result = removeCall.getUpdateCount();
            if (result == 0) throw new DatabaseException(20);
        }
        catch (DatabaseException de) {
            throw de;
        }
        catch (Exception e) {
            throw new DatabaseException(0);
        }
        return true;
    }

    public static boolean updateSaving(Saving saving) throws DatabaseException {
        try {
            Connection conn = DatabaseManager.getConnection();
            PreparedStatement updateCall = conn.prepareCall(
                    "UPDATE savingHistory " +
                            "SET name = ?, " +
                            "description = ?, " +
                            "activeTimeSpan = ?, " +
                            "currentValue = ?, " +
                            "interestRate = ?," +
                            "lastCheckedDate = ? " +
                            "WHERE savingId = ?"
            );
            if (saving.getId().equals("")) throw new DatabaseException(20);
            updateCall.setString(1, saving.getName());
            updateCall.setString(2, saving.getDescription());
            updateCall.setInt(3, saving.getActiveTimeSpan());
            updateCall.setDouble(4, saving.getCurrentValue());
            updateCall.setDouble(5, saving.getInterestRate());
            updateCall.setDate(6, Date.valueOf(saving.getLastCheckedDate()));
            updateCall.setString(7, saving.getId());

            updateCall.execute();
            int result = updateCall.getUpdateCount();
            if (result == 0) throw new DatabaseException(20);
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
