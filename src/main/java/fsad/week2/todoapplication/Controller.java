package fsad.week2.todoapplication;

import fsad.week2.todoapplication.dataModel.toDoItem;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextArea;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Controller {
    List<toDoItem> toDoItems = new ArrayList<>();

    @FXML
    ListView<String> toDoListView;
    @FXML
    TextArea itemDetailsTextArea;

    @FXML
    public void initialize() {
        toDoItem item1 = new toDoItem("Tidy up", "Clean room, make bed, fold clothes", LocalDate.of(2022, 2, 28));
        toDoItem item2 = new toDoItem("Get car MOTd", "Get your car MOTd or get towed", LocalDate.of(2022, 2, 28));
        toDoItem item3 = new toDoItem("Uni Work", "FSD, TOC, AI assignment work", LocalDate.of(2022, 2, 28));
        toDoItem item4 = new toDoItem("Plan birthday party", "Send out invites and book venue", LocalDate.of(2022, 2, 28));
        toDoItem item5 = new toDoItem("Grocery Shopping", "Weekly shopping at Tesco", LocalDate.of(2022, 2, 28));

        toDoItems.add(item1);
        toDoItems.add(item2);
        toDoItems.add(item3);
        toDoItems.add(item4);
        toDoItems.add(item5);

        //Check this?
        toDoListView.getItems();

        SelectionMode selectionMode = SelectionMode.SINGLE;
    }

    @FXML
    public void handleClickListView() {

    }
}