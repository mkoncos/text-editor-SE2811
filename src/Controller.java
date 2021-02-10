import MementoPattern.Caretaker;
import MementoPattern.Originator;
import com.sun.org.apache.xpath.internal.operations.Or;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

public class Controller {

    @FXML
    public AnchorPane anchor;
    public VBox vbox;
    public TextArea textArea;

    public Originator originator;
    public Caretaker caretaker;

    public void initialize(){
        vbox.prefWidthProperty().bind(anchor.widthProperty());
        vbox.prefHeightProperty().bind(anchor.heightProperty());

        originator = new Originator();
        caretaker = new Caretaker();
    }

    public void newState(){
        caretaker.addUndo(originator.saveState());
    }

    public void undo(){
        caretaker.addRedo(originator.saveState());
        originator.setState(caretaker.popUndo());
    }

    public void redo(){
        caretaker.addUndo(originator.saveState());
        originator.setState(caretaker.popRedo());
    }
}
