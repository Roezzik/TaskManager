package client;


import shared.constant.ViewConstants;
import shared.constant.ViewPathConstants;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.Socket;


public class Client extends Application {
    
    public static void main(String[] args) throws IOException {
        
        ClientFacade clientFacade = ClientFacade.getInstance();
        clientFacade.setSocket(new Socket("localhost", 1234));
        clientFacade.connect();
        Application.launch(args);
    }
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(ViewPathConstants.PATH_TO_MAIN_FORM_VIEW));
        Parent     root       = fxmlLoader.load();
        primaryStage.setTitle(ViewConstants.TITLE_TO_MAIN_FORM_VIEW);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
    
    @Override
    public void stop() throws Exception {
        super.stop();
        //todo отправлять команду, чтобы произошёл дисконект клиента
    }
}

