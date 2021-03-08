package main;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage){

        VBox root = new VBox();
        root.setPrefSize(900, 500);

        // Menu
        MenuBar mainMenu = new MenuBar();
        Menu datei = new Menu("Datei");
        Menu bearbeiten = new Menu("Bearbeiten");
        MenuItem schliessen = new MenuItem("Schließen");
        schliessen.setOnAction(actionEvent -> Platform.exit());
        MenuItem ausschneiden = new MenuItem("Ausschneiden");
        MenuItem kopieren = new MenuItem("Kopieren");
        MenuItem einfuegen = new MenuItem("Einfügen");
        datei.getItems().add(schliessen);
        bearbeiten.getItems().addAll(ausschneiden, kopieren, einfuegen);
        mainMenu.getMenus().addAll(datei, bearbeiten);

        // Toolbar
        ToolBar toolBar = new ToolBar();
        Button schliessenTool = new Button("Schließen");
        schliessenTool.setOnMouseClicked(mouseEvent -> Platform.exit());
        Button ausschneidenTool = new Button("Ausschneiden");
        Button kopierenTool = new Button("Kopieren");
        Button einfuegenTool = new Button("Einfügen");
        toolBar.getItems().addAll(schliessenTool, new Separator(), ausschneidenTool, kopierenTool, einfuegenTool);
        toolBar.setOrientation(Orientation.HORIZONTAL);
        HBox toolBox = new HBox(toolBar);
        toolBar.setPrefWidth(root.getPrefWidth());
        toolBox.setLayoutY(30);

        // TabPanes
        TabPane tabPane = new TabPane();
        Tab sourceTarget = new Tab("Source/Target");
        Tab logging = new Tab("Logging");
        sourceTarget.setClosable(false);
        logging.setClosable(false);

        TextArea source = new TextArea();
        source.setPrefSize(300, 300);
        source.setMaxSize(300, 300);
        source.setLayoutX(100);
        source.setLayoutY(50);

        TextArea target = new TextArea();
        target.setPrefSize(300, 300);
        target.setMaxSize(300, 300);
        target.setLayoutX(500);
        target.setLayoutY(50);

        TextArea log = new TextArea();
        log.setPrefSize(600, 300);
        log.setMaxSize(600, 300);
        log.setLayoutX(150);
        log.setLayoutY(50);

        Pane paneSourceTarget = new Pane(source, target);
        Pane paneLogging = new Pane(log);
        sourceTarget.setContent(paneSourceTarget);
        logging.setContent(paneLogging);

        tabPane.getTabs().addAll(sourceTarget, logging);

        // Add Menu, Toolbar to root
        root.getChildren().addAll(mainMenu, toolBox, tabPane);

        // Finish scene
        Scene scene = new Scene(root, root.getPrefWidth(), root.getPrefHeight());
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Zeichenprogramm");
        primaryStage.show();

    }
}
