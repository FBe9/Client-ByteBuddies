package view.exam;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

/**
 * Controller class for the Exam help window.
 * 
 * @author Alex
 */
public class HelpExamController {

    /**
     * The control used to show the page.
     */
    @FXML
    WebView webView;

    /**
     * Initialises and shows the window .
     * 
     * @param root The root of the FXML hierarchy.
     */
    public void initialize(Parent root) {
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Help");
        stage.setResizable(false);
        stage.setMinWidth(800);
        stage.setMinHeight(600);
        stage.setOnShowing(event -> {
            WebEngine webEngine = webView.getEngine();
            webEngine.load(getClass().getResource("/view/exam/HelpExam.html").toExternalForm());
        });
        stage.show();
    }
}
