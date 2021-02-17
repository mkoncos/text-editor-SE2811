/*
 * SE 2811- Presentation
 * Memento Class
 * Matej Koncos, Ian Gresser, Garin Jankowski
 */
package MementoPattern;

/**
 * Holds a TextAreaState. Multiple Mementos are
 * kept in the Caretaker and reapplied  to the
 * Originator when needed.
 */
public class Memento {
    private TextAreaState state;

    public Memento(TextAreaState state){
        this.state = state;
    }

    public TextAreaState getState() {
        return state;
    }

    public void setState(TextAreaState state) {
        this.state = state;
    }

}
