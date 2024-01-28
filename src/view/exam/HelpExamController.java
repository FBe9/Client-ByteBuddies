/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.exam;

import view.subject.*;
import view.exercise.*;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * Controller class for help window . 
 * It shows a help page that explains how to use the user's data management window.
 * The view is defined in HelpExam.fmxl file and the help page is HelpExam.html.
 *
 * @author Olivia
 */
public class HelpExamController {
    /**
     * The control that shows the help page.
     */
    @FXML
    private WebView webView;
    /**
     * Initializes and show the help window.
     * @param root The FXML document hierarchy root. 
     */
    public void initialize(Parent root) {
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Help");
        stage.setResizable(false);
        stage.setMinWidth(800);
        stage.setMinHeight(600);
        stage.setOnShowing(this::handleWindowShowing);
        stage.show();
    }
    /**
     * Initializes window state. It implements behavior for WINDOW_SHOWING type 
     * event.
     * @param event  The window event 
     */
    private void handleWindowShowing(WindowEvent event){
        WebEngine webEngine = webView.getEngine();
        //Load help page.
        webEngine.load(getClass().getResource("/view/exam/HelpExam.html").toExternalForm());
    }
}