package fsad.week2.todoapplication;

import fsad.week2.todoapplication.dataModel.ToDoItem;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextArea;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Controller {
    private List<ToDoItem> toDoItems = new ArrayList<>();

    @FXML
    private ListView<ToDoItem> toDoListView;
    @FXML
    private TextArea itemDetailsTextArea;

    @FXML
    public void initialize() {
        ToDoItem item1 = new ToDoItem("Tidy up", "Clean room, make bed, fold clothes", LocalDate.of(2022, 2, 28));
        ToDoItem item2 = new ToDoItem("Get car MOTd", "Get your car MOTd or get towed", LocalDate.of(2022, 2, 28));
        ToDoItem item3 = new ToDoItem("Uni Work", "FSD, TOC, AI assignment work", LocalDate.of(2022, 2, 28));
        ToDoItem item4 = new ToDoItem("Plan birthday party", "Send out invites and book venue", LocalDate.of(2022, 2, 28));
        ToDoItem item5 = new ToDoItem("Grocery Shopping", "Weekly shopping at Tesco", LocalDate.of(2022, 2, 28));

        toDoItems.add(item1);
        toDoItems.add(item2);
        toDoItems.add(item3);
        toDoItems.add(item4);
        toDoItems.add(item5);

        toDoListView.getItems().setAll(toDoItems);
        toDoListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }

    @FXML
    public void handleClickListView() {
        ToDoItem item = toDoListView.getSelectionModel().getSelectedItem();
        StringBuilder sb = new StringBuilder(item.getDetails());
        sb.append("\n\n\n\n");
        sb.append("Due: ");
        sb.append(item.getDeadline().toString());
        itemDetailsTextArea.setText(sb.toString());
    }
}