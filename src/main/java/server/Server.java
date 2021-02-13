package server;


import shared.ConnectionListener;
import shared.Connection;

import java.io.*;
import java.net.ServerSocket;

import java.util.ArrayList;

public class Server implements ConnectionListener {

    private ArrayList<Connection> connections = new ArrayList<>();
    private Server() {
        System.out.println("Server starts...");
        try (ServerSocket serverSocket = new ServerSocket(8080)) {
            while (true) {
               new Connection(this, serverSocket.accept());
            }
        } catch (IOException e) {
            System.out.println("fail");
        }
    }

    public static void main(String[] args) { new Server();}


    @Override
    public void connect(Connection connection) {
        connections.add(connection);
    }

    @Override
    public void receive(Connection connection, String string) {
    }

    @Override
    public void send(Connection connection, String string) {
    }

    @Override
    public void disconnect(Connection connection) {
        connections.remove(connection);

    }
}