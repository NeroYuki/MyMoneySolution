package database;

import exception.DatabaseException;
import helper.UUIDHelper;
import model.Budget;
import model.Category;
import model.Transaction;

import java.sql.*;
import java.util.ArrayList;

public class DatabaseCategories {
    //return if operation is success
    public static boolean addCategories(Category cat) throws DatabaseException {
        try {
            Connection conn = DatabaseManager.getConnection();
            PreparedStatement createCall = conn.prepareCall("INSERT INTO transCategory VALUES (?, ?, ?, ?, ?, TRUE)");
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
                        categoryResult.getInt("transType"),
                        categoryResult.getBoolean("isUsed")
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
                        categoryResult.getInt("transType"),
                        categoryResult.getBoolean("isUsed")
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
                        categoryResult.getInt("transType"),
                        categoryResult.getBoolean("isUsed")
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

    public static boolean removeCategory(Category cat) throws DatabaseException {
        try {
            Connection conn = DatabaseManager.getConnection();
            PreparedStatement removeCall = conn.prepareCall("DELETE FROM transCategory WHERE transCategoryId = ?");
            if (cat.getId().equals("")) throw new DatabaseException(12);

            for (Transaction trans : DatabaseTransaction.getTransactionByCategory(cat.getId())) {
                DatabaseTransaction.removeTransaction(trans);
            }

            removeCall.setString(1, cat.getId());
            removeCall.execute();
            int result = removeCall.getUpdateCount();
            if (result == 0) throw new DatabaseException(12);
        }
        catch (DatabaseException de) {
            throw de;
        }
        catch (Exception e) {
            throw new DatabaseException(0);
        }
        return true;
    }

    public static boolean updateCategory(Category cat) throws DatabaseException {
        try {
            Connection conn = DatabaseManager.getConnection();
            PreparedStatement updateCall = conn.prepareCall(
                    "UPDATE transCategory " +
                            "SET name = ?, " +
                            "description = ?, " +
                            "iconPath = ? " +
                            "WHERE transCategoryId = ?"
            );
            if (cat.getId().equals("")) throw new DatabaseException(18);
            updateCall.setString(1, cat.getName());
            updateCall.setString(2, cat.getDescription());
            updateCall.setString(3, cat.getIconPath());
            updateCall.setString(4, cat.getId());

            updateCall.execute();
            int result = updateCall.getUpdateCount();
            if (result == 0) throw new DatabaseException(18);
        }
        catch (DatabaseException de) {
            throw de;
        }
        catch (Exception e) {
            throw new DatabaseException(0);
        }
        return true;
    }

    public static boolean softRemoveCategory(Category cat) throws DatabaseException {
        try {
            Connection conn = DatabaseManager.getConnection();
            PreparedStatement removeCall = conn.prepareCall(
                    "UPDATE transCategory " +
                            "SET isAvailable = FALSE " +
                            "WHERE transCategoryId = ?"
            );
            if (cat.getId().equals("")) throw new DatabaseException(12);

            removeCall.setString(1, cat.getId());
            removeCall.execute();
            int result = removeCall.getUpdateCount();
            if (result == 0) throw new DatabaseException(12);
        }
        catch (DatabaseException de) {
            throw de;
        }
        catch (Exception e) {
            throw new DatabaseException(0);
        }
        return true;
    }
}
