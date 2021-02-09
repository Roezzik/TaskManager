package server.controller.utils;


import shared.constant.ServerCommandConstants;
import server.handler.AddTaskHandler;
import server.handler.Handler;
import server.handler.SendTasksHandler;

import java.util.HashMap;
import java.util.Map;


public class ServerCommandManager {
    
    private Map<Integer, Handler> serverHandlerMap;
    
    public ServerCommandManager() {
        serverHandlerMap = new HashMap<>();
        serverHandlerMap.put(ServerCommandConstants.SEND_TASKS, new SendTasksHandler());
        serverHandlerMap.put(ServerCommandConstants.ADD_TASK, new AddTaskHandler());
    }
    //todo
}
