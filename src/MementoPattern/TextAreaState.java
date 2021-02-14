package MementoPattern;

import javafx.scene.control.TextArea;

public class TextAreaState {
    private String text;
    private int caretPosition;

    public TextAreaState(TextArea textArea){
        text = textArea.getText();
        caretPosition = textArea.getCaretPosition();
    }

    public void apply(TextArea textArea){
        textArea.setText(text);
        textArea.positionCaret(caretPosition);
    }
}
