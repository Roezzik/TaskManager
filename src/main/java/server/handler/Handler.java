package server.handler;


import shared.utils.Command;


public interface Handler {
    
    void handle(Command command); //todo throws HandleException
}
