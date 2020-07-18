package process;

import database.DatabaseBudget;
import database.DatabaseSaving;
import exception.DatabaseException;
import exception.ProcessExeption;
import helper.IntervalEnum;
import model.Saving;

import java.time.LocalDate;
import java.util.ArrayList;

public class ProcessSaving {
    public static void addSaving(String Name, String desc, double interset, int activeTime, IntervalEnum.INTERVAL interestInterval,double baseValue)throws ProcessExeption {
        if (Name.length() >= 255) {
            throw new ProcessExeption(1);
        }
        if (desc.length() >= 1023) {
            throw new ProcessExeption(3);
        }
        if(activeTime<=0){throw new ProcessExeption();}
        if(interset<0){throw new ProcessExeption();}
        if(interestInterval==null) {throw new ProcessExeption();}
        if(baseValue<0){throw new ProcessExeption();}
        try {
            Saving saving=new Saving(Name,desc,interset,null,activeTime,interestInterval,baseValue,baseValue);
            DatabaseSaving.addSaving(saving,singletonBudget.getInstance().getBudget());
            singletonBudget.getInstance().getBudget().getActiveSavingList().add(saving);
        }
        catch (DatabaseException de){
            de.printStackTrace();
            throw new ProcessExeption();
        }
    }
    public static void editSaving(Saving saving,String name,String desc)throws ProcessExeption{
        if (name.length() >= 255) {
            throw new ProcessExeption(1);
        }
        if (desc.length() >= 1023) {
            throw new ProcessExeption(3);
        }

        saving.setName(name);
        saving.setDescription(desc);
        try {
            DatabaseSaving.updateSaving(saving);
            singletonBudget.getInstance().setBudget(DatabaseBudget.getBudget(singletonUser.getInstance().getUser()));
        }
        catch (DatabaseException de){
            de.printStackTrace();
        }

    }
    public static ArrayList<Saving> getSaving()throws ProcessExeption {
        try {
            return DatabaseSaving.getActiveSaving(singletonBudget.getInstance().getBudget().getId());
        }
        catch (DatabaseException de)
        {
            de.printStackTrace();
        }
        return null;
    }
    public static boolean deactivate(Saving saving)throws ProcessExeption{
        if (saving==null) throw new ProcessExeption();
        try {
            return DatabaseSaving.deactivateSaving(saving);
        }
        catch (DatabaseException de){
            de.printStackTrace();
        }
        return false;
    }
}
