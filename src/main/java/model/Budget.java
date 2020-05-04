package model;

import java.util.ArrayList;

public class Budget {
    private ArrayList<Balance> balanceList;
    //should we only fetch active saving and loan instance?
    private ArrayList<Saving> activeSavingList;
    private ArrayList<Loan> activeLoanList;

    public Budget(ArrayList<Balance> balanceList, ArrayList<Saving> savingList, ArrayList<Loan> loanList) {
        setBalanceList(balanceList);
        setActiveSavingList(savingList);
        setActiveLoanList(loanList);
    }

    void setActiveLoanList(ArrayList<Loan> activeLoanList) {
        this.activeLoanList = activeLoanList;
    }

    void setActiveSavingList(ArrayList<Saving> activeSavingList) {
        this.activeSavingList = activeSavingList;
    }

    void setBalanceList(ArrayList<Balance> balanceList) {
        this.balanceList = balanceList;
    }
}
