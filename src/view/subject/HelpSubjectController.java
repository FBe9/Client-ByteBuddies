package view.subject;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * Controller class for help window . It shows a help page that explains how to
 * use the user's data management window. The view is defined in
 * HelpSubject.fmxl file and the help page is HelpSubject.html.
 *
 * @author Irati
 */
public class HelpSubjectController {

    /**
     * The control that shows the help page.
     */
    @FXML
    private WebView webView;

    /**
     * Initializes and show the help window.
     *
     * @param root The FXML document hierarchy root.
     */
    public void initialize(Parent root) {
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Help Subject");
        //Se añadirá a la ventana el icono de una estrella.
        stage.getIcons().add(new Image("resources/Logo.jpg"));
        stage.setResizable(false);
        stage.setMinWidth(800);
        stage.setMinHeight(600);
        stage.setOnShowing(this::handleWindowShowing);
        stage.show();
    }

    /**
     * Initializes window state. It implements behavior for WINDOW_SHOWING type
     * event.
     *
     * @param event The window event
     */
    private void handleWindowShowing(WindowEvent event) {
        WebEngine webEngine = webView.getEngine();
        //Load help page.
        webEngine.load(getClass().getResource("/view/subject/HelpSubject.html").toExternalForm());
    }
}
