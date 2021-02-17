/*
 * SE 2811- Presentation
 * Controller Class
 * Matej Koncos, Ian Gresser, Garin Jankowski
 */
import MementoPattern.Caretaker;
import MementoPattern.Memento;
import MementoPattern.Originator;
import MementoPattern.TextAreaState;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.util.Timer;
import java.util.TimerTask;

/**
 * This class controls the flow of the user edits on the text
 */
public class Controller {

    @FXML
    private AnchorPane anchor;
    @FXML
    private VBox vbox;
    @FXML
    private TextArea textArea;
    @FXML
    private ColorPicker colorPicker;
    @FXML
    private TextField sizeField;
    @FXML
    private ChoiceBox<String> choiceBox;
    @FXML
    private CheckMenuItem boldCheck;
    @FXML
    private CheckMenuItem italicCheck;

    private Originator originator;
    private Caretaker caretaker;

    private boolean shouldCreateNewState = true;
    private boolean shouldDetectKeys = true;

    /**
     * Initializes the GUI
     */
    public void initialize(){
        vbox.prefWidthProperty().bind(anchor.widthProperty());
        vbox.prefHeightProperty().bind(anchor.heightProperty());

        initializeStateSaver(0.5);
        initializeKeyListener();
        initializeChoiceBoxListener();

        originator = new Originator(new TextAreaState(textArea));
        caretaker = new Caretaker();
    }

    /**
     * Initializes the listener for undo and redo shortcuts, also the listener
     * for new Memento creation.
     */
    private void initializeKeyListener(){
        textArea.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.Z && event.isShortcutDown()){
                event.consume();
                undo();
            } else if (event.getCode() == KeyCode.Y && event.isShortcutDown()){
                event.consume();
                redo();
            }
        });

        textArea.setOnKeyTyped(event -> {
            if(shouldDetectKeys && !event.isShortcutDown()) {
                shouldDetectKeys = false;
                Event.fireEvent(event.getTarget(), event);
                shouldDetectKeys = true;
                event.consume();

                if (shouldCreateNewState) {
                    newState();
                    shouldCreateNewState = false;
                }
                originator.getState().update(textArea);
            }
        });
    }

    /**
     * Initializes the listener for the choice box, allows for swift font change
     */
    private void initializeChoiceBoxListener(){
        String[] fonts = new String[]{
                "Times New Roman",
            "Georgia",
            "Arial",
            "Verdana",
            "Helvetica"
        };
        choiceBox.getItems().addAll(fonts);
        choiceBox.getSelectionModel().selectedIndexProperty().addListener(
            (ObservableValue<? extends Number> ov, Number old_val, Number new_val) -> {
                textArea.setStyle(textArea.getStyle() + "-fx-font-family:" + fonts[new_val.intValue()] + ";");
                newState();
            });
    }

    /**
     * Initializes the schedule for new memento creation
     * @param secondsBetweenSaves the amount of seconds before new memento created
     */
    private void initializeStateSaver(double secondsBetweenSaves){
        new Timer().schedule(
                new TimerTask() {
                    @Override
                    public void run() {
                        shouldCreateNewState = true;
                    }
                }, 0, (long)(secondsBetweenSaves*1000));
    }

    /**
     * Creates a new save using the originator and caretaker of the memento pattern
     */
    private void newState(){
        caretaker.addUndo(originator.saveState());
        originator.setState(new TextAreaState(textArea));
        caretaker.clearRedos();
    }

    /**
     * Performs and undo using the Memento pattern
     */
    @FXML
    private void undo(){
        if(caretaker.hasUndos()) {
            caretaker.addRedo(originator.saveState());

            Memento undo = caretaker.popUndo();
            originator.loadState(undo);
            undo.getState().apply(textArea);
        }
    }

    /**
     * Performs a redo using the Memento pattern
     */
    @FXML
    private void redo(){
        if(caretaker.hasRedos()) {
            caretaker.addUndo(originator.saveState());

            Memento redo = caretaker.popRedo();
            originator.loadState(redo);
            redo.getState().apply(textArea);
        }
    }

    @FXML
    private void bold(){
        if(boldCheck.isSelected()) {
            textArea.setStyle(textArea.getStyle() + "-fx-font-weight: bold;");
        } else {
            textArea.setStyle(textArea.getStyle() + "-fx-font-weight: normal;");
        }
        newState();
    }

    @FXML
    private void italic(){
        if(italicCheck.isSelected()) {
            textArea.setStyle(textArea.getStyle() + "-fx-font-style: italic;");
        } else {
            textArea.setStyle(textArea.getStyle() + "-fx-font-style: normal;");
        }
        newState();
    }

    @FXML
    private void changeColor(){
        textArea.setStyle(textArea.getStyle() + "-fx-text-fill: " + toRgbString(colorPicker.getValue()) + ";");
        newState();
    }

    @FXML
    private void changeSize(){
        textArea.setStyle(textArea.getStyle() + "-fx-font-size: " + sizeField.getText() + ";");
        newState();
    }

    private String toRgbString(Color c) {
        return "rgb("
            + to255Int(c.getRed())
            + "," + to255Int(c.getGreen())
            + "," + to255Int(c.getBlue())
            + ")";
    }

    private int to255Int(double d) {
        return (int) (d * 255);
    }
}
