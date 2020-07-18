package process;

import database.DatabaseBudget;
import database.DatabaseFinancialGoal;
import exception.DatabaseException;
import exception.ProcessExeption;
import model.Balance;
import model.Budget;
import model.FinancialGoal;
import model.Transaction;

import java.time.LocalDate;
import java.time.temporal.TemporalUnit;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ProcessFinancialGoal {


    public static class objectiveModel {
        public boolean isPassed = false;
        public double progress = 0;
        public double curValue;

        public objectiveModel (double curValue, double goalValue, int type) {
            this.curValue=curValue;
            progress = (curValue / goalValue)*100;
            isPassed = false;
            if (type == 1 || type == 3 && progress > 1) isPassed = true;
            else if (type == 2 && progress < 1) isPassed = true;
        }
    }
    public static void addFinancialGoal(LocalDate startDate, LocalDate expireDate, Balance checkBalance,double threshold,int type,String desc)throws ProcessExeption{
        if(expireDate.isBefore(startDate))throw new ProcessExeption(16);
        if(checkBalance==null) throw new ProcessExeption(17);
        if(threshold <0)throw new ProcessExeption(18);
        FinancialGoal financialGoal=new FinancialGoal(desc,type,threshold,startDate,expireDate,checkBalance);
        try{
            if(DatabaseFinancialGoal.addFinancialGoal(financialGoal,singletonBudget.getInstance().getBudget())) {
                singletonBudget.getInstance().getBudget().getActiveFinancialGoalList().add(financialGoal);
            }
            else throw new ProcessExeption(15);
        }
        catch (DatabaseException de)
        {
            System.out.println(de.getErrorCodeMessage());
        }
    }
    public static void addFinancialGoal(LocalDate startDate, LocalDate expireDate,double threshold,int type,String desc)throws ProcessExeption{
        if(expireDate.isBefore(startDate))throw new ProcessExeption(16);
        if(threshold <0)throw new ProcessExeption(18);
        FinancialGoal financialGoal=new FinancialGoal(desc,type,threshold,startDate,expireDate,null);
        try{
            if(DatabaseFinancialGoal.addFinancialGoal(financialGoal,singletonBudget.getInstance().getBudget())){
                singletonBudget.getInstance().getBudget().getActiveFinancialGoalList().add(financialGoal);
            }
            else throw new ProcessExeption(15);
        }
        catch (DatabaseException de)
        {
            System.out.println(de.getErrorCodeMessage());
        }
    }
    public static ArrayList<FinancialGoal> getFinancialGoals()throws ProcessExeption{
        ArrayList<FinancialGoal> financialGoals=new ArrayList<>();
        Budget budget=singletonBudget.getInstance().getBudget();
        try {
            financialGoals.addAll(DatabaseFinancialGoal.getFinancialGoal(budget.getId()));
        }
        catch (DatabaseException de){
            System.out.println(de.getErrorCodeMessage());
        }
        return financialGoals;
    }
    public static void editFinancialGoal(FinancialGoal financialGoal,double value,String desc)throws ProcessExeption{
        if(financialGoal==null){
            throw new ProcessExeption();
        }
        financialGoal.setThreshold(value);
        financialGoal.setDescription(desc);
        try{
            DatabaseFinancialGoal.updateFinancialGoal(financialGoal);
            singletonBudget.getInstance().setBudget(DatabaseBudget.getBudget(singletonUser.getInstance().getUser()));
        }
        catch (DatabaseException de){
            System.out.println(de.getErrorCodeMessage());
        }
    }
    public static boolean deleteFinanacialGoal(FinancialGoal financialGoal){
        try{
            if(DatabaseFinancialGoal.removeFinancialGoal(financialGoal)==false) return false;
        }
        catch (DatabaseException de)
        {
            System.out.println(de.getErrorCodeMessage());
        }
        return true;
    }
    public static objectiveModel getStatus(FinancialGoal financialGoal)throws ProcessExeption{
        objectiveModel objectiveModel = null;
        LocalDate startDate=financialGoal.getStartDate();
        LocalDate endDay=LocalDate.now();
        ArrayList<Transaction> transactions=ProcessTransaction.getTransactionsInfo(startDate,endDay);
        if(financialGoal.getType()==1){
            double temp=0;
            for (Transaction transaction:transactions) {
                if(transaction.getType()=="Income"){
                    temp+=transaction.getTransValue();
                }
            }
            objectiveModel=new objectiveModel(temp,financialGoal.getThreshold(),1);
        }
        else if(financialGoal.getType()==2){
            double temp=0;
            for (Transaction transaction:transactions) {
                if(transaction.getType()=="Expense"){
                    temp+=transaction.getTransValue();
                }
            }
            objectiveModel=new objectiveModel(temp,financialGoal.getThreshold(),2);
        }
        else if(financialGoal.getType()==3){
            objectiveModel=new objectiveModel(financialGoal.getCheckBalance().getValue(),financialGoal.getThreshold(),3);
        }
        else throw new ProcessExeption();
        return objectiveModel;
    }
    public static long getRemainDays(FinancialGoal financialGoal){
        LocalDate now=LocalDate.now();
        LocalDate end=financialGoal.getExpireDate();
        long result = end.toEpochDay() - now.toEpochDay();
        return result;
    }
    public static String predictResult(FinancialGoal financialGoal) throws ProcessExeption {
        LocalDate endDay = null;
        if (financialGoal.getExpireDate().isBefore(LocalDate.now())) {
            endDay = financialGoal.getExpireDate();
        } else endDay = LocalDate.now();
        Map<LocalDate, Double> maps = new TreeMap<LocalDate, Double>();
        List<LocalDate> localDateList = financialGoal.getStartDate().datesUntil(endDay).collect(Collectors.toList());
        for (LocalDate local : localDateList) {
            maps.put(local, 0.0);
        }
        if (financialGoal.getType() == 3) {
            ArrayList<Transaction> transactions = ProcessTransaction.getTransactionsInfo(financialGoal.getCheckBalance(),financialGoal.getStartDate(), endDay);
            Double bal = financialGoal.getCheckBalance().getValue();
            for (Transaction transaction : transactions) {
                if (transaction.getType() == "Income") bal -= transaction.getTransValue();
                else if (transaction.getType() == "Expense") bal += transaction.getTransValue();
            }
            maps.put(financialGoal.getStartDate(), bal);
            double temp2 = 0;
            for (Transaction transaction : transactions) {
                if (transaction.getType() == "Income") temp2 = transaction.getTransValue();
                else if (transaction.getType() == "Expense") temp2 = -transaction.getTransValue();
                else temp2 = 0;
                maps.put(transaction.getTransDate(), maps.get(transaction.getTransDate()) + temp2);
            }
        } else if (financialGoal.getType() == 1) {
            ArrayList<Transaction> transactions1 = ProcessTransaction.getTransactionsInfo(financialGoal.getStartDate(), endDay);

            for (Transaction transaction : transactions1) {
                if (transaction.getType() == "Income") {
                    maps.put(transaction.getTransDate(), maps.get(transaction.getTransDate()) + transaction.getTransValue());
                }
            }
        } else if (financialGoal.getType() == 2) {
            ArrayList<Transaction> transactions1 = ProcessTransaction.getTransactionsInfo(financialGoal.getStartDate(), endDay);

            for (Transaction transaction : transactions1) {
                if (transaction.getType() == "Expense") {
                    maps.put(transaction.getTransDate(), maps.get(transaction.getTransDate()) + transaction.getTransValue());
                }
            }
        }


//        System.out.println(maps.toString());

        ArrayList<Double> dates = new ArrayList<Double>();
        ArrayList<Double> values = new ArrayList<Double>();

        double temp = 0;

        for (Map.Entry<LocalDate, Double> entry : maps.entrySet()) {
            dates.add(Double.valueOf(entry.getKey().toEpochDay() - financialGoal.getStartDate().toEpochDay()));
//            System.out.println(entry.getKey().toEpochDay() - financialGoal.getStartDate().toEpochDay());
            temp += entry.getValue();
            values.add(temp);
//            System.out.println(temp);
        }
        if (dates.size() < 2) return "";
        helper.LinearRegressionClassifier linearRegressionClassifier = new helper.LinearRegressionClassifier(dates, values);
        double prediction = linearRegressionClassifier.predictValue(Double.valueOf(endDay.toEpochDay() - financialGoal.getStartDate().toEpochDay()));
        System.out.println(prediction);
        if (prediction < financialGoal.getThreshold()) {
            if ((financialGoal.getType() == 1) || (financialGoal.getType() == 3))
                return "Your goal may not achievable due remaining time";
            else if (financialGoal.getType() == 2) {
                return "you likely to spend less than you expected";
            }
        } else if (prediction >= financialGoal.getThreshold()){
            if ((financialGoal.getType() == 1) || (financialGoal.getType() == 3))
                return "You have a good change to get your plan goal";
            else if (financialGoal.getType() == 2) {
                return "you likely to over spend during this plan";
            }
        }
        return "";
    }
}
