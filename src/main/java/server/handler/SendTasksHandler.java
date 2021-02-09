package server.handler;


import shared.utils.Command;


public class SendTasksHandler implements Handler {
    
    @Override
    public void handle(Command command) {
        
        //todo получить dataOutputStream из сокета клиента, отправить команду
        
//        CommandCreator.getInstance().createJsonCommand(ClientCommandConstants.GET_ALL_TASKS, Controller.getInstance().getAllTasks());
    
    }
}
