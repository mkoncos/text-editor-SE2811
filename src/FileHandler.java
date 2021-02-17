/*
 * SE 2811- Presentation
 * FileHandler Class
 * Matej Koncos, Ian Gresser, Garin Jankowski
 */
import MementoPattern.Caretaker;
import MementoPattern.Originator;
import javafx.scene.control.TextArea;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;

public class FileHandler {
    private TextArea textArea;
    private Originator originator;
    private Caretaker caretaker;

    public FileHandler(TextArea textArea, Originator originator, Caretaker caretaker){
        this.textArea = textArea;
        this.originator = originator;
        this.caretaker = caretaker;
    }

    public void writeFile(File file) throws IOException {
        String path = file.getAbsolutePath();
        String text = textArea.getText();

        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(path))) {
            writer.write(text);
        }
    }

    public void readFile(File file) throws IOException{
        String path = file.getAbsolutePath();
        String text;

        try (BufferedReader reader = Files.newBufferedReader(Paths.get(path))){
            text = reader.lines().collect(Collectors.joining());
        }
        textArea.setText(text);
    }

}
