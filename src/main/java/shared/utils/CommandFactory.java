package shared.utils;


import com.fasterxml.jackson.core.JsonProcessingException;


public class CommandFactory {
    
    private static CommandFactory instance;
    
    private CommandFactory() {
    }
    
    public static synchronized CommandFactory getInstance() {
        if (instance == null) {
            instance = new CommandFactory();
        }
        return instance;
    }
    
    public Command createCommand(int commandId, Object content) {
        return new Command(commandId, content);
    }
    
    public String createJsonCommand(int commandId, Object content) throws JsonProcessingException {
        Command command = createCommand(commandId, content);
        return JsonManager.getInstance().createJsonString(command);
    }
    
}
