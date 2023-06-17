package memoranda.ui;

import memoranda.AuthenticationService.AuthenticationServer;
import memoranda.AuthenticationService.LoginReturns;
import memoranda.CurrentProject;
import memoranda.Project;
import memoranda.ProjectManager;
import memoranda.date.CalendarDate;
import memoranda.util.FileStorage;
import memoranda.util.Local;
import memoranda.util.Util;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.MouseListener;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginFrame extends JFrame {
    public AuthenticationServer server;
    public LoginFrame(AppFrame currentApplicationFrame, App currentApplication) {
        server = new AuthenticationServer();
        JFrame frame = new JFrame();
        frame.setSize(new Dimension(500, 350));
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel framePanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10,10,10,10);

        JLabel notice = new JLabel("                                        ");
        JLabel notice2 = new JLabel("                                        ");
        JLabel userName = new JLabel("Username: ");
        JLabel password = new JLabel("Password: ");
        JTextField userNameField = new JTextField(20);
        JPasswordField passwordField = new JPasswordField(20);
        userNameField.setMinimumSize(new Dimension(200,20));
        passwordField.setMinimumSize(new Dimension(200,20));

        JButton login = new JButton("Login");
        JButton createNewAccount = new JButton("Create New Account");
        login.addActionListener(e -> {
            if(userNameField.getText().length()<6||userNameField.getText().length()>32
            || passwordField.getText().length()<8 || passwordField.getText().length()>64) {
                notice.setText("Invalid Credentials");
                notice2.setText("                                        ");
            }
            else {
                notice.setText("Loading");
                LoginReturns result = server.login(false, userNameField.getText(), passwordField.getText());
                Util.currentUser = userNameField.getText();
                if(result==LoginReturns.LOGIN_SUCCESSFUL) {
                    Util.currentUser = userNameField.getText();
                    Project openProject = ProjectManager.createProject(userNameField.getText(),
                            Local.getString(userNameField.getText() + " project"), CalendarDate.today(), null);
                    CurrentProject.set(openProject);
                    currentApplication.show();
                    passwordField.setText("");
                    userNameField.setText("");
                    frame.setVisible(false);
                }
                else {
                    notice.setText(result.toString());
                }
            }
        });
        createNewAccount.addActionListener(e -> {
            if(userNameField.getText().length()<6) {
                notice.setText("Username too short.");
                notice2.setText("Must be 6-32 characters");
            }
            else if(userNameField.getText().length()>32) {
                notice.setText("Username too long.");
                notice2.setText("Must be 6-32 characters");
            }
            else if(passwordField.getText().length()<8){
                notice.setText("Password too short.");
                notice2.setText("Must be 8-64 characters");
            }
            else if(passwordField.getText().length()>64){
                notice.setText("Password too long.");
                notice2.setText("Must be 8-64 characters");
            }
            else {
                Pattern p = Pattern.compile("[A-Z]");
                Matcher m = p.matcher(passwordField.getText());
                boolean hasUppercase = m.find();
                p = Pattern.compile("[A-Za-z0-9]");
                m=p.matcher(passwordField.getText());
                boolean hasSpecialChar = m.find();
                p = Pattern.compile("[0-9]");
                m=p.matcher(passwordField.getText());
                boolean hasNumber = m.find();
                if(!hasUppercase) {
                    notice.setText("Must contain an upper case character.");
                    notice2.setText("                                        ");
                }
                else if(!hasSpecialChar) {
                    notice.setText("Must contain a special character.");
                    notice2.setText("                                        ");
                }
                else if(!hasNumber) {
                    notice.setText("Must contain a number.");
                    notice2.setText("                                        ");
                }
                else {
                    notice.setText("Loading");
                    LoginReturns result = server.login(true, userNameField.getText(), passwordField.getText());
                    if(result==LoginReturns.CREATED_ACCOUNT_INFO) {
                        Util.currentUser = userNameField.getText();
                        Project openProject = ProjectManager.createProject(userNameField.getText(),
                                Local.getString(userNameField.getText() + " project"), CalendarDate.today(), null);
                        CurrentProject.set(openProject);
                        currentApplication.show();
                        passwordField.setText("");
                        userNameField.setText("");
                        frame.setVisible(false);
                    }
                    else {
                        notice.setText(result.toString());
                    }
                }
            }
        });

        JLabel logo = new JLabel();
        ImageIcon imageLogo = new ImageIcon(Objects.requireNonNull(getClass().getResource("/ui/splash.png")));
        Image resized = imageLogo.getImage().getScaledInstance(200,100, Image.SCALE_SMOOTH);
        imageLogo.setImage(resized);
        logo.setIcon(imageLogo);
        logo.setPreferredSize(new Dimension(200,100));
        logo.setHorizontalAlignment(SwingConstants.CENTER);

        framePanel.add(logo, gbc);
        framePanel.add(userName, gbc);
        framePanel.add(userNameField, gbc);
        framePanel.add(password, gbc);
        framePanel.add(passwordField, gbc);
        framePanel.add(login, gbc);
        framePanel.add(createNewAccount, gbc);
        framePanel.add(notice,gbc);
        framePanel.add(notice2,gbc);

        frame.add(framePanel);
        frame.pack();
        frame.setAlwaysOnTop(true);
        frame.setVisible(true);


    }
}
