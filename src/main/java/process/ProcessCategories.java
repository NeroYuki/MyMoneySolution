package process;

import database.*;
import exception.DatabaseException;
import exception.ProcessExeption;
import model.Category;

import java.util.ArrayList;

public class ProcessCategories {
    public static boolean saveCategories(String Name, String FileName, String Info, String Type) throws ProcessExeption {
        try {
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
            Category category = new Category(Name, FileName, Info, Typeint);
            DatabaseCategories.addCategories(category);
        }
        catch (ProcessExeption pe) {
            throw pe;
        }
        catch (DatabaseException De) {
            System.out.println(De.getErrorCodeMessage());
        }
        catch (Exception e) {
            throw new ProcessExeption(0);
        }
        return true;
    }

    public static boolean updateCategories(String Name,String FileName,String Info,String Type) throws ProcessExeption{
        try{
            int Typeint=0;
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
            if(FileName ==null){
                throw new ProcessExeption(2);
            }
            Category category=new Category(Name,FileName,Info,Typeint);
            DatabaseCategories.updateCategory(category);
        }
        catch (ProcessExeption pe){
            throw pe;
        }
        catch (DatabaseException De){
            System.out.println(De.getErrorCodeMessage());
        }
        catch (Exception e){
            throw new ProcessExeption(0);
        }
        return  true;
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

}
