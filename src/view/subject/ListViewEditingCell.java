package view.subject;

import exceptions.FindErrorException;
import factories.TeacherFactory;
import factories.UserFactory;
import interfaces.TeacherInterface;
import interfaces.UserInterface;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;
import javafx.event.EventHandler;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.MultipleSelectionModel;
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
    private TeacherInterface teacherInterface;

    public ListViewEditingCell() {
        this.listViewTeacher = new ListView<>();
        teacherInterface = TeacherFactory.getModel();
       // User userTeacher = new Teacher();
       
            
        try {
            teachers = FXCollections.observableArrayList(teacherInterface.findAll());
        } catch (FindErrorException ex) {
            Logger.getLogger(ListViewEditingCell.class.getName()).log(Level.SEVERE, null, ex);
        }
           
       
            listViewTeacher.setItems(teachers);
            setGraphic(listViewTeacher);

            listViewTeacher.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

           MultipleSelectionModel<Teacher> teachersModel = listViewTeacher.getSelectionModel();

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
               /*   listViewTeacher.setOnEditCommit((e) -> {
                
            }); */
                } else {
                    StringBuilder teachersNames = new StringBuilder();
                    teachers.forEach(teacher -> {
                        if (teachersNames.length() > 0) {
                            teachersNames.append("\n");
                        }
                        teachersNames.append(teacher.toString());
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
