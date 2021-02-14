import MementoPattern.Caretaker;
import MementoPattern.Memento;
import MementoPattern.Originator;
import MementoPattern.TextAreaState;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.util.Timer;
import java.util.TimerTask;

public class Controller {

    @FXML
    public AnchorPane anchor;
    public VBox vbox;
    public TextArea textArea;
    public ColorPicker colorPicker;

    public Originator originator;
    public Caretaker caretaker;

    private boolean shouldCreateNewState = true;

    public void initialize(){
        vbox.prefWidthProperty().bind(anchor.widthProperty());
        vbox.prefHeightProperty().bind(anchor.heightProperty());

        initializeStateSaver(0.5);
        initializeKeyListener();

        originator = new Originator();
        caretaker = new Caretaker();
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
            if(!event.isShortcutDown() && shouldCreateNewState){
                newState();
                shouldCreateNewState = false;
            }
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
        originator.setState(new TextAreaState(textArea));
        caretaker.addUndo(originator.saveState());
        caretaker.clearRedos();
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
    private void changeColor(){
        textArea.setStyle("-fx-text-fill: " + toRgbString(colorPicker.getValue()) + ";");
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
