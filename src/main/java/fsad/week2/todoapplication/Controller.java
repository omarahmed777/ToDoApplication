package fsad.week2.todoapplication;

import fsad.week2.todoapplication.dataModel.ToDoData;
import fsad.week2.todoapplication.dataModel.ToDoItem;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.util.Callback;

import java.io.IOException;
import java.security.KeyException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public class Controller {
    //private List<ToDoItem> toDoItems;
    private Predicate<ToDoItem> allItems;
    private Predicate<ToDoItem> todayItem;

    @FXML
    private ListView<ToDoItem> toDoListView;
    @FXML
    private TextArea itemDetailsTextArea;
    @FXML
    private Label deadlineLabel;
    @FXML
    private BorderPane mainBorderPane;
    @FXML
    private ContextMenu listContextMenu;
    @FXML
    private ToggleButton filterToggleButton;
    private FilteredList<ToDoItem> filteredList;
    @FXML
    private TextField shortDescrip;

    @FXML
    public void initialize() {

        listContextMenu = new ContextMenu();
        MenuItem editMenuItem = new MenuItem("Edit");
        MenuItem deleteMenuItem = new MenuItem("Delete");
        deleteMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                ToDoItem item = toDoListView.getSelectionModel().getSelectedItem();
                deleteItem(item);
            }
        });

        editMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                ToDoItem item = toDoListView.getSelectionModel().getSelectedItem();
                editItem(item);
            }
        });
        listContextMenu.getItems().addAll(editMenuItem);
        listContextMenu.getItems().addAll(deleteMenuItem);

        filteredList = new FilteredList<>(ToDoData.getInstance().getToDoItems(),
                new Predicate<ToDoItem>() {
                    @Override
                    public boolean test(ToDoItem toDoItem) { return true; }
                });
        SortedList<ToDoItem> sortedList = new SortedList<>(filteredList,
                new Comparator<ToDoItem>() {
                    @Override
                    public int compare(ToDoItem o1, ToDoItem o2) {
                        return o1.getDeadline().compareTo(o2.getDeadline());
                    }
                });

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

        toDoListView.setItems(sortedList);
        toDoListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        toDoListView.getSelectionModel().selectFirst();

        toDoListView.setCellFactory(new Callback<ListView<ToDoItem>, ListCell<ToDoItem>>() {
            @Override
            public ListCell<ToDoItem> call(ListView<ToDoItem> param) {
                ListCell<ToDoItem> cell = new ListCell<>() {
                    @Override
                    protected void updateItem(ToDoItem item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setText(null);
                        } else {
                            setText(item.getShortDescription());
                            if (item.getDeadline().isBefore(LocalDate.now().plusDays(1))) {
                                setTextFill(Color.RED);
                            } else if (item.getDeadline().equals(LocalDate.now().plusDays(1))) {
                                setTextFill(Color.GREEN);
                            } else {
                                setTextFill(Color.BLACK);
                            }
                        }
                    }
                };
                cell.emptyProperty().addListener(
                        (obs, wasEmpty, isNowEmpty) -> {
                            if (isNowEmpty) {
                                cell.setContextMenu(null);
                            } else {
                                cell.setContextMenu(listContextMenu);
                            }
                        });
                cell.editingProperty().addListener(new ChangeListener<Boolean>() {
                    @Override
                    public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                        if (!oldValue.equals(newValue))
                            cell.setContextMenu(listContextMenu);
                    }
                });
                return cell;
            }
        });
    }

    @FXML
    public void showItemDialog() {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(mainBorderPane.getScene().getWindow());
        dialog.setTitle("To add an item:");
        dialog.setHeaderText("Create a new item do do: =>");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("todoitem-dialog.fxml"));
        try {
            dialog.getDialogPane().setContent(fxmlLoader.load());
        } catch (IOException e) {
            System.out.println("Could not load the dialog");
            e.printStackTrace();
            return;
        }
        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            DialogController controller = fxmlLoader.getController();
            ToDoItem newItem = controller.processResults();
            toDoListView.getSelectionModel().select(newItem);
        }
    }

    public void editItem(ToDoItem item) {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(mainBorderPane.getScene().getWindow());
        dialog.setTitle("Edit an item:");
        dialog.setHeaderText("Edit an existing item: =>" + item.getShortDescription());
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("todoitem-dialog.fxml"));
        try {
            dialog.getDialogPane().setContent(fxmlLoader.load());
        } catch (IOException e) {
            System.out.println("Could not load the dialog");
            e.printStackTrace();
            return;
        }
        DialogController dc = fxmlLoader.getController();
        dc.setToEdit(item);

        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
        Optional<ButtonType> result = dialog.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            ToDoItem changeItem = dc.processEditResults(item);
            toDoListView.getSelectionModel().select(changeItem);
        }
    }

    @FXML
    public void handleKeyPressed(KeyEvent keyEvent) {
        ToDoItem selectedItem = toDoListView.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            if (keyEvent.getCode().equals(KeyCode.DELETE)) {
                deleteItem(selectedItem);
            }
        }
    }

    public void deleteItem(ToDoItem item) {
        Alert alert = new Alert((Alert.AlertType.CONFIRMATION));
        alert.setTitle("Delete an item");
        alert.setHeaderText("Item to be deleted is: " + item.getShortDescription());
        alert.setContentText("Are you sure you would like to delete the item?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            ToDoData.getInstance().deleteToDoItem(item);
        }
    }

    @FXML
    public void handleToggle() {
        if (filterToggleButton.isSelected()) {
            filteredList.setPredicate(new Predicate<ToDoItem>() {
                @Override
                public boolean test(ToDoItem item) {
                    return (item.getDeadline().equals(LocalDate.now()));
                }
            });
        } else {
            filteredList.setPredicate(new Predicate<ToDoItem>() {
                @Override
                public boolean test(ToDoItem item) {
                    return true;
                }
            });
        }
    }

    @FXML
    public void handleExit() {
        Platform.exit();
    }

    @FXML
    public void handleClickListView() {
        ToDoItem item = toDoListView.getSelectionModel().getSelectedItem();
        itemDetailsTextArea.setText(item.getDetails());
        deadlineLabel.setText(item.getDeadline().toString());
    }

}