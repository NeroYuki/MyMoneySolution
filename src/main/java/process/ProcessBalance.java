package process;

import database.DatabaseBalance;
import database.DatabaseBudget;
import exception.DatabaseException;
import exception.ProcessExeption;
import model.Balance;
import model.Budget;
import model.User;

import javax.xml.crypto.Data;
import java.util.ArrayList;

public class ProcessBalance {

    public  static void addBalance(String name,String desc,double value)throws ProcessExeption{
        //TODO : add check x

        Balance balance =new Balance(name,desc,value);
        Budget budget=singletonBudget.getInstance().getBudget();
        try{
            DatabaseBalance.addBalance(balance, budget);
            budget.getBalanceList().add(balance);
            singletonBudget.getInstance().setBudget(budget);
            }
        catch (DatabaseException de){
            System.out.println(de.getErrorCodeMessage());
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }
    public static ArrayList<Balance> getBalances() throws ProcessExeption{
        //TODO: checking
        User user=singletonUser.getInstance().getUser();
        ArrayList<Balance> balances=new ArrayList<>();
        try{
            balances =DatabaseBalance.getBalances(DatabaseBudget.getBudget(user).getId());
        }
        catch (DatabaseException de)
        {
            System.out.println(de.getErrorCodeMessage());
        }
        return balances;
    }
}
