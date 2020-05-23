package model;

import database.DatabaseBudget;

import java.time.*;

public class User {
    private String id = "";
    private String username;
    //TODO: encrypt the password?
    private String password;
    private String email;
    private LocalDate birthday;
    private Budget budget;

    public User(String username, String password, String email, LocalDate birthday, Budget budget) {
        setUsername(username);
        //storing password as plain text is the most insecured thing ever but because we dont have fancy hash-and-salt function yet, alas
        setPassword(password);
        setBirthday(birthday);
        setEmail(email);
        setBudget(budget);
    }

    public User(String id, String username, String password, String email, LocalDate birthday, Budget budget) {
        this(username, password, email, birthday, budget);
        setId(id);
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public LocalDate getBirthday() {
        return birthday;
    }
}
