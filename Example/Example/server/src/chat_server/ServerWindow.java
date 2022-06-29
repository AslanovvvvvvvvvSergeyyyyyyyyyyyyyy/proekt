package chat_server;

import Pysk.Go;
import chat_network.TCPConnection;
import chat_network.TCPConnectionListener;
import Pysk.Enity.Login;

import javax.swing.*;
import java.io.IOException;
import java.net.ServerSocket;
import java.sql.SQLException;
import java.util.ArrayList;

public class ServerWindow extends Login implements TCPConnectionListener{

    public static void main(String[] args){
        new ServerWindow();
    }
    private final ArrayList<TCPConnection> connections = new ArrayList<>();
    private ServerWindow(){
        System.out.println("Server running.........");
        try(ServerSocket serverSocket = new ServerSocket(80)){
            while(true){
                try {
                    new TCPConnection(this, serverSocket.accept());
                }catch (IOException e){
                    System.out.println("TCPConnection ex: " + e); // имя пользователя
                }
            }
        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }


    @Override
    public synchronized void onConnectionReady(TCPConnection tcpConnection) {
            connections.add(tcpConnection);
            sendAllConnections("Client connected: " + tcpConnection );
        }


    @Override
    public synchronized void onReceiveString(TCPConnection tcpConnection, String value) {
        sendAllConnections(value);
    }

    @Override
    public synchronized void onDisconnect(TCPConnection tcpConnection) {
        connections.remove(tcpConnection);
        sendAllConnections("Client Disconnected: " + tcpConnection);

    }

    @Override
    public synchronized void onException(TCPConnection tcpConnection, Exception e) {
        System.out.println("TCPConnection ex: " + e);
    }

    @Override
    public void actionPerformed(Integer value) {
        try {
            Go.statement.execute("UPDATE register SET tcp2 =\'" + value + "\'");
        } catch (SQLException e1) {
            throw new RuntimeException(e1);
        }
    }

    private void sendAllConnections(String value) {
        System.out.println(value);
        final int cnt = connections.size();
        for (int i = 0; i < cnt; i++) connections.get(i).sendString(value);

    }
}
