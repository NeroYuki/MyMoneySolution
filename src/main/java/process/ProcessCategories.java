package process;

import database.DatabaseBalance;
import database.DatabaseBudget;
import database.DatabaseCategories;
import database.DatabaseTransaction;
import exception.DatabaseException;
import exception.ProcessExeption;
import model.Balance;
import model.Category;
import model.Expense;
import model.Income;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class ProcessCategories {
    public static class CatModel{
        public String key;
        public Double value;
        public CatModel(String a,double b){
            key=a;
            value=b;
        }
    }


    public static boolean saveCategories(String Name, String FileName, String Info, String Type) throws ProcessExeption {
        int Typeint = 0;

        if (Name.length() >= 255 || Name.length() <= 0) {
            throw new ProcessExeption(1);
        }
        if (Info.length() >= 1023) {
            throw new ProcessExeption(3);
        }
        if (Type == "Income") {
            Typeint = 1;
        } else if (Type == "Expense") {
            Typeint = 2;
        } else {
            throw new ProcessExeption(4);
        }
        if (FileName == null) {
            throw new ProcessExeption(2);
        }
        try {
            Category category = new Category(Name, Info,FileName , Typeint);
            DatabaseCategories.addCategories(category);
        }
        catch (DatabaseException De) {
            System.out.println(De.getErrorCodeMessage());
        }
        catch (Exception e) {
            throw new ProcessExeption(0);
        }
        return true;
    }

    public static boolean updateCategories(String id,String Name, String FileName, String Info, int Type,boolean isUsed) throws ProcessExeption{
        int Typeint=0;
        if(id==null){
            throw new ProcessExeption(8);
        }
        if(Name.length()>=255 || Name.length() <= 0) {
            throw new ProcessExeption(1);
        }
        if(Info.length()>=1023){
            throw new ProcessExeption(3);
        }
        if(FileName ==null) {
            throw new ProcessExeption(2);
        }
        try {
            Category category = new Category(id,Name, Info,FileName, Typeint,isUsed);
            DatabaseCategories.updateCategory(category);
        }

        catch (DatabaseException De){
            System.out.println(De.getErrorCodeMessage());
        }
        catch (Exception e){
            throw new ProcessExeption(0);
        }
        return  true;
    }
    public static void deleleCategory(Category category)throws ProcessExeption{
        if (category==null) throw new ProcessExeption(8);
        try{
            DatabaseCategories.removeCategory(category);
        }
        catch (DatabaseException De){
                System.out.println(De.getErrorCodeMessage());
        }
    }
    public static void softDeleteCategory(Category category)throws ProcessExeption{
        if(category==null) throw new ProcessExeption(9);
        try {
            DatabaseCategories.softRemoveCategory(category);
        }
        catch (DatabaseException de){
            System.out.println(de.getErrorCodeMessage());
        }
    }
    public static String[] getIncomeCategoriesName() throws ProcessExeption{
        try {
            ArrayList<Category> categories = DatabaseCategories.getIncomeCategory();
            String[] categoriesname=new String[categories.size()];
            for(Category cat: categories) {
                categoriesname[categories.indexOf(cat)]=cat.getName();
            }
            return  categoriesname;
        }
        catch (DatabaseException de){
            throw new ProcessExeption();
        }
    }
    public static String[] getExpenseCategoriesName() throws ProcessExeption{
        try {
            ArrayList<Category> categories = DatabaseCategories.getExpenseCategory();
            String[] categoriesname=new String[categories.size()];
            for(Category cat: categories) {
                categoriesname[categories.indexOf(cat)]=cat.getName();
            }
            return  categoriesname;
        }
        catch (DatabaseException de){
            throw new ProcessExeption();
        }
    }
    public static ArrayList<Category> getIncomeCategories()throws ProcessExeption{
        try {
            ArrayList<Category> categories = DatabaseCategories.getIncomeCategory();
            return  categories;
        }
        catch (DatabaseException de){
            throw new ProcessExeption();
        }
    }

    public static ArrayList<Category> getExpenseCategories()throws ProcessExeption{
        try {
            ArrayList<Category> categories = DatabaseCategories.getExpenseCategory();
            return  categories;
        }
        catch (DatabaseException de){
            throw new ProcessExeption();
        }
        catch (Exception e){
            throw e;
        }
    }
    public static void deleteCategories(String index)throws ProcessExeption{
        if(index==null){
            throw new ProcessExeption(0);
        }
        try {
            Category category =DatabaseCategories.getCategoryById(index);
            DatabaseCategories.removeCategory(category);
        }
        catch (DatabaseException de){
            throw new ProcessExeption(0);
        }
    }

    public static ArrayList<Balance> getBalanceWithTotal() throws ProcessExeption{
        Balance total;
        ArrayList<Balance> result=new ArrayList<>(),temp;
        double value=0;
        try{
            temp = DatabaseBalance.getBalances(DatabaseBudget.getBudget(singletonUser.getInstance().getUser()).getId());
            for(int i=0;i<temp.size();i++){
                value=value +temp.get(i).getValue();
            }
            total=new Balance("Total","",value);
            result.add(total);
            result.addAll(temp);
        }
        catch (DatabaseException de){
            throw new ProcessExeption();
        }
        return result;
    }
    public static ArrayList<Balance> getBalances() throws ProcessExeption{
        ArrayList<Balance> re   = new ArrayList<>();
        try{
            re=DatabaseBalance.getBalances((DatabaseBudget.getBudget(singletonUser.getInstance().getUser()).getId()));
        }
        catch (DatabaseException de){
            throw new ProcessExeption();
        }
        return re;
    }


    public static ArrayList<CatModel> getIncomePineChart(int a,int b)throws ProcessExeption{
        LocalDate now=LocalDate.now().minusDays(a);
        LocalDate past=now.minusDays(b);
        ArrayList<Category> categories= getIncomeCategories();
        ArrayList<CatModel> catModels=new ArrayList<>();
        ArrayList<Income> incomes=ProcessTransaction.getAllIncome(past,now);


        Map<String, Double> maps = new TreeMap<String, Double>();
        for (Category category : categories) {
            maps.put(category.getName(), 0.0);
        }
        for (Income income:incomes) {
            maps.put(income.getCategoryName(),maps.get(income.getCategoryName())+income.getTransValue());
        }
        for (Map.Entry<String, Double> entry : maps.entrySet()) {
            catModels.add(new CatModel(entry.getKey(),entry.getValue()));
        }
        return catModels;
    }

    public static ArrayList<CatModel> getIncomePineChart()throws ProcessExeption{
        ArrayList<Category> categories= getIncomeCategories();
        ArrayList<CatModel> catModels=new ArrayList<>();
        ArrayList<Income> incomes=ProcessTransaction.getAllIncome();

        Map<String, Double> maps = new TreeMap<String, Double>();
        for (Category category : categories) {
            maps.put(category.getName(), 0.0);
        }
        for (Income income:incomes) {
            maps.put(income.getCategoryName(),maps.get(income.getCategoryName())+income.getTransValue());
        }
        for (Map.Entry<String, Double> entry : maps.entrySet()) {
            catModels.add(new CatModel(entry.getKey(),entry.getValue()));
        }
        return catModels;
    }


    public static ArrayList<CatModel> getExpensePineChart(int a,int b)throws ProcessExeption{
        LocalDate now=LocalDate.now().minusDays(a);
        LocalDate past=now.minusDays(b);
        ArrayList<Category> categories= getExpenseCategories();
        ArrayList<CatModel> catModels=new ArrayList<>();
        ArrayList<Expense> expenses=ProcessTransaction.getAllExpense(past,now);


        Map<String, Double> maps = new TreeMap<String, Double>();
        for (Category category : categories) {
            maps.put(category.getName(), 0.0);
        }
        for (Expense expense:expenses) {
            maps.put(expense.getCategoryName(),maps.get(expense.getCategoryName())+expense.getTransValue());
        }
        for (Map.Entry<String, Double> entry : maps.entrySet()) {
            catModels.add(new CatModel(entry.getKey(),entry.getValue()));
        }
        return catModels;
    }
    public static ArrayList<CatModel> getExpensePineChart()throws ProcessExeption{
        ArrayList<Category> categories= getExpenseCategories();
        ArrayList<CatModel> catModels=new ArrayList<>();
        ArrayList<Expense> expenses=ProcessTransaction.getAllExpense();


        Map<String, Double> maps = new TreeMap<String, Double>();
        for (Category category : categories) {
            maps.put(category.getName(), 0.0);
        }
        for (Expense expense:expenses) {
            maps.put(expense.getCategoryName(),maps.get(expense.getCategoryName())+expense.getTransValue());
        }
        for (Map.Entry<String, Double> entry : maps.entrySet()) {
            catModels.add(new CatModel(entry.getKey(),entry.getValue()));
        }
        return catModels;
    }
    public static double getSum(ArrayList<CatModel> catModels){
        double sum=0;
        for (CatModel catModel:catModels
             ) {
            sum+=catModel.value;
        }
        return sum;
    }
    public static double getAllIncomeSum() throws ProcessExeption {
        ArrayList<CatModel> catModels=getIncomePineChart();
        double sum=0;
        for (CatModel catModel:catModels
        ) {
            sum+=catModel.value;
        }
        return sum;
    }
    public static double getAllExpenseSum() throws ProcessExeption {
        ArrayList<CatModel> catModels=getExpensePineChart();
        double sum=0;
        for (CatModel catModel:catModels
        ) {
            sum+=catModel.value;
        }
        return sum;
    }






}
