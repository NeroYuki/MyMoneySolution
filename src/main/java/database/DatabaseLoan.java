package database;

import exception.DatabaseException;
import helper.IntervalEnum;
import helper.UUIDHelper;
import model.Budget;
import model.Loan;
import model.Saving;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class DatabaseLoan {
    //Get all active loan
    public static ArrayList<Loan> getActiveLoan(String budgetId) throws DatabaseException {
        //TODO: error check
        ArrayList<Loan> result = new ArrayList<>();
        try {
            //TODO: actually implement this
            Connection conn = DatabaseManager.getConnection();
            PreparedStatement loanQuery = conn.prepareCall("SELECT * FROM loanHistory WHERE isActive = 1 AND budgetId = ?");
            loanQuery.setString(1, budgetId);
            ResultSet activeLoanResult = loanQuery.executeQuery();
            while (activeLoanResult.next()) {
                Loan loanEntry = new Loan(
                        activeLoanResult.getString("loanId"),
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

    public static boolean addLoan(Loan loan, Budget ownBudget) throws DatabaseException {
        try {
            Connection conn = DatabaseManager.getConnection();
            PreparedStatement registerCall = conn.prepareCall("INSERT INTO loanHistory VALUES (?, ?, ?, ?, 1, ?, ?, ?, ?, ?, ?, ?)");
            if (loan.getId().equals("")) loan.setId(UUIDHelper.newUUIDString());
            else throw new DatabaseException(6);

            registerCall.setString(1, loan.getId());
            registerCall.setString(2, ownBudget.getId());
            registerCall.setString(3, loan.getName());
            registerCall.setString(4, loan.getDescription());
            registerCall.setDate(5, Date.valueOf(loan.getCreationDate()));
            registerCall.setInt(6, loan.getActiveTimeSpan());
            registerCall.setDouble(7, loan.getBaseValue());
            registerCall.setDouble(8, loan.getCurrentValue());
            registerCall.setDouble(9, loan.getInterestRate());
            registerCall.setString(10, loan.getInterestInterval().toString());
            registerCall.setString(11, loan.getPaymentInterval().toString());

            registerCall.execute();
            int result = registerCall.getUpdateCount();
            if (result == 0) throw new DatabaseException(6);
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
