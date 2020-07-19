package process;

import database.DatabaseCategories;
import database.DatabaseTransaction;
import exception.DatabaseException;
import exception.ProcessExeption;
import model.*;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class ProcessStaticstics {
    //time 0: weakly 1: monthly 2 quarterly 3 yearly 4 alltime

    public static class ChartModel{
        public ArrayList<LocalDate> localDates=null;
        public ArrayList<Double> numbers=null;

        public ChartModel(ArrayList<LocalDate> localDates ,ArrayList<Double> numbers){
            this.localDates=localDates;
            this.numbers= numbers;
        }
    }
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


    public static ChartModel getTransactionInTime(Balance balance,LocalDate start,LocalDate end)throws ProcessExeption {
        if(balance == null) return null;
        if(start==null) return null;
        if(end==null) return null;
        if(end.isBefore(start)) return null;
        if(start.isAfter(LocalDate.now()))return null;
        ChartModel chartModel = null;
        LocalDate endDay = null;
        if (end.isBefore(LocalDate.now())) {
            endDay = end;
        } else endDay = LocalDate.now();

        Map<LocalDate, Double> maps = new TreeMap<LocalDate, Double>();
        List<LocalDate> localDateList = start.datesUntil(endDay.plusDays(1)).collect(Collectors.toList());
        for (LocalDate local : localDateList) {
            maps.put(local, 0.0);
        }
        ArrayList<Transaction> transactions = ProcessTransaction.getTransactionsInfo(balance, start, endDay);
        Double bal = balance.getValue();
        for (Transaction transaction : transactions) {
            if (transaction.getType() == "Income") bal -= transaction.getTransValue();
            else if (transaction.getType() == "Expense") bal += transaction.getTransValue();
        }
        maps.put(start, bal);
        double temp2 = 0;
        for (Transaction transaction : transactions) {
            if (transaction.getType() == "Income") temp2 = transaction.getTransValue();
            else if (transaction.getType() == "Expense") temp2 = -transaction.getTransValue();
            else temp2 = 0;
            maps.put(transaction.getTransDate(), maps.get(transaction.getTransDate()) + temp2);
        }

        ArrayList<LocalDate> dates = new ArrayList<LocalDate>();
        ArrayList<Double> values = new ArrayList<Double>();
        double temp = 0;

        for (Map.Entry<LocalDate, Double> entry : maps.entrySet()) {
            dates.add(entry.getKey());
            temp += entry.getValue();
            values.add(temp);
        }



        chartModel=new ChartModel(dates,values);
        return chartModel;
    }

    public static ChartModel getIncomeChart(Balance balance,LocalDate start,LocalDate end)throws ProcessExeption {
        if (balance == null) return null;
        if (start == null) return null;
        if (end == null) return null;
        if(end.isBefore(start)) return null;
        if(start.isAfter(LocalDate.now()))return null;

        ChartModel chartModel = null;

        LocalDate endDay = null;
        if (end.isBefore(LocalDate.now())) {
            endDay = end;
        } else endDay = LocalDate.now();

        Map<LocalDate, Double> maps = new TreeMap<LocalDate, Double>();
        List<LocalDate> localDateList = start.datesUntil(endDay.plusDays(1)).collect(Collectors.toList());
        for (LocalDate local : localDateList) {
            maps.put(local, 0.0);
        }
        try{
            ArrayList<Income> incomes=ProcessTransaction.getIncome(balance,start,endDay);
            for (Income income:incomes) {
                maps.put(income.getTransDate(),maps.get(income.getTransDate())+income.getTransValue());
            }
        }
        catch (ProcessExeption pe) {
            throw pe;
        }
        ArrayList<LocalDate> dates = new ArrayList<LocalDate>();
        ArrayList<Double> values = new ArrayList<Double>();

        for (Map.Entry<LocalDate, Double> entry : maps.entrySet()) {
            dates.add(entry.getKey());
            values.add(entry.getValue());
        }

        chartModel =new ChartModel(dates,values);

        return chartModel;
    }

    public static ChartModel getExpenseChart(Balance balance,LocalDate start,LocalDate end)throws ProcessExeption {
        if (balance == null) return null;
        if (start == null) return null;
        if (end == null) return null;
        if(end.isBefore(start)) return null;
        if(start.isAfter(LocalDate.now()))return null;

        ChartModel chartModel = null;

        LocalDate endDay = null;
        if (end.isBefore(LocalDate.now())) {
            endDay = end;
        } else endDay = LocalDate.now();

        Map<LocalDate, Double> maps = new TreeMap<LocalDate, Double>();
        List<LocalDate> localDateList = start.datesUntil(endDay.plusDays(1)).collect(Collectors.toList());
        for (LocalDate local : localDateList) {
            maps.put(local, 0.0);
        }
        try{
            ArrayList<Expense> expenses=ProcessTransaction.getExpense(balance,start,endDay);
            for (Expense expense:expenses) {
                maps.put(expense.getTransDate(),maps.get(expense.getTransDate())+expense.getTransValue());
            }
        }
        catch (ProcessExeption pe) {
            throw pe;
        }
        ArrayList<LocalDate> dates = new ArrayList<LocalDate>();
        ArrayList<Double> values = new ArrayList<Double>();

        for (Map.Entry<LocalDate, Double> entry : maps.entrySet()) {
            dates.add(entry.getKey());
            values.add(entry.getValue());
        }

        chartModel =new ChartModel(dates,values);

        return chartModel;
    }
}
