package com.Chat;

import java.util.*;
import java.io.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ClientWindow extends JFrame implements ConnectionListener, ActionListener {

    private final String IP;
    private final int PORT=8000;
    private final String name;
    private final int WIDTH=700;
    private final int HEIGT=560;
    private final JTextArea text = new JTextArea();
    private final JTextField fieldName = new JTextField();
    private final JTextField fieldInputText = new JTextField();
    private Connection connection;

    public ClientWindow(String IP, String name) {
        this.IP=IP;
        this.name = name;
        fieldName.setText(name);
        text.setForeground(Color.green);
        text.setBackground(Color.black);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(WIDTH, HEIGT);
        setLocationRelativeTo(null);
        setAlwaysOnTop(true);
        text.setEditable(false);
        text.setLineWrap(true);
        fieldInputText.addActionListener(this);
        add(text, BorderLayout.CENTER);
        add(fieldInputText, BorderLayout.SOUTH);
        setVisible(true);
        printMessage("Client name: " + fieldName.getText() + " Server port: " + PORT + " Server ip: " + IP );

                try {
                    connection = new Connection(this, IP, PORT);
                } catch (IOException e) {
                    e.printStackTrace();
                    printMessage("Connection exception: " + e);
                }
            }

    @Override
    public void actionPerformed(ActionEvent e) {
        String post = fieldInputText.getText();
        if(post.equals("")) return;
        fieldInputText.setText(null);
        connection.sendString(fieldName.getText() + ": " + post);
    }

    @Override
    public synchronized void onConnectionReady(Connection connection) {
        printMessage("Connection with server exist...");
    }

    @Override
    public synchronized void onReceiveString(Connection connection, String string) {
        printMessage(string);
    }

    @Override
    public synchronized void onDisconneckt(Connection connection) {
        printMessage("User disconnected: " + connection);
    }

    @Override
    public synchronized void onException(Connection connection, Exception e) {
        printMessage("Connection exception: " + e);
    }

    public static synchronized String writeMsg() {
        Scanner scanner = new Scanner(System.in);
        String write = scanner.nextLine();
        return write;
    }

    private synchronized void printMessage(String message) {
        SwingUtilities.invokeLater(new Runnable()  {
            @Override
            public void run() {
                text.append(message + "\n");
                text.setCaretPosition(text.getDocument().getLength());
            }
        });
    }
}