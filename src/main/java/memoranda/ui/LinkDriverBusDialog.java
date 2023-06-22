package main.java.memoranda.ui;

import main.java.memoranda.Bus;
import main.java.memoranda.DriverList;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class LinkDriverBusDialog extends JDialog {
    JPanel mPanel = new JPanel(new BorderLayout());
    JPanel areaPanel = new JPanel(new BorderLayout());
    JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
    JButton cancelButton = new JButton();
    JButton okayButton = new JButton();
    Border border1;
    Border border2;
    JPanel dialogTitlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    JLabel header = new JLabel();
    public boolean CANCELLED = true;
    JPanel jPanel8 = new JPanel(new GridBagLayout());
    Border border3;
    Border border4;
    JPanel gridPanel = new JPanel(new GridLayout(2, 2));
    JTextField seatsField = new JTextField();
    JTextField idField = new JTextField();
    Border border8;

    JPanel idFieldPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    JPanel idLabelPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    JLabel idLabel = new JLabel();
    JPanel seatsLabelPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    JLabel seatsLabel = new JLabel();
    JPanel seatsFieldPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

    public LinkDriverBusDialog(Frame frame, String title, DriverList driverList, Bus bus){
        
    }
}
