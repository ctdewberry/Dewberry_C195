# Dewberry_C195

README.TXT

Title:
Multi-client scheduler
AKA C195 Project Submission

Purpose of the application:
To allow users to schedule and keep track of appointments and customers, connecting those customers with various 3rd party contacts on a multinational scale.

Author:
Caleb Dewberry

Contact information:
Student ID: #001222626
cdewbe3@wgu.edu

Application version:
Version 1.1

Date:
12/02/2021

IDE Version:
IntelliJ IDEA 2021.1.1 (Community Edition)
Build #IC-211.7142.45, built on April 30, 2021
Runtime version: 11.0.10+9-b1341.41 amd64

Full JDK of version 11:
11.0.11

JavaFX version compatible with JDK22:
JavaFX-SDK-11.0.2

Directions on how to run program:
Compile program by opening project in IntelliJ and selecting 'Run'
From there you will get a login window requesting a username and password
Enter credentials: test, test or admin, admin for username and password and click log in.
You will be notified if your credentials are invalid and please keep in mind, all log in attempt will be recorded.
The app supports English and French on the login page.
Once you are logged in, you will be notified if you (based on your username) have any appointments within 15 minutes, and even if not, you will see information about any future appointments that are scheduled for you.
On the left, you will see three options: Customers, Appointments, and Reports.
Selecting Customers will take you to a TableView of all Customers enrolled in the database.
You can add, update, and delete customers, but please make sure to fill out all required information for Add and Delete. You will be notified if and what any errors are. While on the Customers page, if you select a customer, you will see if they have a future appointment by way of a text field at the bottom of the screen.
Back on the main page, if you select Appointments, you will be taken to a similar screen that shows a TableView of appointments. Feel free to change the listed appointments by switching between "All", "Current Month", and "Current Week". Again, you can add, update, and delete.
The input of the dates and times have been designed to allow a degree of freedom and variety in how you enter the information.
For example, for the date you can either use the DatePicker, or type in a date. Please use format MM/dd/yyyy or MM/dd/yy.
For entering time you can enter h:mm or hh:mm. Please do not forget to indicate AM or PM as 24 hour time is not supported. AM and PM does not need to be capitalized.
Please do not schedule outside of business hours 8:00AM to 10:00PM EST as your requested time slots will be compared to those times. Also keep in mine, you will be unable to schedule a customer during an appointment that that customer is already scheduled. You will be notified if any of these things happen.
Back at the main page, if you click Reports, you will be taken to a page that will allow 3 different types of reports. Please choose an option, and select the detail from the choice boxes provided and click "Run Report". Your requested query results will show up on the same screen shortly.
When done you can either X out of the app or click the Exit button to be prompted to quit.

Thank you for trying my software.

Lambdas:
You can find the Lambdas in the following locations:

CustomerModify.java:

Interface: updateDivisionList

Implementation: comboBoxDivision.setOnMouseClicked((e -> updateDivisionList());

Usefulness:  The lambda updates the division list (choiceBox of divisions) whenever either the comboBox for countries or the comboBox for divisions is clicked. Without this lambda expression it was possible that the user could choose invalid divisions that did not match the country. By running this lambda, i am able to ensure that the user will only ever choose valid division list options.


CustomersScreen.java:

Interface: refreshSelectedCustomer

Implementation: customerTableView.setOnMouseClicked(e -> refreshSelectedCustomer());

Usefulness:  This Lambda is used to update a text field right beneath the tableView for customer. If the selected customer has any future appointments, some basic info about the appointment will populate the text field. Though this was not part of the requirements, in the real world this would be a great feature for the user as they would be able to quickly see the customers next appointment without having to switch to the appointment screen or report screen.


AppointmentsScreen.java:

Interface: java.util (No discrete interface aside from the inherent Functionalnterface of java.util)

apptStartDateTimeCol.setCellValueFactory( startDateString -> new ReadOnlyStringWrapper(startDateString.getValue().getStartDateTimeString()));

Usefulness: This lambda expression is used to retrieve, parse, format, and localize the time and date of the appointments that are populating the tableView on this page. I used this because it was the only way I could find to properly format and localize the dateTime for proper use in the table column.


These lambdas were each used several times, so you may come across multiple usages of each.


Description of additional report from part A.3.f:
My third report allows the user to run a report for all appointments for a specified location. Upon reaching the landing page for the 3rd report, the user will have a choice box of all locations currently assigned to appointments. Please select a location and click Run Report. A tableView of the results will appear shortly.

MySQL Connected driver version number, including update number:
Mysql-connector-java-8.0.22



