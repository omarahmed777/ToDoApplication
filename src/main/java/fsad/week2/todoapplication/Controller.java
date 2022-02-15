package fsad.week2.todoapplication;

import fsad.week2.todoapplication.dataModel.ToDoData;
import fsad.week2.todoapplication.dataModel.ToDoItem;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextArea;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Controller {
    private List<ToDoItem> toDoItems = new ArrayList<>();

    @FXML
    private ListView<ToDoItem> toDoListView;
    @FXML
    private TextArea itemDetailsTextArea;
    @FXML
    private Label deadlineLabel;

    @FXML
    public void initialize() {
        ToDoItem item1 = new ToDoItem("Tidy up", "Clean room, make bed, fold clothes",
                LocalDate.of(2022, Month.JULY, 17));
        ToDoItem item2 = new ToDoItem("Get car MOTd", "Get your car MOTd or get towed",
                LocalDate.of(2022, Month.APRIL, 10));
        ToDoItem item3 = new ToDoItem("Uni Work", "FSD, TOC, AI assignment work",
                LocalDate.of(2022, Month.SEPTEMBER, 01));
        ToDoItem item4 = new ToDoItem("Plan birthday party", "Send out invites and book venue",
                LocalDate.of(2022, Month.MARCH, 05));
        ToDoItem item5 = new ToDoItem("Grocery Shopping", "Weekly shopping at Tesco",
                LocalDate.of(2022, Month.JANUARY, 25));

        toDoItems.add(item1);
        toDoItems.add(item2);
        toDoItems.add(item3);
        toDoItems.add(item4);
        toDoItems.add(item5);
        ToDoData.getInstance().setToDoItems(toDoItems);

        toDoListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<ToDoItem>() {
            @Override
            public void changed(ObservableValue<? extends ToDoItem> observable, ToDoItem oldValue, ToDoItem newValue) {
                if (newValue != null) {
                    ToDoItem item = toDoListView.getSelectionModel().getSelectedItem();
                    itemDetailsTextArea.setText(item.getDetails());
                    DateTimeFormatter df = DateTimeFormatter.ofPattern("MMMM d, yyyy");
                    //deadlineLabel.setText(item.getDeadline().toString());
                    deadlineLabel.setText(df.format(item.getDeadline()));
                }
            }
        });

        toDoListView.getItems().setAll(ToDoData.getInstance().getToDoItems());
        toDoListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        toDoListView.getSelectionModel().selectFirst();
    }

    /*
    @FXML
    public void handleClickListView() {
        ToDoItem item = toDoListView.getSelectionModel().getSelectedItem();
        StringBuilder sb = new StringBuilder(item.getDetails());
        sb.append("\n\n\n\n");
        sb.append("Due: ");
        sb.append(item.getDeadline().toString());
        itemDetailsTextArea.setText(sb.toString());
    }
     */
}