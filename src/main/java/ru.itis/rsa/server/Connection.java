package ru.itis.rsa.server;

import ru.itis.rsa.MessageProvider;
import ru.itis.rsa.Rsa;

import java.io.*;
import java.math.BigInteger;
import java.net.Socket;
import java.util.List;

public class Connection implements Runnable {
    private Server server;
    private String user;
    private int usersCount = 0;

    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;

    public Connection(Server server, Socket socket, String user) {
        this.server = server;
        this.user = user;
        try {
            dataInputStream = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
            dataOutputStream = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        new Thread(this).start();
    }

    public void run() {
        while (true) {
            try {
                if (dataInputStream.available() > 0) {
                    String[] input = dataInputStream.readUTF().split("#");
                    Rsa sender = server.getRsa(user);
                    Rsa receiver = server.getRsa(input[1]);
                    List<BigInteger> encrypt = MessageProvider.getInstance().encrypt(input[0], sender, receiver);
                    for (Connection connection : server.getConnections()) {
                        String message = MessageProvider.getInstance().decrypt(encrypt, sender,
                                server.getRsa(connection.getUser()));
                        connection.send(user + ": " + message);
                    }
                }
                if (usersCount != server.getConnections().size()) {
                    for (Connection connection : server.getConnections()) {
                        connection.send("$" + server.getUsers());
                    }
                    usersCount = server.getConnections().size();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void send(String message) {
        try {
            dataOutputStream.writeUTF(message);
            dataOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getUser() {
        return user;
    }
}
