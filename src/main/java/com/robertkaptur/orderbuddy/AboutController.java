package com.robertkaptur.orderbuddy;

import javafx.fxml.FXML;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;

public class AboutController {

    // Fields
    @FXML
    private Label appDetailsContentLabel;
    @FXML
    private Label authorsContentLabel;
    @FXML
    private Label contactFirstLineLabel;
    @FXML
    private Hyperlink contactFirstLineHyperlink;
    @FXML
    private Label contactSecondLineLabel;
    @FXML
    private Hyperlink contactSecondLineHyperlink;

    // Strings
    final String appDetailsContent = "Simple application created to manage orders within i.e. some small fast-food company. \n" +
            "Also this app is my first real-life project, for portfolio, after learning basics of programming in Java. \n\n" +
            "Current version: alpha \n" +
            "Established on 14/03/2022";
    final String authorsContent = "Robert Kaptur - Main Developer, Idea, Tests, Design";
    final String contactFirstLineLabelContent = "Robert Kaptur";
    final String contactFirstLineHyperlinkContent = "rob.kaptur@gmail.com";
    final String contactSecondLineLabelContent = "Project's GitHub";
    final String contactSecondLineHyperlinkContent = "https://github.com/robkap94/OrderBuddy";

    public void initialize() {
        appDetailsContentLabel.setText(appDetailsContent);
        authorsContentLabel.setText(authorsContent);
        contactFirstLineLabel.setText(contactFirstLineLabelContent);
        contactFirstLineHyperlink.setText(contactFirstLineHyperlinkContent);
        contactSecondLineLabel.setText(contactSecondLineLabelContent);
        contactSecondLineHyperlink.setText(contactSecondLineHyperlinkContent);
    }
}
