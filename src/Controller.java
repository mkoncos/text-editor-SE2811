import MementoPattern.Caretaker;
import MementoPattern.Memento;
import MementoPattern.Originator;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
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
        textArea.setOnKeyPressed(new EventHandler<KeyEvent>() {

            @Override
            public void handle(KeyEvent event) {
                if ((event.getCode() == KeyCode.Z || event.getCode() == KeyCode.Y)
                        && event.isShortcutDown()) {
                    event.consume();
                    String textAreaText = textArea.getText();
                    if (event.getCode() == KeyCode.Z && event.isShortcutDown()){
                        System.out.println("we got here");
                        undo();
                        System.out.println("then here");
                    } else if (event.getCode() == KeyCode.Y && event.isShortcutDown()){
                        redo();
                    } else if (event.getCode() == KeyCode.SPACE){
                        newState();
                    }

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
        caretaker.addRedo(originator.saveState());
        originator.setState(caretaker.popUndo());
    }

    public void redo(){
        caretaker.addUndo(originator.saveState());
        originator.setState(caretaker.popRedo());
    }
}
