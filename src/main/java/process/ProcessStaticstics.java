package process;

import database.DatabaseCategories;
import database.DatabaseTransaction;
import exception.DatabaseException;
import exception.ProcessExeption;
import model.Budget;
import model.Category;
import model.Income;
import model.Transaction;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;

public class ProcessStaticstics {
    //time 0: weakly 1: monthly 2 quarterly 3 yearly 4 alltime
    public static Map<Category,Double> getTimelyIncome(int time)throws ProcessExeption{
        ArrayList<Income>temp;
        ArrayList<Transaction> transactions;
        double Value =0;
        LocalDate localDate=LocalDate.now();
        Hashtable<Category, Double> hashtable=new Hashtable<Category,Double>();

        if(time ==0){
            localDate =localDate.minusDays(7);
        }
        if (time ==1){
            localDate =localDate.minusMonths(1);
        }
        if(time ==2){
            localDate = localDate.minusMonths(3);
        }
        if(time ==3){
            localDate =localDate.minusYears(1);
        }
        try {

            singletonUser usedModel = singletonUser.getInstance();

            ArrayList<Category> categories= DatabaseCategories.getIncomeCategory();
            if(time==4){
                for (int i = 0; i < categories.size(); i++) {
                    Value = 0;
                    transactions = DatabaseTransaction.getTransactionByCategory(categories.get(i).getId());
                    for (Transaction j : transactions) {
                        Value += j.getTransValue();
                    }
                    if (Value != 0)
                        hashtable.put( categories.get(i),Value);
                }
            }
            else {
                for (int i = 0; i < categories.size(); i++) {
                    Value = 0;
                    transactions = DatabaseTransaction.getTransactionByCategory(categories.get(i).getId());
                    for (Transaction j : transactions) {
                        if (j.getTransDate().isAfter(localDate))
                            Value += j.getTransValue();
                    }
                    if (Value != 0)
                        hashtable.put( categories.get(i),Value);
                }
            }
        }
        catch (DatabaseException de)
        {
            throw new ProcessExeption(0);
        }
        return hashtable;
    }
    public static Map<Category,Double> getTimelyExpense(int time)throws ProcessExeption{
        ArrayList<Income>temp;
        ArrayList<Transaction> transactions;
        double Value =0;
        LocalDate localDate=LocalDate.now();
        Hashtable<Category, Double> hashtable=new Hashtable<Category,Double>();

        if(time ==0){
            localDate =localDate.minusDays(7);
        }
        if (time ==1){
            localDate =localDate.minusMonths(1);
        }
        if(time ==2){
            localDate = localDate.minusMonths(3);
        }
        if(time ==3){
            localDate =localDate.minusYears(1);
        }
        try {

            singletonUser usedModel = singletonUser.getInstance();

            ArrayList<Category> categories= DatabaseCategories.getExpenseCategory();
            if(time==4){
                for (int i = 0; i < categories.size(); i++) {
                    Value = 0;
                    transactions = DatabaseTransaction.getTransactionByCategory(categories.get(i).getId());
                    for (Transaction j : transactions) {
                        Value += j.getTransValue();
                    }
                    if (Value != 0)
                        hashtable.put( categories.get(i),Value);
                }
            }
            else {
                for (int i = 0; i < categories.size(); i++) {
                    Value = 0;
                    transactions = DatabaseTransaction.getTransactionByCategory(categories.get(i).getId());
                    for (Transaction j : transactions) {
                        if (j.getTransDate().isAfter(localDate))
                            Value += j.getTransValue();
                    }
                    if (Value != 0)
                        hashtable.put( categories.get(i),Value);
                }
            }
        }
        catch (DatabaseException de)
        {
            throw new ProcessExeption(0);
        }
        return hashtable;
    }
}
