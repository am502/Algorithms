package ru.itis.rsa.client;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class ClientChat extends Canvas implements Runnable {
    private final static int PORT = 3456;
    private final static String HOST = "localhost";

    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;

    private JFrame frame;
    private JComboBox<String> comboBox;
    private JTextArea textArea;

    private String selectedUser;
    private List<String> users;
    private String username;

    public ClientChat() {
        Dimension size = new Dimension(640, 480);
        frame = new JFrame();

        users = new ArrayList<>();

        try {
            Socket socket = new Socket(HOST, PORT);
            dataInputStream = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
            dataOutputStream = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        AtomicBoolean isLogged = new AtomicBoolean(false);

        JPanel verticalPanel = new JPanel();
        verticalPanel.setLayout(new BorderLayout());

        comboBox = new JComboBox<>();
        comboBox.addActionListener(e -> {
                    selectedUser = (String) comboBox.getSelectedItem();
                }
        );
        verticalPanel.add(comboBox, BorderLayout.PAGE_START);

        textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setSize(size);
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setBounds(10, 60, 780, 500);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        verticalPanel.add(scrollPane, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.X_AXIS));
        JTextField textField = new JTextField();
        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(e -> {
                    if (selectedUser != null) {
                        try {
                            String message = textField.getText() + "#" + selectedUser;
                            dataOutputStream.writeUTF(message);
                            dataOutputStream.flush();
                            textField.setText("");
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    }
                    if (!isLogged.get()) {
                        try {
                            username = textField.getText();
                            dataOutputStream.writeUTF(username);
                            dataOutputStream.flush();
                            textField.setText("");
                            isLogged.set(true);
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    }
                }
        );
        bottomPanel.add(textField);
        bottomPanel.add(submitButton);
        verticalPanel.add(bottomPanel, BorderLayout.PAGE_END);

        frame.add(verticalPanel);

        frame.setPreferredSize(size);
        frame.setResizable(false);
        frame.setTitle("Chat");
        frame.pack();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        new Thread(this).start();
    }

    public void run() {
        while (true) {
            try {
                String input = dataInputStream.readUTF();
                if (input.charAt(0) == '$') {
                    comboBox.removeAllItems();
                    for (String user : input.substring(1).split(" ")) {
                        if (!user.equals(username)) {
                            comboBox.addItem(user);
                        }
                    }
                } else {
                    textArea.append(input + "\n");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        new ClientChat();
    }
}
