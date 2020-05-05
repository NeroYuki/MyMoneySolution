package database;

import exception.DatabaseException;
import model.Expense;
import model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DatabaseUser {
    //Get user info from a given username and password
    public static User getUserInfo(String username, String password) throws DatabaseException {
        User result = null;
        try {
            Connection conn = DatabaseManager.getConnection();
            PreparedStatement userQuery = conn.prepareCall("SELECT * FROM loggedUser WHERE username = ? AND password = ?");
            userQuery.setString(1, username);
            userQuery.setString(2, password);
            ResultSet userResult = userQuery.executeQuery();
            if (userResult.first()) {
                result = new User(
                        userResult.getString("username"),
                        userResult.getString("email"),
                        userResult.getDate("birthday").toLocalDate(),
                        DatabaseBudget.getBudget(userResult.getLong("userId"))
                );
            }
            else {
                throw new DatabaseException(2);
            }
        }
        catch (DatabaseException de) {
            throw de;
        }
        catch (Exception e) {
            throw new DatabaseException(0);
        }
        return result;
    }
}
