package Main;
import NoteSystem.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import javax.swing.*;
import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.synth.SynthTextAreaUI;
import java.awt.*;
import java.io.*;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.time.format.DateTimeParseException;

import static java.awt.Color.BLACK;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
//        ManageNote noteTakingSystem = new ManageNote();
//        noteTakingSystem.start();
    }


    @Override
    public void start(Stage stage) throws Exception {
// Load the FXML file
        Parent root = FXMLLoader.load(getClass().getResource("Main.fxml"));

        // Create a new scene
        Scene scene = new Scene(root);
        // Set the scene on the primary stage
        //scene.getStylesheets().add(MainController.class.getResource("styles.css").toExternalForm());
        stage.setScene(scene);

        //scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());

        // Set the stage title
        stage.setTitle("Note Taking System");

        // Show the primary stage
        stage.show();
    }
}