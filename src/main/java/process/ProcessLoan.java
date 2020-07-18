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
    public static void addLoan(String Name, String desc, double interset, int activeTime, IntervalEnum.INTERVAL interestInterval,IntervalEnum.INTERVAL paymenInterVal, double baseValue)throws ProcessExeption {
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
        if (baseValue < 0) {
            throw new ProcessExeption();
        }
        try {
            Loan loan = new Loan(Name, desc, interset, LocalDate.now(), activeTime, interestInterval, paymenInterVal, baseValue, baseValue);
            DatabaseLoan.addLoan(loan, singletonBudget.getInstance().getBudget());
            singletonBudget.getInstance().getBudget().getActiveLoanList().add(loan);
        } catch (DatabaseException de) {
            de.printStackTrace();
            throw new ProcessExeption();
        }
    }
    public static void editLoan(Loan loan,String name,String desc)throws ProcessExeption {
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
        public static ArrayList<Loan> getLoan()throws  ProcessExeption {
            try {
                return DatabaseLoan.getActiveLoan(singletonBudget.getInstance().getBudget().getId());
            }
            catch (DatabaseException de)
            {
                de.printStackTrace();
            }
            return null;
        }
        public static boolean deactivate(Loan loan)throws ProcessExeption{
            if (loan==null) throw new ProcessExeption();
            try {
                return DatabaseLoan.deactivateLoan(loan);
            }
            catch (DatabaseException de){
                de.printStackTrace();
            }
            return false;
        }

    }

