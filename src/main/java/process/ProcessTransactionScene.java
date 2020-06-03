package process;

import database.*;
import exception.DatabaseException;
import model.Budget;
import model.Category;
import model.Transaction;

import java.util.ArrayList;


public class ProcessTransactionScene {
    public static ArrayList<Transaction> getTransactionsInfo() throws DatabaseException{
        try {
            UsedModel usedModel = UsedModel.getInstance();
            Budget budget = usedModel.user.getBudget();
            ArrayList<Transaction> transaction = DatabaseTransaction.getTransaction(budget.getId());
            return transaction;
        }
        catch(DatabaseException de){
            throw de;
        }
    }
}
