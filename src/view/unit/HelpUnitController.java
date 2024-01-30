package view.unit;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

/**
 * Controller class for help window . It shows a help page that explains how to
 * use the units manage window. The view is defined in HelpUnit.fmxl file and
 * the help page is HelpUnit.html.
 *
 * @author Nerea
 */
public class HelpUnitController {

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
    public void initStage(Parent root) {
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Help");
        stage.setResizable(false);
        stage.setMinWidth(800);
        stage.setMinHeight(600);
        WebEngine webEngine = webView.getEngine();
        //Load help page.
        webEngine.load(getClass().getResource("/view/unit/HelpUnit.html").toExternalForm());
        stage.show();
    }
}
