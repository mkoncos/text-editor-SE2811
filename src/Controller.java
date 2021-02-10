import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

public class Controller {

    @FXML
    public AnchorPane anchor;
    public VBox vbox;
    public TextArea textArea;

    public void initialize(){
        vbox.prefWidthProperty().bind(anchor.widthProperty());
        vbox.prefHeightProperty().bind(anchor.heightProperty());
    }
}
