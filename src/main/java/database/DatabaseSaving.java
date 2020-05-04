package database;

import exception.DatabaseException;
import model.Saving;
import model.Transaction;

import java.sql.Connection;
import java.util.ArrayList;

public class DatabaseSaving {
    //Get all active saving
    public static ArrayList<Saving> getActiveSaving(long budgetId) throws DatabaseException {
        //TODO: error check
        ArrayList<Saving> result = new ArrayList<>();
        try {
            //TODO: actually implement this
            Connection conn = PersonalDatabase.getConnection();
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
