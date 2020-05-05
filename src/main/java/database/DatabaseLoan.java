package database;

import com.mysql.cj.x.protobuf.MysqlxPrepare;
import exception.DatabaseException;
import helper.IntervalEnum;
import model.Loan;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class DatabaseLoan {
    //Get all active loan
    public static ArrayList<Loan> getActiveLoan(long budgetId) throws DatabaseException {
        //TODO: error check
        ArrayList<Loan> result = new ArrayList<>();
        try {
            //TODO: actually implement this
            Connection conn = PersonalDatabase.getConnection();
            PreparedStatement loanQuery = conn.prepareCall("SELECT * FROM loanHistory WHERE isActive = 1 AND budgetId = ?");
            loanQuery.setLong(1, budgetId);
            ResultSet activeLoanResult = loanQuery.executeQuery();
            while (activeLoanResult.next()) {
                Loan loanEntry = new Loan(
                        activeLoanResult.getString("name"),
                        activeLoanResult.getString("description"),
                        activeLoanResult.getDouble("interestRate"),
                        activeLoanResult.getDate("creationDate").toLocalDate(),
                        activeLoanResult.getInt("activeTimeSpan"),
                        IntervalEnum.INTERVAL.valueOf(activeLoanResult.getString("interestInterval")),
                        IntervalEnum.INTERVAL.valueOf(activeLoanResult.getString("paymentInterval")),
                        activeLoanResult.getDouble("baseValue"),
                        activeLoanResult.getDouble("currentValue")
                );
                result.add(loanEntry);
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

    public static void addNewLoan(Loan l) {

    }
}
