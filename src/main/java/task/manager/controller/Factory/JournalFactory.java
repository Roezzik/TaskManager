package task.manager.controller.Factory;


import task.manager.model.Journal;

public class JournalFactory implements JournalFactoryInterface {

    @Override
    public Journal create(){
        return new Journal();
    }
}
