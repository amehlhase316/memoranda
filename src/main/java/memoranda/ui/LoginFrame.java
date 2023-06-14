package memoranda.ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.text.JTextComponent;
import java.awt.*;

public class LoginFrame extends JFrame {
    public LoginFrame() {
        JFrame frame = new JFrame();
        frame.setSize(new Dimension(500, 300));
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel framePanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10,10,10,10);

        JLabel userName = new JLabel("Username: ");
        JLabel password = new JLabel("Password: ");
        JTextField userNameField = new JTextField(20);
        JPasswordField passwordField = new JPasswordField(20);
        userNameField.setMinimumSize(new Dimension(200,20));
        passwordField.setMinimumSize(new Dimension(200,20));

        JButton login = new JButton("Login");
        JButton createNewAccount = new JButton("Create New Account");

        framePanel.add(userName, gbc);
        framePanel.add(userNameField, gbc);
        framePanel.add(password, gbc);
        framePanel.add(passwordField, gbc);
        framePanel.add(login, gbc);
        framePanel.add(createNewAccount, gbc);

        frame.add(framePanel);
        frame.pack();
        frame.setVisible(true);
    }
}
