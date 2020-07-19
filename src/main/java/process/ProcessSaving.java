package process;

import database.DatabaseBudget;
import database.DatabaseLoan;
import database.DatabaseSaving;
import exception.DatabaseException;
import exception.ProcessExeption;
import helper.IntervalEnum;
import model.Saving;
import model.Transaction;

import java.time.LocalDate;
import java.util.ArrayList;

public class ProcessSaving {
    public static String addSaving(String Name, String desc, double interset, int activeTime, IntervalEnum.INTERVAL interestInterval )throws ProcessExeption {
        if (Name.length() >= 255) {
            throw new ProcessExeption(1);
        }
        if (desc.length() >= 1023) {
            throw new ProcessExeption(3);
        }
        if(activeTime<=0){throw new ProcessExeption();}
        if(interset<0){throw new ProcessExeption();}
        if(interestInterval==null) {throw new ProcessExeption();}
        try {
            Saving saving=new Saving(Name,desc,interset,LocalDate.now(),activeTime,interestInterval,0,0);
            String id =DatabaseSaving.addSaving(saving,singletonBudget.getInstance().getBudget());
            saving.setId(id);
            singletonBudget.getInstance().getBudget().getActiveSavingList().add(saving);
            return id;
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
    public static void editSaving(Saving saving)throws ProcessExeption{
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
    public static Saving getSaving(String id) throws ProcessExeption{
        ArrayList<Saving> savings=getSaving();
        for (Saving saving:savings
             ) {
            if(saving.getId().equals(id)) return saving;
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
    public static void deleteSaving(Saving saving)throws ProcessExeption{
        if (saving==null) throw new ProcessExeption();
        try {
            DatabaseSaving.removeSaving(saving);
        }
        catch (DatabaseException de){
            de.printStackTrace();
        }
    }
    public static void applySaving(String id, Double value) throws ProcessExeption {
        Saving saving=getSaving(id);
        saving.setCurrentValue(value);
        editSaving(saving);
    }
    public static boolean depositSaving(String id, Double value) throws ProcessExeption {
        Saving saving=getSaving(id);
        saving.setCurrentValue(saving.getCurrentValue()+value);
        editSaving(saving);
        return true;
    }
    public static boolean withdrawSaving(String id, Double value) throws ProcessExeption {
        Saving saving=getSaving(id);
        if(saving.getCurrentValue()<value) return false;
        saving.setCurrentValue(saving.getCurrentValue()-value);
        editSaving(saving);
        return true;
    }

    public static ArrayList<Saving> savingUpdating()throws  ProcessExeption{
        ArrayList<Saving> result=new ArrayList<>();
        ArrayList<Saving> savings=getSaving();
        LocalDate now=LocalDate.now();
        for(Saving saving:savings){
            boolean saved=false;
            double interest=saving.getInterestRate();
            double current =saving.getCurrentValue();
            LocalDate creationDay=saving.getCreationDate();
            int interval=saving.getInterestInterval().intervalToDays();
            LocalDate lastCheck =saving.getLastCheckedDate();
            LocalDate expireDay=creationDay.plusDays(saving.getActiveTimeSpan());

            if(expireDay.isBefore(now)) {
                now = expireDay;
                saved=true;
            }
            if(saving.getCurrentValue()==0&&saved)
            {
                ProcessSaving.deactivate(saving);
            }
            else {
//            int pastIntervals=(int)(lastCheck.toEpochDay()- creationDay.toEpochDay())/interval;
//            int willIntervals=(int)(now.toEpochDay()- creationDay.toEpochDay())/interval;
                int numberOfInterval = (int) (now.toEpochDay() - creationDay.toEpochDay()) / interval - (int) (lastCheck.toEpochDay() - creationDay.toEpochDay()) / interval;
                for (int i = 0; i < numberOfInterval; i++) {
                    current = current * (1 + interest / 100);
                }
                saving.setCurrentValue(current);
                saving.setLastCheckedDate(now);
                editSaving(saving);
                if (saved) result.add(saving);
            }
        }
        return result;
    }
}
