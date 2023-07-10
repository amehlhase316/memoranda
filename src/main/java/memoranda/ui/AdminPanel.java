package main.java.memoranda.ui;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

import javax.swing.*;
import javax.swing.event.*;


import main.java.memoranda.Resource;
import main.java.memoranda.User;
import main.java.memoranda.UserList;
import main.java.memoranda.util.AppList;
import main.java.memoranda.util.CurrentStorage;
import main.java.memoranda.util.Local;
import main.java.memoranda.util.MimeType;
import main.java.memoranda.util.MimeTypesList;
import main.java.memoranda.util.Util;
import main.java.memoranda.EventsManager;
import main.java.memoranda.ui.EventsTable;
import main.java.memoranda.ui.DailyItemsPanel;
import main.java.memoranda.LessonList;

public class AdminPanel extends JPanel {
    HashMap<String, User> users = UserList.users;
    User user;
    JComboBox monthBox;
    JComboBox roomsList;
    JComboBox dateBox;
    JComboBox timeBox;

    JComboBox classes;
    JTextField trainerUserName;
    
    JTextField userNameField;
    JTextField userPasswordField;

    EventsTable eventsTable = new EventsTable();

    DailyItemsPanel parentPanel = null;

    LessonList lessonList = new LessonList();

    JTextField classType;
    public AdminPanel(DailyItemsPanel panel) {
        parentPanel = panel;
        try {
            jbInit();
        }
        catch (Exception ex) {
           new ExceptionDialog(ex);
        }
    }

    void jbInit() throws Exception{
                //Setting Layout Manager to BoxLayout. Contents will be put into different panels for
                //different sections of the UI using FlowLayout or another BoxLayout, which will organize the contents in a
                //horizontal fashion. These panels will then be stacked on top of one another.
                setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
                //Creating different sections of Admin Panel UI.

                //Creating first section of Admin Panel UI used to create a new user.

                //Instruction text informing user how to create new user
                JLabel createUserInstruction = new JLabel("To create a new user, enter in their username and password, select a user type and click the 'Create new user' button.");
                add(createUserInstruction);

                //Creating new panel for the rest of the create user UI. A FlowLayout is used, which will
                //organize the content of the panel in a horizontal fashion.
                JPanel createUserPanel = new JPanel();
                createUserPanel.setLayout(new BoxLayout(createUserPanel, BoxLayout.X_AXIS));
                //createUserPanel.add(Box.createHorizontalStrut(400));

                //Label for username textbox
                JLabel userNameLabel = new JLabel("Username: ");
                createUserPanel.add(userNameLabel);
                //Textbox to enter username for new user with space for 15 characters
                userNameField = new JTextField(15);
                userNameField.setMaximumSize(userNameField.getPreferredSize());
                createUserPanel.add(userNameField);
                //Label for password textbox
                JLabel userPasswordLabel = new JLabel("Password: ");
                createUserPanel.add(userPasswordLabel);
                //Textbox to enter user password with space for 15 characters
                userPasswordField = new JTextField(15);
                userPasswordField.setMaximumSize(userPasswordField.getPreferredSize());

                createUserPanel.add(userPasswordField);
                //Creating a list of user types that can be selected from
                JLabel typeLabel = new JLabel("User type: ");
                createUserPanel.add(typeLabel);
                String userTypes[] = {"Student", "Trainer", "Admin"};
                JComboBox userTypeList = new JComboBox<>(userTypes);
                userTypeList.setMaximumRowCount(1); // Display only one option initially

        // Add an action listener to the combo box
        userTypeList.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JComboBox<String> source = (JComboBox<String>) e.getSource();
                int itemCount = source.getItemCount();
                boolean popupVisible = source.isPopupVisible();

                if (itemCount > 1 && !popupVisible) {
                    source.setMaximumRowCount(itemCount); // Expand the list
                } else {
                    source.setMaximumRowCount(1); // Display only one option
                }
            }
        });
        //Set maximum size to preferred size so item isn't oversized in window
        userTypeList.setMaximumSize(userTypeList.getPreferredSize());
        createUserPanel.add(userTypeList);

                //Button to create user with the inputted information
                JButton createUserB = new JButton("Create new user");
                createUserPanel.add(createUserB);
                add(createUserPanel);
                
                createUserB.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        createUserB_actionPerformed(e);
                    }
                });

                //Creating UI for second section of Admin Panel used to edit a user's info

        //Instruction text informing user how to edit a user's info
        JLabel editUserInstruction1 = new JLabel("To edit an existing user's info, enter their username and press the 'Search user' button.");
        add(editUserInstruction1);
        JLabel editUserInstruction2 = new JLabel("Their details will autopopulate in the textboxes. Edit any fields then press the 'Save changes' button to save changes.");
        add(editUserInstruction2);


        JPanel editUserPanel = new JPanel();
        editUserPanel.setLayout(new BoxLayout(editUserPanel, BoxLayout.X_AXIS));
        //Label for username textbox
        JLabel searchedUserNameLabel = new JLabel("Username: ");
        editUserPanel.add(searchedUserNameLabel);
        //Textbox to enter username for new user with space for 15 characters
        JTextField searchedUserName = new JTextField(15);
        searchedUserName.setMaximumSize(searchedUserName.getPreferredSize());
        editUserPanel.add(searchedUserName);
        //Creating a list of user types that can be selected from
        JLabel searchedTypeLabel = new JLabel("User type: ");
        editUserPanel.add(searchedTypeLabel);
        String searchedserTypes[] = {"Student", "Trainer", "Admin"};
        JComboBox searchedUserTypeList = new JComboBox<>(userTypes);
        searchedUserTypeList.setMaximumRowCount(1); // Display only one option initially

        searchedUserTypeList.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JComboBox<String> source = (JComboBox<String>) e.getSource();
                int itemCount = source.getItemCount();
                boolean popupVisible = source.isPopupVisible();

                if (itemCount > 1 && !popupVisible) {
                    source.setMaximumRowCount(itemCount); // Expand the list
                } else {
                    source.setMaximumRowCount(1); // Display only one option
                }
            }
        });
        //Set maximum size to preferred size so item isn't oversized in window
        searchedUserTypeList.setMaximumSize(searchedUserTypeList.getPreferredSize());
        editUserPanel.add(searchedUserTypeList);
        //Creating label and textbox for first and last name
        JLabel firstNameLabel = new JLabel("First name: ");
        editUserPanel.add(firstNameLabel);
        JTextField firstName = new JTextField(15);
        firstName.setMaximumSize(firstName.getPreferredSize());
        editUserPanel.add(firstName);
        JLabel lastNameLabel = new JLabel("Last name: ");
        editUserPanel.add(lastNameLabel);
        JTextField lastName = new JTextField(15);
        lastName.setMaximumSize(lastName.getPreferredSize());
        editUserPanel.add(lastName);

        //Creating label and scrolling list for belt rank
        JLabel rankLabel = new JLabel("Belt rank: ");
        String[] ranks = {"White", "Yellow", "Orange", "Purple", "Blue", "Blue Stripe", "Green", "Green Stripe", "Brown1", "Brown2", "Brown3", "Black1", "Black2", "Black3"};

        JComboBox ranksList = new JComboBox<>(ranks);
        ranksList.setMaximumRowCount(1); // Display only one option initially

        ranksList.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JComboBox<String> source = (JComboBox<String>) e.getSource();
                int itemCount = source.getItemCount();
                boolean popupVisible = source.isPopupVisible();

                if (itemCount > 1 && !popupVisible) {
                    source.setMaximumRowCount(itemCount); // Expand the list
                } else {
                    source.setMaximumRowCount(1); // Display only one option
                }
            }
        });
        ranksList.setMaximumSize(ranksList.getPreferredSize());
        editUserPanel.add(ranksList);

        //Creating label and testbox for ID
        JLabel idLabel = new JLabel("ID: ");
        editUserPanel.add(idLabel);
        JTextField id = new JTextField(15);
        id.setMaximumSize(id.getPreferredSize());
        editUserPanel.add(id);

        //Creating buttons to search for and save changes to user
        JButton searchUserButton = new JButton("Search user");
        editUserPanel.add(searchUserButton);
        JButton changeUserButton = new JButton("Save changes");
        editUserPanel.add(changeUserButton);
        add(editUserPanel);

        //Creating third section of Admin Panel UI that allows user to create, delete or add a trainer to a class

                //JLabel with instructions on how to create, delete or add trainer to a class
                JLabel createClassInstructions = new JLabel("To create a new class, enter in the class details then press the 'Create new class' button.");
                JLabel deleteClassInstructions = new JLabel("To delete a class, select the class in the box below, then press the 'Delete Class' button.");
                add(createClassInstructions);
                add(deleteClassInstructions);


                JPanel classPanel = new JPanel(new FlowLayout());

        //Label for class type
                JLabel classTypeLabel = new JLabel("Class name: ");
                classPanel.add(classTypeLabel);
                //Textbox to enter class type into
                classType = new JTextField(15);
                classPanel.add(classType);
                //Label for room number
                JLabel classRoomLabel = new JLabel("Room: ");
                classPanel.add(classRoomLabel);
                //List to choose room number from
                String roomNumber[] = {"1", "2", "3", "4"};
                roomsList = new JComboBox<>(roomNumber);
        //roomsList.setMaximumRowCount(1); // Display only one option initially

//        ranksList.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                JComboBox<String> source = (JComboBox<String>) e.getSource();
//                int itemCount = source.getItemCount();
//                boolean popupVisible = source.isPopupVisible();
//
//                if (itemCount > 1 && !popupVisible) {
//                    source.setMaximumRowCount(itemCount); // Expand the list
//                } else {
//                    source.setMaximumRowCount(1); // Display only one option
//                }
//            }
//        });
        //roomsList.setMaximumSize(roomsList.getPreferredSize());
                classPanel.add(roomsList);
                //Label for hour
                JLabel classDateLabel = new JLabel("Hour: ");
                classPanel.add(classDateLabel);
                String[] hours = new String[]{"00:00","01:00","02:00","03:00","04:00","05:00","06:00","07:00","08:00","09:00","10:00","11:00","12:00","13:00","14:00","15:00","16:00","17:00","18:00","19:00","20:00","21:00","22:00","23:00"};
                timeBox = new JComboBox<>(hours);
                classPanel.add(timeBox);
                JLabel monthLabel = new JLabel("Month: ");
                classPanel.add(monthLabel);

                String[] months = new String[]{"January", "February", "March", "April", "May", "June", "July",
                "August", "September", "October", "November", "December"};
                Map<String, Integer> monthDays = new HashMap<>();
            monthDays.put("January", 31);
            monthDays.put("February", 28);
            monthDays.put("March", 31);
            monthDays.put("April", 30);
            monthDays.put("May", 31);
            monthDays.put("June", 30);
            monthDays.put("July", 31);
            monthDays.put("August", 31);
            monthDays.put("September", 30);
            monthDays.put("October", 31);
            monthDays.put("November", 30);
            monthDays.put("December", 31);
            monthBox = new JComboBox<>(months);
            dateBox = new JComboBox<>();
            monthBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedMonth = (String) monthBox.getSelectedItem();
                Integer days = monthDays.get(selectedMonth);

                // Clear the dayComboBox
                dateBox.removeAllItems();

                // Add the appropriate number of days to the dayComboBox
                for (int i = 1; i <= days; i++) {
                    dateBox.addItem(i);
                }
            }
        });
                classPanel.add(monthBox);

                JLabel dateLabel = new JLabel("Date: ");
                classPanel.add(dateLabel);
                classPanel.add(dateBox);
                //List of public or private class for user to select
//                String classAvailabilities[] = {"Public", "Private"};
//        JComboBox classAvailabilityList = new JComboBox<>(classAvailabilities);
//        classAvailabilityList.setMaximumRowCount(1); // Display only one option initially
//
//        ranksList.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                JComboBox<String> source = (JComboBox<String>) e.getSource();
//                int itemCount = source.getItemCount();
//                boolean popupVisible = source.isPopupVisible();
//
//                if (itemCount > 1 && !popupVisible) {
//                    source.setMaximumRowCount(itemCount); // Expand the list
//                } else {
//                    source.setMaximumRowCount(1); // Display only one option
//                }
//            }
//        });

//        classAvailabilityList.setMaximumSize(classAvailabilityList.getPreferredSize());
//                classPanel.add(classAvailabilityList);

                //Label and textbox for trainer username
                JLabel trainerUserNameLabel = new JLabel("Trainer ID: ");
                classPanel.add(trainerUserNameLabel);
                trainerUserName = new JTextField(15);
                classPanel.add(trainerUserName);


                JPanel classButtonsPanel = new JPanel();

                //Button to create class
                JButton createClass = new JButton("Create new class");
        createClass.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                createClass_actionPerformed(e);
            }
        });
                classPanel.add(createClass);

        add(classPanel);

        JPanel deleteClassPanel = new JPanel(new FlowLayout());


        classes = new JComboBox(LessonList.listedLessons.toArray());
        deleteClassPanel.add(classes);
        //Button to delete class
        JButton deleteClass = new JButton("Delete class");
        deleteClass.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deleteClass_actionPerformed(e);
            }
        });

        deleteClassPanel.add(deleteClass);
        add(deleteClassPanel);
    }

    void createUserB_actionPerformed(ActionEvent e) {
        String username = userNameField.getText();
        String password = userPasswordField.getText();
        
        User user = new User(username, password);
        users.put(username, user);
    }
    
    void createClass_actionPerformed(ActionEvent e) {
        int year = main.java.memoranda.date.CalendarDate.today().getYear();
        EventsManager.createEvent(roomsList.getSelectedIndex() + 1, timeBox.getSelectedIndex(), 0, monthBox.getSelectedIndex() + 1, dateBox.getSelectedIndex() + 1, year, classType.getText());
        saveEvents();
        classes.addItem(LessonList.listedLessons.get(LessonList.listedLessons.size() - 1));
    }

    void deleteClass_actionPerformed(ActionEvent e) {
        EventsManager.removeEvent(LessonList.getLesson(classes.getSelectedIndex()));
        String item = (String) classes.getSelectedItem();
        LessonList.removeLesson(classes.getSelectedIndex());
        classes.removeItem(item);
        saveEvents();
//        public static void removeEvent(main.java.memoranda.date.CalendarDate date, int hh, int mm) {
//            EventsManager.Day d = getDay(date);
//            if (d == null)
//                d.getElement().removeChild(getEvent(date, hh, mm).getContent());
//        }
    }

    private void saveEvents() {
        CurrentStorage.get().storeEventsManager();
        eventsTable.refresh();
        main.java.memoranda.EventsScheduler.init();
        parentPanel.calendar.jnCalendar.updateUI();
        parentPanel.updateIndicators();
    }

}