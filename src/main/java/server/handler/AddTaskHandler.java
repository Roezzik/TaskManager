package server.handler;


import shared.model.Task;
import shared.utils.Command;
import server.controller.Controller;
import server.view.utils.Refresher;


public class AddTaskHandler implements Handler {
    
    @Override
    public void handle(Command command) {
        Task       task       = (Task) command.getContent(); //todo: возможно, не сработает, нужно тестить
        Controller controller = Controller.getInstance();
        controller.addTask(task);
        Refresher.getInstance().getMainFormController().refreshTable();
        
//        String serverAnswer = CommandCreator.getInstance().createJsonCommand(ClientCommandConstants.GET_ALL_TASKS, controller.getAllTasks());
//        todo: обновить у всех
    }
}
