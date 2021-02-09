package client;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;


public class ClientFacade {
    
    private static ClientFacade instance;
    private        Socket       socket;
    
    private ClientFacade() {
    }
    
    public static synchronized ClientFacade getInstance() {
        if (instance == null) {
            instance = new ClientFacade();
        }
        return instance;
    }
    
    public void setSocket(Socket socket) {
        this.socket = socket;
    }
    
    void connect() throws IOException {
        
        DataInputStream  dis = new DataInputStream(socket.getInputStream());
        DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
    
        //todo обработчик клиента-потока
        //todo классы работы с командами/json
        
    }
    
}
