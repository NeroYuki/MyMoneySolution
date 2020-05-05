package model;

import database.DatabaseBudget;

import java.time.*;

public class User {
    private String username;
    private String email;
    private LocalDate birthday;
    private Budget budget;

    public User(String username, String email, LocalDate birthday, Budget budget) {
        setUsername(username);
        setBirthday(birthday);
        setEmail(email);
        setBudget(budget);
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public void setBudget(Budget budget) {
        this.budget = budget;
    }
}
