package process;

import database.DatabaseCategories;
import exception.DatabaseException;
import model.Category;

import java.util.ArrayList;

public class ProcessCategoriesScene {
    public static String[] getIncomeCategories() throws DatabaseException{
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
        catch (Exception e){
            throw e;
        }
    }
    public static String[] getExpenseCategories() throws DatabaseException{
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
        catch (Exception e){
            throw e;
        }
    }
}
