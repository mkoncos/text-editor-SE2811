/*
 * SE 2811- Presentation
 * Main Class
 * Matej Koncos, Ian Gresser, Garin Jankowski
 */
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("text-editor.fxml"));
        primaryStage.setTitle("Text Editor");
        primaryStage.setScene(new Scene(root, 500, 500));
        primaryStage.show();
    }
}
