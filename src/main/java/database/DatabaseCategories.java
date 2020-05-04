package database;

import model.Category;

import java.sql.*;
import java.util.ArrayList;

public class DatabaseCategories {
    public static void addCategories(Category cat) {
       Connection conn = PersonalDatabase.getConnection();

       try {

       } catch (Exception e) {
           System.out.println(e.getMessage());
       }
    }

    public static ArrayList<Category> getIncomeCategory() {
        Connection conn = PersonalDatabase.getConnection();
        ArrayList<Category> result = new ArrayList<>();
        try {

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return result;
    }
}
