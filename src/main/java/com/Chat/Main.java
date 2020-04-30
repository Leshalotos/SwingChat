package com.Chat;

public class Main {

    public static void main(String[] args) {
        System.out.println("Please enter yor name or create new server with command (server) ... ");
        String name = ClientWindow.writeMsg();
        if(name.equals("server")) {
            ChatServer chatServer = new ChatServer();
        } else {
            System.out.println("Dear " + name + ", please enter server ip ****.****.****.**** ...");
        }
        String ip = ClientWindow.writeMsg();
        ClientWindow client = new ClientWindow(ip, name);
    }

}