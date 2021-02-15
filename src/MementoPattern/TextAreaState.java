package MementoPattern;

import javafx.scene.control.TextArea;

public class TextAreaState {
    private String style;
    private String text;
    private int caretPosition;

    public TextAreaState(TextArea textArea){
        style = textArea.getStyle();
        text = textArea.getText();
        caretPosition = textArea.getCaretPosition();
    }

    public void apply(TextArea textArea){
        textArea.setStyle(style);
        textArea.setText(text);
        textArea.positionCaret(caretPosition);
    }

    public String toString(){
        return style + "\n" + caretPosition + "\n" + text;
    }
}
