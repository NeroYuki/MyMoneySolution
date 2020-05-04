package database;

import exception.DatabaseException;
import model.Loan;

import java.sql.Connection;
import java.util.ArrayList;

public class DatabaseLoan {
    //Get all active loan
    public static ArrayList<Loan> getActiveLoan(long budgetId) throws DatabaseException {
        //TODO: error check
        ArrayList<Loan> result = new ArrayList<>();
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

    public static void addNewLoan(Loan l) {

    }
}
