package ru.itis.rsa.server;

import ru.itis.rsa.Rsa;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Server {
    private final static int PORT = 3456;

    private List<Connection> connections;
    private Map<String, Rsa> users;

    public Server() {
        connections = new ArrayList<>();
        users = new HashMap<>();

        ServerSocket server = null;
        try {
            server = new ServerSocket(PORT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        while (true) {
            try {
                Socket client = server.accept();

                Rsa rsa = new Rsa(2048);

                DataInputStream inputStream = new DataInputStream(new BufferedInputStream(client.getInputStream()));

                String user = inputStream.readUTF();
                connections.add(new Connection(this, client, user));
                users.put(user, rsa);

                System.out.println(user);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public List<Connection> getConnections() {
        return connections;
    }

    public Rsa getRsa(String user) {
        return users.get(user);
    }

    public String getUsers() {
        StringBuilder sb = new StringBuilder();
        for (String user : users.keySet()) {
            sb.append(user).append(" ");
        }
        return sb.toString().substring(0, sb.length() - 1);
    }

    public static void main(String[] args) {
        new Server();
    }
}
