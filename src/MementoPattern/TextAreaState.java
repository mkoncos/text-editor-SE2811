/*
 * SE 2811- Presentation
 * TextAreaState Class
 * Matej Koncos, Ian Gresser, Garin Jankowski
 */
package MementoPattern;

import javafx.scene.control.TextArea;

public class TextAreaState {
    private String style;
    private String text;
    private int caretPosition;

    public TextAreaState(TextArea textArea){
        update(textArea);
    }

    public void apply(TextArea textArea){
        textArea.setText(text);
        textArea.positionCaret(caretPosition);
        textArea.setStyle(style);
    }

    public void update(TextArea textArea){
        style = textArea.getStyle();
        text = textArea.getText();
        caretPosition = textArea.getCaretPosition();
    }

    public String toString(){
        return style + "\n" + caretPosition + "\n" + text;
    }
}
