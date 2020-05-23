package database;

import exception.DatabaseException;
import helper.UUIDHelper;
import model.Category;

import java.sql.*;
import java.util.ArrayList;

public class DatabaseCategories {
    //return if operation is success
    public static boolean addCategories(Category cat) throws DatabaseException {
        try {
            Connection conn = DatabaseManager.getConnection();
            PreparedStatement createCall = conn.prepareCall("INSERT INTO transCategory VALUES (?, ?, ?, ?, ?)");
            if (cat.getId().equals("")) cat.setId(UUIDHelper.newUUIDString());
            else throw new DatabaseException(9);

            createCall.setString(1, cat.getId());
            createCall.setLong(2, cat.getType());
            createCall.setString(3, cat.getName());
            createCall.setString(4, cat.getDescription());
            createCall.setString(5, cat.getIconPath());
            createCall.execute();

            int result = createCall.getUpdateCount();
            if (result == 0) throw new DatabaseException(9);
        }
        catch (DatabaseException de) {
            throw de;
        }
        catch (Exception e) {
            throw new DatabaseException(0);
        }
        return true;
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
                        categoryResult.getString("transCategoryId"),
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
                        categoryResult.getString("transCategoryId"),
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

    public static Category getCategoryById(String categoryId) throws DatabaseException {
        try {
            Connection conn = DatabaseManager.getConnection();
            PreparedStatement categoryQuery = conn.prepareCall("SELECT * FROM transCategory WHERE transCategoryId = ?");
            categoryQuery.setString(1, categoryId);
            ResultSet categoryResult = categoryQuery.executeQuery();
            if (categoryResult.first()) {
                Category result = new Category(
                        categoryResult.getString("transCategoryId"),
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
