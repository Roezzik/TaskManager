package task.manager.model;

public class JournalFactory implements JournalFactoryInterface{

    @Override
    public Journal createJournal(){
        return new Journal();
    }
}
