package shared.factory;


import shared.model.Journal;


/**
 * Factory pattern class for creating Journal
 * @see Journal
 */
public class JournalFactory {
    
    public Journal create() {
        return new Journal();
    }
}