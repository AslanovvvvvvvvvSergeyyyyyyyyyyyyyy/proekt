package Pysk.Enity;


import Pysk.Go;
import Pysk.persistence.HibernateUtil;
import org.hibernate.Session;

import chat_network.TCPConnection;
import chat_network.TCPConnectionListener;

import javax.swing.*; // javafx meiby
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;


public class EnterPoint extends JFrame implements ActionListener, TCPConnectionListener{


    private static final String IP_ADDR = "172.20.10.2";
    private static final int PORT = 80;
    private static final int WIDTH = 600;
    private static final int HEIGHT = 400;


    public static void main (String[] args){
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new EnterPoint();
            }
        });

    }
    private final JTextArea log = new JTextArea();


    public final JTextField  fieldNickname = new JTextField(); // имя пользователя, каждый раз этот класс заново создается
    private final JTextField fieldInput = new JTextField();


    private TCPConnection connection;
    public EnterPoint() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(WIDTH,HEIGHT);
        setLocationRelativeTo(null);
        setAlwaysOnTop(true);

        setVisible(true);
        log.setEditable(false);
        log.setLineWrap(true);

        fieldInput.addActionListener(this);
        add(log, BorderLayout.CENTER);
        // add(fieldNickname, BorderLayout.NORTH);
        add(fieldInput, BorderLayout.SOUTH);

        try {
            connection = new TCPConnection(this, IP_ADDR,PORT);
        } catch (IOException e) {
            printMsg("Connection ex: " + e);
        }

    }


    @Override
    public void actionPerformed(ActionEvent e) {
        String msg = fieldInput.getText();
        if(msg.equals("")) return;
        fieldInput.setText(null);
        connection.sendString("hanne" + ": " + msg);
    }



    @Override
    public void onConnectionReady(TCPConnection tcpConnection) {
    }


    @Override // Тут втсавить
    public void onReceiveString(TCPConnection tcpConnection, String value) {
        printMsg(value);

    }

    @Override
    public void onDisconnect(TCPConnection tcpConnection) {
        try {
            Go.statement.execute("UPDATE register SET online_reg =\'" + 0 + "\'");
        } catch (SQLException e1) {
            throw new RuntimeException(e1);
        }
    }

    @Override
    public void onException(TCPConnection tcpConnection, Exception e) {
        printMsg("Connection ex: " + e);
    }

    @Override
    public void actionPerformed(Integer value) {
        try {
            Go.statement.execute("UPDATE register SET tcp2 =\'" + value + "\'");
        } catch (SQLException e1) {
            throw new RuntimeException(e1);
        }
    }



    private synchronized  void printMsg(String msg){
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                log.append(msg + "\n");
                log.setCaretPosition(log.getDocument().getLength());
            }
        });
    }

    private synchronized void saveMsg(String msg){
        Session session = HibernateUtil.getSessionFactory().openSession();

        session.beginTransaction();
        User user = new User();

        user.setFirstName("Alexander");
         //user.setLastName(fieldNickname.getText());
        user.setMessage(msg);

        session.save(user);
        session.getTransaction().commit();
    }

}










