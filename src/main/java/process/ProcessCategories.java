package process;

import database.*;
import exception.DatabaseException;
import model.Category;

public class ProcessCategories {
    public static boolean saveCategories(String Name,String FileName,String Info,String Type){

        int Typeint=0;
        if(Name.length()>=255) {
            System.out.println("co loi xay ra name");
            return false;
        }
        if(Info.length()>=1023){
            System.out.println(("co loi xay ra desc"));
            return false;
        }
        if(Type=="Income"){
            Typeint=1;
        }
        else if(Type=="Expense")
        {
            Typeint=2;
        }
        else{
            System.out.println("co loi xay ra tai type");
            return false;
        }
        if(FileName ==null){
            System.out.println(("co loi xay ra tai filename"));
            return false;
        }
        Category category=new Category(Name,FileName,Info,Typeint);
        try {
            DatabaseCategories.addCategories(category);
            return  true;
            //   DatabaseCategories.updateCategory(category);
            //  return true;
            // DatabaseCategories.saveDataCategories(category);
            // return true;
        }
        catch (DatabaseException De){
            System.out.println("chuong trinh loi khi dua data vao database");
            return false;
        }
    }
}
