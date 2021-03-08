package main;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.ObservableSet;
import javafx.geometry.Orientation;
import javafx.print.Printer;
import javafx.print.PrinterJob;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {

    private final TextArea source = new TextArea();
    private final TextArea target = new TextArea();
    private final TextArea log = new TextArea();

    @Override
    public void start(Stage primaryStage){

        VBox root = new VBox();
        root.setPrefSize(900, 500);

        // Menu
        MenuBar mainMenu = new MenuBar();
        Menu datei = new Menu("Datei");
        Menu bearbeiten = new Menu("Bearbeiten");
        MenuItem schliessen = new MenuItem("Schließen");
        schliessen.setOnAction(event -> Platform.exit());
        MenuItem drucken = new MenuItem("Drucken");
        drucken.setAccelerator(new KeyCodeCombination(KeyCode.D, KeyCombination.CONTROL_DOWN));
        drucken.setOnAction(event -> print());
        MenuItem ausschneiden = new MenuItem("Ausschneiden");
        ausschneiden.setAccelerator(new KeyCodeCombination(KeyCode.X, KeyCombination.CONTROL_DOWN));
        MenuItem kopieren = new MenuItem("Kopieren");
        kopieren.setAccelerator(new KeyCodeCombination(KeyCode.C, KeyCombination.CONTROL_DOWN));
        MenuItem einfuegen = new MenuItem("Einfügen");
        einfuegen.setAccelerator(new KeyCodeCombination(KeyCode.V, KeyCombination.CONTROL_DOWN));
        datei.getItems().addAll(schliessen, drucken);
        bearbeiten.getItems().addAll(ausschneiden, kopieren, einfuegen);
        mainMenu.getMenus().addAll(datei, bearbeiten);

        // Toolbar
        ToolBar toolBar = new ToolBar();
        Button schliessenTool = new Button("Schließen");
        schliessenTool.setOnMouseClicked(event -> Platform.exit());
        Button druckenTool = new Button("Drucken");
        druckenTool.setOnMouseClicked(event -> print());
        Button ausschneidenTool = new Button("Ausschneiden");
        Button kopierenTool = new Button("Kopieren");
        Button einfuegenTool = new Button("Einfügen");
        toolBar.getItems().addAll(schliessenTool, druckenTool, new Separator(), ausschneidenTool, kopierenTool, einfuegenTool);
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

        source.setPrefSize(300, 300);
        source.setMaxSize(300, 300);
        source.setLayoutX(100);
        source.setLayoutY(50);

        source.setOnDragDetected(event -> {
            Dragboard db = source.startDragAndDrop
                    (TransferMode.ANY);
            ClipboardContent content = new ClipboardContent();
            content.putString(source.getText());
            db.setContent(content);
            event.consume();
        });

        ContextMenu sourceMenu = new ContextMenu();
        MenuItem sourceAusschneiden = new MenuItem("Ausschneiden");
        sourceAusschneiden.setOnAction(event -> handleAusschneiden());
        MenuItem sourceKopieren = new MenuItem("Kopieren");
        sourceKopieren.setOnAction(event -> handleKopieren());
        sourceMenu.getItems().addAll(sourceAusschneiden, sourceKopieren);
        source.setContextMenu(sourceMenu);

        target.setPrefSize(300, 300);
        target.setMaxSize(300, 300);
        target.setLayoutX(500);
        target.setLayoutY(50);

        target.setOnDragOver(event -> {
            if (event.getGestureSource() != target && event.getDragboard().hasString()) {
                event.acceptTransferModes(TransferMode.ANY);
                target.setText(event.getDragboard().getString());
            }
            event.consume();
        });

        ContextMenu targetMenu = new ContextMenu();
        MenuItem targetEinfuegen = new MenuItem("Einfügen");
        targetEinfuegen.setOnAction(event -> handleEinfuegen());
        targetMenu.getItems().addAll(targetEinfuegen);
        target.setContextMenu(targetMenu);

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

        // EventHandlers
        ausschneiden.setOnAction(event -> handleAusschneiden());
        ausschneidenTool.setOnMouseClicked(event -> handleAusschneiden());

        kopieren.setOnAction(event -> handleKopieren());
        kopierenTool.setOnMouseClicked(event -> handleKopieren());

        einfuegen.setOnAction(event -> handleEinfuegen());
        einfuegenTool.setOnMouseClicked(event -> handleEinfuegen());

        // Finish scene
        Scene scene = new Scene(root, root.getPrefWidth(), root.getPrefHeight());
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Zeichenprogramm");
        primaryStage.show();

    }

    public void handleAusschneiden(){
        log.appendText("Cut text from Source Area." + System.lineSeparator());
        source.setText("");
    }

    public void handleKopieren(){
        log.appendText("Copied '" + source.getSelectedText() + "' from Source." + System.lineSeparator());
        ClipboardContent content = new ClipboardContent();
        content.putString(source.getSelectedText());
        Clipboard.getSystemClipboard().setContent(content);
    }

    public void handleEinfuegen(){
        log.appendText("Pasted '" + Clipboard.getSystemClipboard().getString() + "' from Source to Target." + System.lineSeparator());
        target.setText(Clipboard.getSystemClipboard().getString());
    }

    private void print() {
        log.appendText("Creating a printer job..." + System.lineSeparator());
        PrinterJob job = PrinterJob.createPrinterJob();

        if (job != null) {
            if (job.printPage(source)) {
                job.endJob();
            } else {
                log.appendText("Printing failed." + System.lineSeparator());
            }
        } else {
            log.appendText("Could not create a printer job." + System.lineSeparator());
        }
    }
}
