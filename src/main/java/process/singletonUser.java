package process;

import model.User;

public class singletonUser {
    private static singletonUser instance = null;

    private User user;

    private singletonUser(){
    }

    public User getUser() {
        return user;
    }
    public void setUser(User user){
        this.user=user;
    }

    public static singletonUser getInstance(){
        if(instance==null)
            instance=new singletonUser();
        return instance;
    }
}

