package com.netcracker.task.manager.view.utils;


public class ViewConstants {
    
    public static final String DATE_FORMAT = "dd.MM.yyyy HH:mm";
    
    public static final String ERROR_FILE_OUTPUT_STREAM_EXCEPTION = "Error output stream Exception!";
    public static final String ERROR_FILE_INPUT_STREAM_EXCEPTION  = "Error input stream Exception!";
    public static final String ERROR_EDIT_TASK_FORM_EXCEPTION     = "Edit task form exception!";
    public static final String ERROR_ADD_TASK_FORM_EXCEPTION      = "Add task form exception!";
    public static final String ERROR_PRINT_WRITER_EXCEPTION       = "Error print write!";
    public static final String ERROR_BUFFER_READ_EXCEPTION        = "Error Buffer Reader Exception!";
    public static final String ERROR_SCHEDULED_TASK               = "Error scheduled task!";
    public static final String ERROR_READ_PROPERTY                = "File property read error!";
    public static final String ERROR_PLATFORM_RUN                 = "Error creating a stream!";
    public static final String ERROR_CREATE_FILE                  = "File creation error!";
    public static final String ERROR_READ_FILE                    = "File read error!";
    public static final String ERROR_BACKUP_NOT_FOUND             = """
            The backup file was not found.\s
            You may be using 'Task Manager' for the first time.\s
            We have created a new backup file for you. Enjoy using it!""";
    
    public static final String TITLE_TO_ERROR = "Error!";
    public static final String TITLE_TO_HELLO = "Welcome to Task Manager!";
    
    public static final String TITLE_TO_MAIN_FORM_VIEW         = "Task Manager";
    public static final String TITLE_TO_ADD_FORM_VIEW          = "Add Task";
    public static final String TITLE_TO_EDIT_FORM_VIEW         = "Edit Task";
    public static final String TITLE_TO_NOTIFICATION_FORM_VIEW = "Notification";
    public static final String TITLE_TO_POSTPONE_FORM_VIEW     = "Postpone Task";
    
    public static final String STYLE_FOR_EDIT_BUTTON = "-fx-background-color: transparent;";
    public static final double MIN_SIZE_EDIT_BUTTON  = 17;
    public static final double MAX_SIZE_EDIT_BUTTON  = 17;
    public static final double PREF_SIZE_EDIT_BUTTON = 17;
    
    public static final String TITLE_TO_ALERT_ERROR_ADDING     = "Adding error!";
    public static final String TITLE_TO_ALERT_ERROR_EDITING    = "Editing error!";
    public static final String TITLE_TO_POSTPONE_ERROR_EDITING = "Postpone error!";
    
    public static final String ALERT_MISSING_TASK_NAME  = "Please, enter a name of task!";
    public static final String ALERT_INCORRECT_TIME     = "You can't set the passed time for notification! \n "
                                                          + "Please, enter a correct time of notification.";
    public static final String ALERT_INCORRECT_POSTPONE = "You can't postpone a task by the elapsed time.\n"
                                                          + "The selected time period will be added to the current moment. ";
}