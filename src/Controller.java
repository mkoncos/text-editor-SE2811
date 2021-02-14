import MementoPattern.Caretaker;
import MementoPattern.Memento;
import MementoPattern.Originator;
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

public class Controller {

    @FXML
    public AnchorPane anchor;
    public VBox vbox;
    public TextArea textArea;
    public ColorPicker colorPicker;

    public Originator originator;
    public Caretaker caretaker;

    public void initialize(){
        vbox.prefWidthProperty().bind(anchor.widthProperty());
        vbox.prefHeightProperty().bind(anchor.heightProperty());
        textArea.setOnKeyPressed(new EventHandler<KeyEvent>() {

            @Override
            public void handle(KeyEvent event) {
                if ((event.getCode() == KeyCode.Z || event.getCode() == KeyCode.Y)
                        && event.isShortcutDown()) {
                    event.consume();
                    if (event.getCode() == KeyCode.Z && event.isShortcutDown()){
                        System.out.println("we got here");
                        undo();
                        System.out.println("then here");
                    } else if (event.getCode() == KeyCode.Y && event.isShortcutDown()){
                        redo();
                    }

                } else if (event.getCode() == KeyCode.SPACE || event.getCode() == KeyCode.ENTER) {
                    newState();
                }

            }
        });

        originator = new Originator();
        caretaker = new Caretaker();
    }

    public void newState(){
        originator.setState(new Memento(textArea.getText()));
        caretaker.addUndo(originator.saveState());
        caretaker.clearRedos();
    }

    public void undo(){
        if(caretaker.hasUndos()) {
            caretaker.addRedo(originator.saveState());
            originator.setState(caretaker.popUndo());
        }
    }

    public void redo(){
        if(caretaker.hasRedos()) {
            caretaker.addUndo(originator.saveState());
            originator.setState(caretaker.popRedo());
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
