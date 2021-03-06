/*
 * SE 2811- Presentation
 * Originator Class
 * Matej Koncos, Ian Gresser, Garin Jankowski
 */
package MementoPattern;

/**
 * Holds the current state of the program. Can have its
 * state updated with Mementos and can save its state to
 * a new Memento.
 */
public class Originator {
    private TextAreaState currentState;

    public Originator(TextAreaState currentState){
        this.currentState = currentState;
    }

    public TextAreaState getState(){
        return currentState;
    }

    public void setState(TextAreaState state){
        currentState = state;
    }

    public Memento saveState(){
        return new Memento(currentState);
    }

    public void loadState(Memento memento){
        currentState = memento.getState();
    }
}