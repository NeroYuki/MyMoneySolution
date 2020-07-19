package process;

import database.DatabaseBalance;
import database.DatabaseBudget;
import exception.DatabaseException;
import exception.ProcessExeption;
import model.Balance;
import model.Budget;
import model.User;

import java.util.ArrayList;

public class ProcessBalance {

    public static void addBalance(String name, String desc, double value) throws ProcessExeption {
        //TODO : add check x
        if (name.length() >= 255 || name.length() <= 0) throw new ProcessExeption(12);
        if (desc.length() >= 1023) throw new ProcessExeption(13);
        Balance balance = new Balance(name, desc, value);
        Budget budget = singletonBudget.getInstance().getBudget();
        try {
            DatabaseBalance.addBalance(balance, budget);
            budget.getBalanceList().add(balance);
            singletonBudget.getInstance().setBudget(budget);
        } catch (DatabaseException de) {
            System.out.println(de.getErrorCodeMessage());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static ArrayList<Balance> getBalances() throws ProcessExeption {
        //TODO: checking
        User user = singletonUser.getInstance().getUser();
        ArrayList<Balance> balances = new ArrayList<>();
        try {
            balances = DatabaseBalance.getBalances(DatabaseBudget.getBudget(user).getId());
        } catch (DatabaseException de) {
            System.out.println(de.getErrorCodeMessage());
        }
        return balances;
    }

    public static boolean removeBalace(Balance balance) throws ProcessExeption {
        if (balance == null) throw new ProcessExeption(14);
        try {
            DatabaseBalance.softRemoveBalance(balance);
        } catch (DatabaseException de) {
            System.out.println(de.getErrorCodeMessage());
        }
        return true;
    }

    public static boolean updateBalance(String id, String name, String desc, double value) throws ProcessExeption {
        if (id == null) {
            throw new ProcessExeption(8);
        }
        if (name.length() >= 255 || name.length() <= 0) throw new ProcessExeption(12);
        if (desc.length() >= 1023) throw new ProcessExeption(13);
        try {
            Balance balance = new Balance(id, name, desc, value);
            DatabaseBalance.updateBalance(balance);
        } catch (DatabaseException De) {
            System.out.println(De.getErrorCodeMessage());
        } catch (Exception e) {
            throw new ProcessExeption(0);
        }
        return true;
    }

    public static double getSum() throws ProcessExeption {
        ArrayList<Balance> balances = getBalances();
        Double sum = 0.0;
        for (Balance balance : balances
        ) {
            sum += balance.getValue();
        }
        return sum;
    }

    public static double getBalanceValue(String balanceId) throws ProcessExeption {
        try {
            Balance balance =DatabaseBalance.getBalanceById(balanceId);
            return balance.getValue();
        }
        catch (DatabaseException de){
            de.printStackTrace();
        }
        return 0;
    }
}
