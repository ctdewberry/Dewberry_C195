package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class Schedule {
    private static ObservableList<AppointmentModel> allAppointments = FXCollections.observableArrayList();

    public static void addAppointment(AppointmentModel newAppointment){
        allAppointments.add(newAppointment);
    }
}
