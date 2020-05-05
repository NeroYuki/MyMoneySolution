package database;

import exception.DatabaseException;
import helper.IntervalEnum;
import model.Saving;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class DatabaseSaving {
    //Get all active saving
    public static ArrayList<Saving> getActiveSaving(long budgetId) throws DatabaseException {
        //TODO: error check
        ArrayList<Saving> result = new ArrayList<>();
        try {
            //TODO: actually implement this
            Connection conn = DatabaseManager.getConnection();
            PreparedStatement savingQuery = conn.prepareCall("SELECT * FROM savingHistory WHERE isActive = 1 AND budgetId = ?");
            savingQuery.setLong(1, budgetId);
            ResultSet activeSavingResult = savingQuery.executeQuery();
            while (activeSavingResult.next()) {
                Saving savingEntry = new Saving(
                        activeSavingResult.getString("name"),
                        activeSavingResult.getString("description"),
                        activeSavingResult.getDouble("interestRate"),
                        activeSavingResult.getDate("creationDate").toLocalDate(),
                        activeSavingResult.getInt("activeTimeSpan"),
                        IntervalEnum.INTERVAL.valueOf(activeSavingResult.getString("interestInterval")),
                        activeSavingResult.getDouble("baseValue"),
                        activeSavingResult.getDouble("currentValue")
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
}
