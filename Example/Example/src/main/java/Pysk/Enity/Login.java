package Pysk.Enity;

import Pysk.Go;
import Pysk.persistence.HibernateUtil;
import chat_network.TCPConnection;
import chat_network.TCPConnectionListener;
import org.hibernate.Session;
import Pysk.Enity.Reg;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Login extends JPanel {

    public JTextField login = new JTextField(); //ptivate
    private JPasswordField password = new JPasswordField();
    private JButton singin = new JButton("Login");
    private JButton reg = new JButton("Registr");

    public static CardLayout layout = new CardLayout();

    public static JPanel mainPanel = new JPanel();


    public Login() {
        setSize(800, 600);
        setLayout(null);

        login.setBounds(250, 100, 300, 30);
        password.setBounds(250, 150, 300, 30);
        singin.setBounds(250, 200, 300, 20);
        reg.setBounds(250, 250, 300, 20);

        add(login);
        add(password);
        add(singin);
        add(reg);

        singin.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    ResultSet result = Go.statement.executeQuery("SELECT id_reg FROM register WHERE login=\'" + login.getText() + "\' AND" + " online_reg=\'" + 0 + "\' AND" + " password=\'" + password.getText() + "\' ");
                    if (result.next()) {
                        Go.statement.execute("UPDATE register SET online_reg = 1 WHERE login=\'" + login.getText() + "\'");
                        mainPanel.add(new EnterPoint(), "Chat");
                        add(mainPanel);
                        Go.layout.show(Go.mainPanel, "Chat");

                    } else {
                        ResultSet result1 = Go.statement.executeQuery("SELECT id_reg FROM register WHERE login=\'" + login.getText() + "\'");
                        if (result1.next()) {
                            JOptionPane.showMessageDialog(null, "Wrong password:(, Try again");
                        }
                        JOptionPane.showMessageDialog(null, "The user is not registered:(");
                    }
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        });
        reg.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Go.layout.show(Go.mainPanel, "Registr");
            }
        });
    }

    @Override
    public String toString() {
        return login.getText();
    }
}