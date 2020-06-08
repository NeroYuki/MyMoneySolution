package process;

import model.User;

public class singletonUser {
    private static singletonUser instance = null;

    public User user;

    private singletonUser(){
    }

    public static singletonUser getInstance(){
        if(instance==null)
            instance=new singletonUser();

        return instance;
    }
}

