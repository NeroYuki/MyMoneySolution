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

import java.util.ArrayList;

public class ProcessCategories {
    public static boolean saveCategories(String Name, String FileName, String Info, String Type) throws ProcessExeption {
        int Typeint = 0;

        if (Name.length() >= 255) {
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

    public static boolean updateCategories(String id,String Name, String FileName, String Info, String Type,boolean isUsed) throws ProcessExeption{
        int Typeint=0;
        if(id==null){
            throw new ProcessExeption(8);
        }
        if(Name.length()>=255) {
            throw new ProcessExeption(1);
        }
        if(Info.length()>=1023){
            throw new ProcessExeption(3);
        }
        if(Type=="Income"){
            Typeint=1;
        }
        else if(Type=="Expense")
        {
            Typeint=2;
        }
        else{
            throw new ProcessExeption(4);
        }
        if(FileName ==null) {
            throw new ProcessExeption(2);
        }
        try {
            Category category = new Category(Name, FileName, Info, Typeint);
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
    public static String[] getIncomeCategoriesName() throws DatabaseException{
        try {
            ArrayList<Category> categories = DatabaseCategories.getIncomeCategory();
            String[] categoriesname=new String[categories.size()];
            for(Category cat: categories) {
                categoriesname[categories.indexOf(cat)]=cat.getName();
            }
            return  categoriesname;
        }
        catch (DatabaseException de){
            throw de;
        }
    }
    public static String[] getExpenseCategoriesName() throws DatabaseException{
        try {
            ArrayList<Category> categories = DatabaseCategories.getExpenseCategory();
            String[] categoriesname=new String[categories.size()];
            for(Category cat: categories) {
                categoriesname[categories.indexOf(cat)]=cat.getName();
            }
            return  categoriesname;
        }
        catch (DatabaseException de){
            throw de;
        }
    }
    public static ArrayList<Category> getIncomeCategories()throws DatabaseException{
        try {
            ArrayList<Category> categories = DatabaseCategories.getIncomeCategory();
            return  categories;
        }
        catch (DatabaseException de){
            throw de;
        }
    }

    public static ArrayList<Category> getExpenseCategories()throws DatabaseException{
        try {
            ArrayList<Category> categories = DatabaseCategories.getExpenseCategory();
            return  categories;
        }
        catch (DatabaseException de){
            throw de;
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
            System.out.println(de.getErrorCodeMessage());
        }
        return result;
    }
    public static ArrayList<Balance> getBalances() throws ProcessExeption{
        ArrayList<Balance> re   = new ArrayList<>();
        try{
            re=DatabaseBalance.getBalances((DatabaseBudget.getBudget(singletonUser.getInstance().getUser()).getId()));
        }
        catch (DatabaseException de){
            System.out.println(de.getErrorCodeMessage());
        }
        return re;
    }
    public static ArrayList<Income> getIncome(Balance balance) throws ProcessExeption{
        if (balance==null){
            throw new ProcessExeption(8);
        }
        ArrayList<Income> incomes=new ArrayList<>();
        try{
            incomes=DatabaseTransaction.getIncome(balance.getId());
        }
        catch (DatabaseException De)
        {
            System.out.println(De.getErrorCodeMessage());

        }
        return  incomes;
    }
    public static ArrayList<Expense> getExpense(Balance balance) throws ProcessExeption{
        if (balance==null){
            throw new ProcessExeption(8);
        }
        ArrayList<Expense> Expense=new ArrayList<>();
        try{
            Expense=DatabaseTransaction.getExpense(balance.getId());
        }
        catch (DatabaseException De)
        {
            System.out.println(De.getErrorCodeMessage());
        }
        return  Expense;
    }
    public static ArrayList<Income> getAllIncome() throws ProcessExeption {
        ArrayList<Income> incomes=new ArrayList<>();
        try{
            ArrayList<Balance> balances=ProcessCategories.getBalances();
            for (Balance balance:balances) {
                incomes.addAll(DatabaseTransaction.getIncome(balance.getId()));
            }
        }
        catch (ProcessExeption pe){
            throw pe;
        }
        catch (DatabaseException de){
            System.out.println(de.getErrorCodeMessage());
        }
        return incomes;
    }
    public static ArrayList<Expense> getAllExpense() throws ProcessExeption {
        ArrayList<Expense> Expenses=new ArrayList<>();
        try{
            ArrayList<Balance> balances=ProcessCategories.getBalances();
            for (Balance balance:balances) {
                Expenses.addAll(DatabaseTransaction.getExpense(balance.getId()));
            }
        }
        catch (ProcessExeption pe){
            throw pe;
        }
        catch (DatabaseException de){
            System.out.println(de.getErrorCodeMessage());
        }
        return Expenses;
    }
}
