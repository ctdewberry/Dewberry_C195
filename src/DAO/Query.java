package DAO;

import DAO.DBConnection;

import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Query {

    public static void testAccess (){
        DBConnection.startConnection();

    }

}
