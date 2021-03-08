package main;

import javafx.application.Application;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage){

        BorderPane root = new BorderPane();
        root.setPrefSize(900, 500);

        MenuBar mainMenu = new MenuBar();

        Menu datei = new Menu("Datei");
        Menu bearbeiten = new Menu("Bearbeiten");

        MenuItem schliessen = new MenuItem("Schließen");
        MenuItem ausschneiden = new MenuItem("Ausschneiden");
        MenuItem kopieren = new MenuItem("Kopieren");
        MenuItem einfuegen = new MenuItem("Einfügen");

        datei.getItems().add(schliessen);
        bearbeiten.getItems().addAll(ausschneiden, kopieren, einfuegen);

        mainMenu.getMenus().addAll(datei, bearbeiten);

        ToolBar toolBar = new ToolBar();
        Button schliessenTool = new Button("Schließen");
        Button ausschneidenTool = new Button("Ausschneiden");
        Button kopierenTool = new Button("Kopieren");
        Button einfuegenTool = new Button("Einfügen");
        toolBar.getItems().addAll(schliessenTool, ausschneidenTool, kopierenTool, einfuegenTool);
        toolBar.setOrientation(Orientation.VERTICAL);
        VBox vBox = new VBox(toolBar);

        //root.setTop(mainMenu);
        root.getChildren().add(vBox);

        Scene scene = new Scene(root, root.getPrefWidth(), root.getPrefHeight());
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Zeichenprogramm");
        primaryStage.show();

    }
}
