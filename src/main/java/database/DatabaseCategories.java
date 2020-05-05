package database;

import exception.DatabaseException;
import model.Category;

import java.sql.*;
import java.util.ArrayList;

public class DatabaseCategories {
    //return if operation is success
    public static boolean addCategories(Category cat) throws DatabaseException {
       try {
           Connection conn = DatabaseManager.getConnection();
           //TODO: actually implement this
           return true;
       }
       catch (Exception e) {
           //if this happen then oh god oh fuck
           throw new DatabaseException(0);
       }
       finally {
           return false;
       }
    }

    //return a list of available income category
    public static ArrayList<Category> getIncomeCategory() throws DatabaseException {
        ArrayList<Category> result = new ArrayList<>();
        try {
            Connection conn = DatabaseManager.getConnection();
            PreparedStatement categoryQuery = conn.prepareCall("SELECT * FROM transCategory WHERE transType = 1");
            ResultSet categoryResult = categoryQuery.executeQuery();
            while (categoryResult.next()) {
                Category categoryEntry = new Category(
                        categoryResult.getString("name"),
                        categoryResult.getString("description"),
                        categoryResult.getString("iconPath"),
                        categoryResult.getInt("transType")
                );
                result.add(categoryEntry);
            }
        }
        catch (DatabaseException de) {
            throw de;
        }
        catch (Exception e) {
            //if this happen then oh god oh fuck
            throw new DatabaseException(0);
        }
        return result;
    }

    public static ArrayList<Category> getExpenseCategory() throws DatabaseException {
        ArrayList<Category> result = new ArrayList<>();
        try {
            Connection conn = DatabaseManager.getConnection();
            PreparedStatement categoryQuery = conn.prepareCall("SELECT * FROM transCategory WHERE transType = 2");
            ResultSet categoryResult = categoryQuery.executeQuery();
            while (categoryResult.next()) {
                Category categoryEntry = new Category(
                        categoryResult.getString("name"),
                        categoryResult.getString("description"),
                        categoryResult.getString("iconPath"),
                        categoryResult.getInt("transType")
                );
                result.add(categoryEntry);
            }
        }
        catch (DatabaseException de) {
            throw de;
        }
        catch (Exception e) {
            //if this happen then oh god oh fuck
            throw new DatabaseException(0);
        }
        return result;
    }

    public static Category getCategoryById(long categoryId) throws DatabaseException {
        try {
            Connection conn = DatabaseManager.getConnection();
            PreparedStatement categoryQuery = conn.prepareCall("SELECT * FROM transCategory WHERE categoryId = ?");
            categoryQuery.setLong(1, categoryId);
            ResultSet categoryResult = categoryQuery.executeQuery();
            if (categoryResult.first()) {
                Category result = new Category(
                        categoryResult.getString("name"),
                        categoryResult.getString("description"),
                        categoryResult.getString("iconPath"),
                        categoryResult.getInt("transType")
                );
                return result;
            }
            else return null;
        }
        catch (DatabaseException de) {
            throw de;
        }
        catch (Exception e) {
            //if this happen then oh god oh fuck
            throw new DatabaseException(0);
        }
    }
}
