package process;

import database.DatabaseBudget;
import database.DatabaseLoan;
import database.DatabaseSaving;
import exception.DatabaseException;
import exception.ProcessExeption;
import helper.IntervalEnum;
import model.Loan;
import model.Saving;

import java.time.LocalDate;
import java.util.ArrayList;

public class ProcessLoan {
    public static String addLoan(String Name, String desc, double interset, int activeTime, IntervalEnum.INTERVAL interestInterval, IntervalEnum.INTERVAL paymenInterVal) throws ProcessExeption {
        if (Name.length() >= 255) {
            throw new ProcessExeption(1);
        }
        if (desc.length() >= 1023) {
            throw new ProcessExeption(3);
        }
        if (activeTime <= 0) {
            throw new ProcessExeption();
        }
        if (interset < 0) {
            throw new ProcessExeption();
        }
        if (interestInterval == null) {
            throw new ProcessExeption();
        }
        if (paymenInterVal == null) {
            throw new ProcessExeption();
        }

        String id = null;
        try {
            Loan loan = new Loan(Name, desc, interset, LocalDate.now(), activeTime, interestInterval, paymenInterVal, 0, 0);
            id = DatabaseLoan.addLoan(loan, singletonBudget.getInstance().getBudget());
            loan.setId(id);
            singletonBudget.getInstance().getBudget().getActiveLoanList().add(loan);
        } catch (DatabaseException de) {
            de.printStackTrace();
            throw new ProcessExeption();
        }
        return id;
    }

    public static void editLoan(Loan loan, String name, String desc) throws ProcessExeption {
        if (name.length() >= 255) {
            throw new ProcessExeption(1);
        }
        if (desc.length() >= 1023) {
            throw new ProcessExeption(3);
        }
        loan.setName(name);
        loan.setDescription(desc);

        try {
            DatabaseLoan.updateLoan(loan);
            singletonBudget.getInstance().setBudget(DatabaseBudget.getBudget(singletonUser.getInstance().getUser()));
        } catch (DatabaseException de) {
            de.printStackTrace();
        }
    }
    public static void editLoan(Loan loan) throws ProcessExeption {
        try {
            DatabaseLoan.updateLoan(loan);
            singletonBudget.getInstance().setBudget(DatabaseBudget.getBudget(singletonUser.getInstance().getUser()));
        } catch (DatabaseException de) {
            de.printStackTrace();
        }
    }
    public static ArrayList<Loan> getLoan() throws ProcessExeption {
        try {
            return DatabaseLoan.getActiveLoan(singletonBudget.getInstance().getBudget().getId());
        } catch (DatabaseException de) {
            de.printStackTrace();
        }
        return null;
    }

    public static Loan getLoan(String id) throws ProcessExeption {
        ArrayList<Loan> loans = getLoan();
        for (Loan loan : loans) {
            if (loan.getId().equals(id)) return loan;
        }
        return null;
    }

    public static boolean deactivate(Loan loan) throws ProcessExeption {
        if (loan == null) throw new ProcessExeption();
        try {
            return DatabaseLoan.deactivateLoan(loan);
        } catch (DatabaseException de) {
            de.printStackTrace();
        }
        return false;
    }

    public static boolean deleteLoan(Loan loan) throws ProcessExeption {
        if (loan == null) throw new ProcessExeption();
        try {
            return DatabaseLoan.removeLoan(loan);
        } catch (DatabaseException de) {
            de.printStackTrace();
        }
        return false;
    }
    public static void applyLoan(String id,Double value) throws  ProcessExeption{
        Loan loan=getLoan(id);
        loan.setCurrentValue(value);
            editLoan(loan);

    }
}

