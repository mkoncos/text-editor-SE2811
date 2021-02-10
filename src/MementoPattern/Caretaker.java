package MementoPattern;

import java.util.LinkedList;

public class Caretaker {
    LinkedList<Memento> undos;
    LinkedList<Memento> redos;

    public Caretaker(){
        undos = new LinkedList<>();
        redos = new LinkedList<>();
    }

    public void addUndo(Memento undo){

        undos.addFirst(undo);
    }

    public void addRedo(Memento redo){
        redos.addFirst(redo);
    }
}