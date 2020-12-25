package com.netcracker.task.manager.view.utils;


import com.netcracker.task.manager.view.mainForm.MainFormController;


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
