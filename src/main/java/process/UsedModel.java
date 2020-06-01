package process;

import model.User;

public class UsedModel {
    private static UsedModel instance = null;

    public User user;

    private UsedModel(){
    }

    public static UsedModel getInstance(){
        if(instance==null)
            instance=new UsedModel();

        return instance;
    }
}

