package database;

import exception.DatabaseException;
import helper.IntervalEnum;
import helper.UUIDHelper;
import model.Budget;
import model.Category;
import model.Loan;
import model.Saving;

import javax.xml.crypto.Data;
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

    public static boolean removeLoan(Loan loan) throws DatabaseException {
        try {
            Connection conn = DatabaseManager.getConnection();
            PreparedStatement removeCall = conn.prepareCall("DELETE FROM loanHistory WHERE loanId = ?");
            if (loan.getId().equals("")) throw new DatabaseException(13);

            removeCall.setString(1, loan.getId());
            removeCall.execute();
            int result = removeCall.getUpdateCount();
            if (result == 0) throw new DatabaseException(13);
            //TODO: delete all transactions involve the loan
        }
        catch (DatabaseException de) {
            throw de;
        }
        catch (Exception e) {
            throw new DatabaseException(0);
        }
        return true;
    }

    public static boolean updateLoan(Loan loan) throws DatabaseException {
        try {
            Connection conn = DatabaseManager.getConnection();
            PreparedStatement updateCall = conn.prepareCall(
                    "UPDATE loanHistory" +
                            "SET name = ?" +
                            "description = ?" +
                            "activeTimeSpan = ?" +
                            "currentValue = ?" +
                            "interestRate = ?" +
                            "WHERE loanId = ?"
            );
            if (loan.getId().equals("")) throw new DatabaseException(19);
            updateCall.setString(1, loan.getName());
            updateCall.setString(2, loan.getDescription());
            updateCall.setInt(3, loan.getActiveTimeSpan());
            updateCall.setDouble(4, loan.getCurrentValue());
            updateCall.setDouble(5, loan.getInterestRate());
            updateCall.setString(6, loan.getId());

            updateCall.execute();
            int result = updateCall.getUpdateCount();
            if (result == 0) throw new DatabaseException(19);
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
