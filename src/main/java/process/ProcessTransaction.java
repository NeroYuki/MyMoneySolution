package process;

import database.*;
import exception.DatabaseException;
import exception.ProcessExeption;
import model.*;

import java.time.LocalDate;
import java.util.ArrayList;


public class ProcessTransaction {
    public static ArrayList<Transaction> getTransactionsInfo() throws ProcessExeption{
        try {
            Budget budget = singletonBudget.getInstance().getBudget();
            ArrayList<Balance> balances=DatabaseBalance.getBalances(budget.getId());
            ArrayList<Transaction> transaction = new ArrayList<>();
            for(int i=0;i<balances.size();i++)
            {
                ArrayList<Transaction> transactions2=DatabaseTransaction.getTransaction(balances.get(i).getId());
                for (Transaction j:transactions2) {
                    j.setApplyingBalance(balances.get(i));
                }
                transaction.addAll(transactions2);
            }
            return transaction;
        }
        catch(DatabaseException de){
            System.out.println(de.getErrorCodeMessage());
            throw new ProcessExeption(0);
        }
    }
    public static ArrayList<Transaction> getTransactionsInfo(Balance balance) throws ProcessExeption{
        try {
            ArrayList<Transaction> transaction = new ArrayList<>();
            transaction=DatabaseTransaction.getTransaction(balance.getId());
                for (Transaction j:transaction) {
                    j.setApplyingBalance(balance);
                }
            return transaction;
        }
        catch(DatabaseException de){
            System.out.println(de.getErrorCodeMessage());
            throw new ProcessExeption(0);
        }
    }
    public static ArrayList<Transaction> getTransactionsInfo(LocalDate startDate, LocalDate endDate) throws ProcessExeption{
        try {
            Budget budget = singletonBudget.getInstance().getBudget();
            ArrayList<Balance> balances=DatabaseBalance.getBalances(budget.getId());
            ArrayList<Transaction> transaction = new ArrayList<>();
            for(int i=0;i<balances.size();i++)
            {
                ArrayList<Transaction> transactions2=DatabaseTransaction.getTransaction(balances.get(i).getId(),startDate,endDate);
                for (Transaction j:transactions2) {
                    j.setApplyingBalance(balances.get(i));
                }
                transaction.addAll(transactions2);
            }
            return transaction;
        }
        catch(DatabaseException de){
            System.out.println(de.getErrorCodeMessage());
            throw new ProcessExeption(0);
        }

    }
    public static ArrayList<Transaction> getTransactionsInfo(Balance balance ,LocalDate startDate, LocalDate endDate) throws ProcessExeption{
        try {
            ArrayList<Transaction> transaction = new ArrayList<>();
            transaction=DatabaseTransaction.getTransaction(balance.getId(),startDate,endDate);
            for (Transaction j:transaction) {
                j.setApplyingBalance(balance);
            }
            return transaction;
        }
        catch(DatabaseException de){
            System.out.println(de.getErrorCodeMessage());
            throw new ProcessExeption(0);
        }
    }
    public static ArrayList<Income> getIncome(Balance balance ,LocalDate startDate, LocalDate endDate) throws ProcessExeption {
        try {
            ArrayList<Income> incomes = new ArrayList<>();
            incomes = DatabaseTransaction.getIncome(balance.getId(), startDate, endDate);
            for (Income j : incomes) {
                j.setApplyingBalance(balance);
            }
            return incomes;
        } catch (DatabaseException de) {
            System.out.println(de.getErrorCodeMessage());
            throw new ProcessExeption(0);
        }
    }
    public static ArrayList<Income> getAllIncome(LocalDate startDate, LocalDate endDate) throws ProcessExeption {
        try {
            ArrayList<Income> incomes = new ArrayList<>();
            ArrayList<Balance> balances=new ArrayList<>();
            balances=ProcessBalance.getBalances();
            for (Balance balance:balances) {
                incomes.addAll(DatabaseTransaction.getIncome(balance.getId(), startDate, endDate));
                for (Income j : incomes) {
                    j.setApplyingBalance(balance);
                }
            }
            return incomes;
        } catch (DatabaseException de) {
            System.out.println(de.getErrorCodeMessage());
            throw new ProcessExeption(0);
        }
    }

    public static ArrayList<Expense> getExpense(Balance balance ,LocalDate startDate, LocalDate endDate) throws ProcessExeption {
        try {
            ArrayList<Expense> expenses = new ArrayList<>();
            expenses = DatabaseTransaction.getExpense(balance.getId(), startDate, endDate);
            for (Expense j : expenses) {
                j.setApplyingBalance(balance);
            }
            return expenses;
        } catch (DatabaseException de) {
            System.out.println(de.getErrorCodeMessage());
            throw new ProcessExeption(0);
        }
    }

    public static ArrayList<Expense> getAllExpense(LocalDate startDate, LocalDate endDate) throws ProcessExeption {
        try {
            ArrayList<Expense> expenses = new ArrayList<>();
            ArrayList<Balance> balances=new ArrayList<>();
            balances=ProcessBalance.getBalances();
            for (Balance balance:balances) {
                expenses.addAll(DatabaseTransaction.getExpense(balance.getId(), startDate, endDate));
                for (Expense j : expenses) {
                    j.setApplyingBalance(balance);
                }
            }
            return expenses;
        } catch (DatabaseException de) {
            System.out.println(de.getErrorCodeMessage());
            throw new ProcessExeption(0);
        }
    }





    public static void addIncome(LocalDate date, double value, String desc, Category category,Balance balance) throws ProcessExeption{
        if(date == null) throw new ProcessExeption(10);
        if(value <0) throw new ProcessExeption(11);
        if(desc ==null) desc="";
        if (category == null) throw new ProcessExeption(12);
        if(balance ==null) throw new ProcessExeption(13);

        Income income=new Income(date,value,desc,category,balance);
        income.applyToBalance();
        try{
            DatabaseBalance.updateBalance(income.getApplyingBalance());
            DatabaseTransaction.addIncome(income);
        }
        catch (DatabaseException de)
        {
            System.out.println(de.getErrorCodeMessage());
            throw new ProcessExeption(0);
        }
    }
    public static void updateIncome(String id, LocalDate date, double value, String desc, Category category,Balance balance) throws ProcessExeption{
        if(id == null) throw new ProcessExeption(8);
        if(date == null) throw new ProcessExeption(10);
        if(value <0) throw new ProcessExeption(11);
        if(desc ==null) desc="";
        if (category == null) throw new ProcessExeption(12);
        if(balance ==null) throw new ProcessExeption(13);

        Income income=new Income(id,date,value,desc,category,balance);
        income.applyToBalance();
        try{
            DatabaseBalance.updateBalance(income.getApplyingBalance());
            DatabaseTransaction.updateTransaction(income);
        }
        catch (DatabaseException de)
        {
            throw new ProcessExeption(0);
        }
    }

    public static void addExpense(LocalDate date, double value, String desc, Category category,Balance balance) throws ProcessExeption{
        if(date == null) throw new ProcessExeption(10);
        if(value <0) throw new ProcessExeption(11);
        if(desc ==null) desc="";
        if (category == null) throw new ProcessExeption(12);
        if(balance ==null) throw new ProcessExeption(13);

        Expense expense=new Expense(date,value,desc,category,balance);
        expense.applyToBalance();
        try{
            DatabaseBalance.updateBalance(expense.getApplyingBalance());
            DatabaseTransaction.addExpense(expense);
        }
        catch (DatabaseException de)
        {
            throw new ProcessExeption(0);
        }
    }
    public static void updateExpense(String id, LocalDate date, double value, String desc, Category category,Balance balance) throws ProcessExeption{
        if(id == null) throw new ProcessExeption();
        if(date == null) throw new ProcessExeption(10);
        if(value <0) throw new ProcessExeption(11);
        if(desc ==null) desc="";
        if (category == null) throw new ProcessExeption(12);
        if(balance ==null) throw new ProcessExeption(13);

        Expense expense=new Expense(date,value,desc,category,balance);
        expense.applyToBalance();
        try{
            DatabaseBalance.updateBalance(expense.getApplyingBalance());
            DatabaseTransaction.updateTransaction(expense);
        }
        catch (DatabaseException de)
        {
            throw new ProcessExeption(0);
        }
    }
    public static void deleteTransaction(Transaction transaction)throws ProcessExeption{
        if(transaction==null) throw new ProcessExeption(9);
        try{
            revertTransaction(transaction);
            DatabaseTransaction.removeTransaction(transaction);
        }
        catch (DatabaseException de)
        {
            throw new ProcessExeption(0);
        }
        catch (ProcessExeption pe)
        {
            throw pe;
        }
    }



    public static void revertTransaction(Transaction transaction)throws  ProcessExeption{
        if (transaction == null) throw new ProcessExeption(9);
        Balance balance=transaction.getApplyingBalance();
        if(transaction.getType()=="Income") {
            balance.setValue(balance.getValue() - transaction.getTransValue());
        }
        if(transaction.getType()=="Expense") {
            balance.setValue(balance.getValue() + transaction.getTransValue());
        }
        try {
            DatabaseBalance.updateBalance(balance);
        }
        catch (DatabaseException de)
        {
            System.out.println(de.getErrorCodeMessage());
        }
    }


    public static ArrayList<Income> getAllIncome() throws ProcessExeption{
        try {
            ArrayList<Income> incomes = new ArrayList<>();
            ArrayList<Balance> balances=new ArrayList<>();
            balances=ProcessBalance.getBalances();
            for (Balance balance:balances) {
                ArrayList<Income> temp= (DatabaseTransaction.getIncome(balance.getId()));
                for (Income j : temp) {
                    j.setApplyingBalance(balance);
                }
                incomes.addAll(temp);
            }
            return incomes;
        } catch (DatabaseException de) {
            System.out.println(de.getErrorCodeMessage());
            throw new ProcessExeption(0);
        }
    }

    public static ArrayList<Expense> getAllExpense() throws ProcessExeption {
        try {
            ArrayList<Expense> expenses = new ArrayList<>();
            ArrayList<Balance> balances = new ArrayList<>();
            balances = ProcessBalance.getBalances();
            for (Balance balance : balances) {
                ArrayList<Expense> temp= (DatabaseTransaction.getExpense(balance.getId()));
                for (Expense j : expenses) {
                    j.setApplyingBalance(balance);
                }
                expenses.addAll(temp);
            }
            return expenses;
        } catch (DatabaseException de) {
            System.out.println(de.getErrorCodeMessage());
            throw new ProcessExeption(0);
        }
    }
}
