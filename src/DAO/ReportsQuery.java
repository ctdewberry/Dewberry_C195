package DAO;

import DAO.DBConnection;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;

public class ReportsQuery {

    public static ObservableList ReportMonthChoices(){
        ObservableList<String> ReportMonthOptions = FXCollections.observableArrayList();
        try {
            String sql = "select DISTINCT DATE_FORMAT (Start, '%m-%Y') from appointments;";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                ReportMonthOptions.add(rs.getString("DATE_FORMAT (Start, '%m-%Y')"));
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return ReportMonthOptions;
    }

    public static ObservableList<String> ReportTypeChoices(){
        ObservableList<String> ReportTypeOptions = FXCollections.observableArrayList();
        try {
            String sql = "select Distinct Type from appointments;";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                ReportTypeOptions.add(rs.getString("Type"));
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return ReportTypeOptions;
    }

}
