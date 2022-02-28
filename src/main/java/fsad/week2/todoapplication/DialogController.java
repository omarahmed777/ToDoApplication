package fsad.week2.todoapplication;

import fsad.week2.todoapplication.dataModel.ToDoData;
import fsad.week2.todoapplication.dataModel.ToDoItem;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.time.LocalDate;

public class DialogController {
    @FXML
    private TextField shortDescrip;
    @FXML
    private TextArea details;
    @FXML
    private DatePicker deadline;

    public ToDoItem processResults() {
        String shortS = shortDescrip.getText().trim();
        String detailsS = details.getText().trim();
        LocalDate deadlineS = deadline.getValue();

        ToDoItem newItem = new ToDoItem(shortS, detailsS, deadlineS);
        ToDoData.getInstance().addToDoItem(newItem);
        return newItem;
    }

}
