package process;

import model.Budget;

public class singletonBudget {
    private static singletonBudget instance = null;

    private Budget budget;

    private singletonBudget(){
    }

    public Budget getBudget() {
        return budget;
    }
    public void setBudget(Budget budget){
        this.budget=budget;
    }

    public static singletonBudget getInstance(){
        if(instance==null)
            instance=new singletonBudget();
        return instance;
    }
}
