package chat_network;


import java.awt.event.ActionEvent;

public interface TCPConnectionListener {
    void onConnectionReady(TCPConnection tcpConnection); // Когда приконектились
    void onReceiveString(TCPConnection tcpConnection, String value); // Получили строчку
    void onDisconnect(TCPConnection tcpConnection);// Отконектились
    void onException(TCPConnection tcpConnection,Exception e);// Исключение

     void actionPerformed(Integer value);

}