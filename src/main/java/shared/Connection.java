package shared;

import java.io.*;
import java.net.Socket;

public class Connection{
    private final Socket socket;
    private final Thread thread;
    private final BufferedWriter out;
    private final BufferedReader in;
    private final ConnectionListener connectionListener;

    public Connection(ConnectionListener connectionListener, Socket socket) throws IOException {
        this.connectionListener = connectionListener;
        this.socket = socket;
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    connectionListener.connect(Connection.this);
                    while(!thread.isInterrupted())
                        connectionListener.receive(Connection.this, in.readLine());
                }
                catch (IOException e){
                    System.out.println("fyyy");
                }
                finally {
                    connectionListener.disconnect(Connection.this);
                }
            }
        });
        thread.start();
    }

    public Connection(ConnectionListener connectionListener, String address, int port) throws IOException {
        this(connectionListener, new Socket(address, port));
    }

    public synchronized void send(String s) throws IOException {
            out.write(s);
            out.flush();
    }

    public String receive() throws IOException {
        synchronized (this.in) {
            return in.readLine();
        }
    }

    public void close() throws IOException {
        in.close();
        out.close();
        socket.close();
    }


    public void disconnect(Connection connection) {
        thread.interrupt();
        try {
            socket.close();
            close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
