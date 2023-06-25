package main.java.memoranda.ui;

import java.awt.*;
import java.awt.event.*;
import java.io.*;

import javax.swing.*;
import javax.swing.event.*;

import main.java.memoranda.CurrentProject;
import main.java.memoranda.Resource;
import main.java.memoranda.util.AppList;
import main.java.memoranda.util.CurrentStorage;
import main.java.memoranda.util.Local;
import main.java.memoranda.util.MimeType;
import main.java.memoranda.util.MimeTypesList;
import main.java.memoranda.util.Util;

public class AdminPanel extends JPanel {
    public AdminPanel() {
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
                JTextField userName = new JTextField(15);
                userName.setMaximumSize(userName.getPreferredSize());
                createUserPanel.add(userName);
                //Label for password textbox
                JLabel userPasswordLabel = new JLabel("Password: ");
                createUserPanel.add(userPasswordLabel);
                //Textbox to enter user password with space for 15 characters
                JTextField userPassword = new JTextField(15);
                userPassword.setMaximumSize(userPassword.getPreferredSize());

                createUserPanel.add(userPassword);
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
                JButton createUser = new JButton("Create new user");
                createUserPanel.add(createUser);
                add(createUserPanel);

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
                JLabel createClassInstructions = new JLabel("To create a new class, enter in the class type, room and date and select either private or public, then press the 'Create new class' button.");
                JLabel deleteClassInstructions = new JLabel("To delete a class, enter the class type and date, then press the 'Delete Class' button.");
                JLabel addTeacherInstructions = new JLabel("To add a trainer to a class, enter the class details and the username of the trainer, then press the 'Add trainer' button.");
                add(createClassInstructions);
                add(deleteClassInstructions);
                add(addTeacherInstructions);


                JPanel classPanel = new JPanel(new FlowLayout());

        //Label for class type
                JLabel classTypeLabel = new JLabel("Class type: ");
                classPanel.add(classTypeLabel);
                //Textbox to enter class type into
                JTextField classType = new JTextField(15);
                classPanel.add(classType);
                //Label for room number
                JLabel classRoomLabel = new JLabel("Room: ");
                classPanel.add(classRoomLabel);
                //List to choose room number from
                String roomNumber[] = {"1", "2", "3", "4"};
        JComboBox roomsList = new JComboBox<>(roomNumber);
        roomsList.setMaximumRowCount(1); // Display only one option initially

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
        roomsList.setMaximumSize(roomsList.getPreferredSize());
                classPanel.add(roomsList);
                //Label for date
                JLabel classDateLabel = new JLabel("Date: ");
                classPanel.add(classDateLabel);
                //Textbox for date
                JTextField classDate = new JTextField(15);
                classPanel.add(classDate);
                //List of public or private class for user to select
                String classAvailabilities[] = {"Public", "Private"};
        JComboBox classAvailabilityList = new JComboBox<>(roomNumber);
        classAvailabilityList.setMaximumRowCount(1); // Display only one option initially

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

        classAvailabilityList.setMaximumSize(classAvailabilityList.getPreferredSize());
                classPanel.add(classAvailabilityList);

                //Label and textbox for trainer username
                JLabel trainerUserNameLabel = new JLabel("Trainer username: ");
                classPanel.add(trainerUserNameLabel);
                JTextField trainerUserName = new JTextField(15);
                classPanel.add(trainerUserName);


                JPanel classButtonsPanel = new JPanel();

                //Button to create class
                JButton createClass = new JButton("Create new class");
                classPanel.add(createClass);

                //Button to delete class
                JButton deleteClass = new JButton("Delete class");
                classPanel.add(deleteClass);

                JButton addTrainer = new JButton("Add trainer");
                classPanel.add(addTrainer);

        add(classPanel);
    }

}