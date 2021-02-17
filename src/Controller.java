import MementoPattern.Caretaker;
import MementoPattern.Memento;
import MementoPattern.Originator;
import MementoPattern.TextAreaState;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.util.Timer;
import java.util.TimerTask;

public class Controller {

    @FXML
    public AnchorPane anchor;
    public VBox vbox;
    public TextArea textArea;
    public ColorPicker colorPicker;
    public TextField sizeField;
    public ChoiceBox<String> choiceBox;

    public Originator originator;
    public Caretaker caretaker;

    private boolean shouldCreateNewState = true;
    private boolean shouldDetectKeys = true;

    public void initialize(){
        vbox.prefWidthProperty().bind(anchor.widthProperty());
        vbox.prefHeightProperty().bind(anchor.heightProperty());

        initializeStateSaver(0.5);
        initializeKeyListener();
        initializeChoiceBoxListener();

        originator = new Originator(new TextAreaState(textArea));
        caretaker = new Caretaker();
        caretaker.printStackSizes();
    }

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

    private void initializeStateSaver(double secondsBetweenSaves){
        new Timer().schedule(
                new TimerTask() {
                    @Override
                    public void run() {
                        shouldCreateNewState = true;
                    }
                }, 0, (long)(secondsBetweenSaves*1000));
    }

    public void newState(){
        caretaker.addUndo(originator.saveState());
        originator.setState(new TextAreaState(textArea));
        caretaker.clearRedos();

        caretaker.printTopUndo();
    }

    public void undo(){
        if(caretaker.hasUndos()) {
            caretaker.addRedo(originator.saveState());

            Memento undo = caretaker.popUndo();
            originator.loadState(undo);
            undo.getState().apply(textArea);
        }
    }

    public void redo(){
        if(caretaker.hasRedos()) {
            caretaker.addUndo(originator.saveState());

            Memento redo = caretaker.popRedo();
            originator.loadState(redo);
            redo.getState().apply(textArea);
        }
    }

    @FXML
    private void bold(){
        textArea.setStyle(textArea.getStyle() + "-fx-font-weight: bold;");
        newState();
    }

    @FXML
    private void italic(){
        textArea.setStyle(textArea.getStyle() + "-fx-font-style: italic;");
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
