package task.manager.view.mainForm;


import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Callback;
import task.manager.controller.Controller;
import task.manager.controller.Setting;
import task.manager.controller.io.BinaryMarshaller;
import task.manager.controller.io.TextMarshaller;
import task.manager.model.Task;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.zip.CheckedOutputStream;


public class MainFormController {
    
    @FXML
    private MenuItem loadJournalItem;
    
    @FXML
    private MenuItem saveJournalItem;
    
    @FXML
    private TableView<Task> tasksTable;
    
    @FXML
    private CheckBox selectAll;
    //TODO: create selectAll() method
    
    //add checkbox in task model?
    @FXML
    private TableColumn<Task, CheckBox> chooseColumn;
    
    @FXML
    private TableColumn<Task, String> nameColumn;
    
    @FXML
    private TableColumn<Task, String> descriptionColumn;
    
    @FXML
    private TableColumn<Task, String> dateColumn;
    
    @FXML
    private TableColumn<Task, String> statusColumn;
    
    @FXML
    private Button editButton;
    
    @FXML
    private Button addButton;
    
    @FXML
    private Button cancelButton;
    
    @FXML
    private Button deleteButton;

    Controller controller;
    @FXML
    private void initialize() throws IOException {
        controller = new Controller();
        Map<Integer, Task>   tasksMap  = TextMarshaller.getInstance().read(Setting.getPropertyValue("FILE_PATH")).getTasksMap();
        ObservableList<Task> tasksData = FXCollections.observableArrayList();
        
        tasksMap.forEach((k, v) -> tasksData.add(v));
        
        //TODO: chooseColumn
        //chooseColumn.setCellValueFactory(new PropertyValueFactory<>("checkBox"));
        
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        dateColumn.setCellValueFactory(param -> {
            Date             date       = param.getValue().getDate();
            SimpleDateFormat simpleDate = new SimpleDateFormat("dd.MM.yyyy hh:mm");
            String           dateInView = simpleDate.format(date);
            return new SimpleObjectProperty<>(dateInView);
        });
        statusColumn.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getStatus().getTitle()));
        addEditButtonToTable();
        
        tasksTable.setItems(tasksData);
    }
    
    @FXML
    private void addEditButtonToTable() {
        
        TableColumn<Task, Task> editButtonColumn = new TableColumn<>();
        editButtonColumn.setMaxWidth(25);
        
        editButtonColumn.setCellValueFactory(p -> new SimpleObjectProperty<>(p.getValue()));
        editButtonColumn.setCellFactory(p -> new ButtonCell());
        
        tasksTable.getColumns().add(editButtonColumn);
    }
    
    @FXML
    public void loadJournal(ActionEvent actionEvent) {
        //TODO
    }
    
    @FXML
    public void saveJournal(ActionEvent actionEvent) {
        //TODO
    }
    
    @FXML
    public void openAddTaskView(ActionEvent event) {
        try {
            URL    url   = new File("src/main/java/task/manager/view/addForm/addTaskView.fxml").toURI().toURL();
            Parent root  = FXMLLoader.load(url);
            Scene  scene = new Scene(root);
            Stage  stage = new Stage();
            stage.setTitle("Add Task");
            stage.setScene(scene);
            stage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    public void openEditTaskView(Task task) {
        try {
            URL    url   = new File("src/main/java/task/manager/view/editForm/editTaskView.fxml").toURI().toURL();
            Parent root  = FXMLLoader.load(url);
            Scene  scene = new Scene(root);
            Stage  stage = new Stage();
            stage.setTitle("Edit Task");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private class ButtonCell extends TableCell<Task, Task> {
        final Button cellButton = new Button();
        
        ButtonCell() {
            FileInputStream inputStream = null;
            try {
                inputStream = new FileInputStream("src/main/resources/images/editButton.png");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            assert inputStream != null;
            Image     img  = new Image(inputStream);
            ImageView view = new ImageView(img);
            cellButton.setGraphic(view);
            cellButton.setStyle("-fx-background-color: transparent;");
            cellButton.setMinSize(17, 17);
            cellButton.setMaxSize(17, 17);
            cellButton.setPrefSize(17, 17);
            cellButton.setOnAction(t -> {
                // Действие кнопки.
                Task task = this.getItem();
                openEditTaskView(task);
                System.out.println("selectedData: " + task);
            });
        }
        
        @Override
        protected void updateItem(Task t, boolean empty) {
            super.updateItem(t, empty);
            // Чтобы показывалась только в строках с данными.
            if (empty) {
                setGraphic(null);
            } else {
                setGraphic(cellButton);
            }
        }
    }
    
}
