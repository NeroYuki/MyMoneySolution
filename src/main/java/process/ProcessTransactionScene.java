package process;

import database.*;
import exception.DatabaseException;
import exception.ProcessExeption;
import model.Balance;
import model.Budget;
import model.Category;
import model.Transaction;

import java.util.ArrayList;


public class ProcessTransactionScene {
    public static ArrayList<Transaction> getTransactionsInfo() throws ProcessExeption{
        try {
            singletonUser usedModel = singletonUser.getInstance();
            Budget budget = usedModel.user.getBudget();
            ArrayList<Balance> balances=DatabaseBalance.getBalances(budget.getId());
            ArrayList<Transaction> transaction = new ArrayList<>();
            for(int i=0;i<balances.size();i++)
            {
                transaction.addAll(DatabaseTransaction.getTransaction(balances.get(i).getId()));
            }
            return transaction;
        }
        catch(DatabaseException de){
            throw new ProcessExeption(0);
        }
    }
}
