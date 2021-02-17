/*
 * SE 2811- Presentation
 * Originator Class
 * Matej Koncos, Ian Gresser, Garin Jankowski
 */
package MementoPattern;

public class Originator {
    private TextAreaState currentState;

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