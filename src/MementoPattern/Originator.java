/*
 * SE 2811- Presentation
 * Originator Class
 * Matej Koncos, Ian Gresser, Garin Jankowski
 */
package MementoPattern;

import java.awt.*;

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