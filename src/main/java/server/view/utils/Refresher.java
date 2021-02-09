package server.view.utils;


import server.view.mainForm.MainFormController;


/**
 * Class for updating a table with tasks
 */
public class Refresher {
    
    private static Refresher          instance;
    private        MainFormController mainFormController;
    
    private Refresher() {
    }
    
    public static Refresher getInstance() {
        if (instance == null) {
            instance = new Refresher();
        }
        return instance;
    }
    
    public MainFormController getMainFormController() {
        return mainFormController;
    }
    
    public void setMainFormController(MainFormController mainFormController) {
        this.mainFormController = mainFormController;
    }
}
