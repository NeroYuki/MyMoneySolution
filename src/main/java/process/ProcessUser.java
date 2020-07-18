package process;

import database.DatabaseBudget;
import database.DatabaseUser;
import exception.DatabaseException;
import exception.ProcessExeption;
import model.Budget;
import model.User;

import java.time.LocalDate;

public class ProcessUser {
    public static boolean registerUser(String username, String password,String email, LocalDate birthday) throws ProcessExeption {

        Budget budget=null;
        if (username.length() >= 20|| username.length()<=1) {
            throw new ProcessExeption(5);
        }
        if (password.length() >= 20|| password.length()<=1) {
            throw new ProcessExeption(5);
        }
        if(email==null){
            throw new ProcessExeption(5);
        }
        if (birthday == null) {
            throw new ProcessExeption(5);
        }
        try {
            User user=new User(username,password,email,birthday,budget);
            DatabaseUser.registerUser(user);
        }
        catch (DatabaseException De) {
            System.out.println(De.getErrorCodeMessage());
        }
        catch (Exception e) {
            throw new ProcessExeption(0);
        }
        return true;
    }
    public static boolean login(String username, String password)throws ProcessExeption{
        if(username==null) throw new ProcessExeption(6);
        if(password==null) throw new ProcessExeption(6);
        try {
            User user=DatabaseUser.getUserInfo(username,password);
            Budget budget= DatabaseBudget.getBudget(user);
            singletonUser.getInstance().setUser(user);
            singletonBudget.getInstance().setBudget(budget);
        }
        catch (DatabaseException de)
        {
            throw new ProcessExeption(19);

        }
        catch (Exception e)
        {
            System.out.println("1");
            throw new ProcessExeption(0);
        }
        return true;
    }
    public static String getPassword(String username,String email,LocalDate localDate)throws ProcessExeption{
        if(username ==null)throw  new ProcessExeption(20);
        if(email == null)throw  new ProcessExeption(20);
        if(localDate==null)throw  new ProcessExeption(20);
        String res = "";
        try{
            res = DatabaseUser.getUserPassword(username,email,localDate);
        }
        catch (DatabaseException de){
            throw new ProcessExeption(20);
        }
        return res;
    }
}
