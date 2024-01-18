package view.subject;

import exceptions.FindErrorException;
import factories.UserFactory;
import interfaces.UserInterface;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableCell;
import javafx.util.Callback;
import models.Subject;
import models.Teacher;
import models.User;

/**
 * TableCell implementation for editing an ObservableSet of Teachers with a
 * ListView. Displays teacher names in a ListView when not in editing mode.
 */
public class ListViewEditingCell extends TableCell<Subject, ObservableSet<Teacher>> {

    private final ListView<Teacher> listViewTeacher;
    private ObservableList<User> users;
    private ObservableList<Teacher> teachers;
    private UserInterface userInterface;

    public ListViewEditingCell() {
        this.listViewTeacher = new ListView<>();
        userInterface = UserFactory.getModel();
        User userTeacher = new Teacher();
        try {
            
            users = FXCollections.observableArrayList(userInterface.findAll());
            for (User user : users) {
                if (user instanceof Teacher) {
                    // Casting de User a Teacher
                    Teacher teacher = (Teacher) user;
                    teachers.add(teacher);
                }
            }
            }catch (FindErrorException ex) {
            Logger.getLogger(ListViewEditingCell.class.getName()).log(Level.SEVERE, null, ex);
        }
            listViewTeacher.setItems(teachers);
            setGraphic(listViewTeacher);

            listViewTeacher.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

            listViewTeacher.setCellFactory(new Callback<ListView<Teacher>, ListCell<Teacher>>() {
                @Override
                public ListCell<Teacher> call(ListView<Teacher> param) {
                    return new ListCell<Teacher>() {
                        @Override
                        protected void updateItem(Teacher item, boolean empty) {
                            super.updateItem(item, empty);
                            if (empty || item == null) {
                                setText(null);
                            } else {
                                setText(item.getName() + " " + item.getSurname());
                            }
                        }
                    };
                }
            });

        }

        @Override
        protected void updateItem
        (ObservableSet<Teacher> teachers, boolean empty
        
            ) {
        super.updateItem(teachers, empty);

            if (empty) {
                setText(null);
                setGraphic(null);
            } else {

                if (isEditing()) {
                    setGraphic(listViewTeacher);
                } else {
                    StringBuilder teachersNames = new StringBuilder();
                    teachers.forEach(teacher -> {
                        if (teachersNames.length() > 0) {
                            teachersNames.append("\n");
                        }
                        teachersNames.append(teacher.getName() + " " + teacher.getSurname());
                    });
                    setText(teachersNames.toString());
                    setGraphic(null);
                }
            }
        }

        @Override
        public void startEdit
        
            () {
        super.startEdit();
            setGraphic(listViewTeacher);
            listViewTeacher.requestFocus();
        }

        @Override
        public void cancelEdit
        
            () {
        super.cancelEdit();
            setText(getItem().toString());
            setGraphic(null);
        }
    }
