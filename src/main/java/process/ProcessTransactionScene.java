package process;

import database.*;
import exception.DatabaseException;
import exception.ProcessExeption;
import model.*;

import java.time.LocalDate;
import java.util.ArrayList;


public class ProcessTransactionScene {
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
    public static void addIncome(LocalDate date, double value, String desc, Category category,Balance balance) throws ProcessExeption{
        if(date == null) throw new ProcessExeption();
        if(value <0) throw new ProcessExeption();
        if(desc ==null) desc="";
        if (category == null) throw new ProcessExeption();
        if(balance ==null) throw new ProcessExeption();

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
        if(id == null) throw new ProcessExeption();
        if(date == null) throw new ProcessExeption();
        if(value <0) throw new ProcessExeption();
        if(desc ==null) desc="";
        if (category == null) throw new ProcessExeption();
        if(balance ==null) throw new ProcessExeption();
        Income income=new Income(id,date,value,desc,category,balance);
        try{
            DatabaseTransaction.updateTransaction(income);
        }
        catch (DatabaseException de)
        {
            throw new ProcessExeption(0);
        }
    }

    public static void addExpense(LocalDate date, double value, String desc, Category category,Balance balance) throws ProcessExeption{
        if(date == null) throw new ProcessExeption();
        if(value <0) throw new ProcessExeption();
        if(desc ==null) desc="";
        if (category == null) throw new ProcessExeption();
        if(balance ==null) throw new ProcessExeption();

        Income income=new Income(date,value,desc,category,balance);
        try{
            DatabaseTransaction.addIncome(income);
        }
        catch (DatabaseException de)
        {
            throw new ProcessExeption(0);
        }
    }
    public static void updateExpense(String id, LocalDate date, double value, String desc, Category category,Balance balance) throws ProcessExeption{
        if(id == null) throw new ProcessExeption();
        if(date == null) throw new ProcessExeption();
        if(value <0) throw new ProcessExeption();
        if(desc ==null) desc="";
        if (category == null) throw new ProcessExeption();
        if(balance ==null) throw new ProcessExeption();
        Income income=new Income(id,date,value,desc,category,balance);
        try{
            DatabaseTransaction.updateTransaction(income);
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









}
