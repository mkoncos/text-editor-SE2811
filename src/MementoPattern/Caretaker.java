package MementoPattern;

import java.util.LinkedList;

public class Caretaker {
    private final int MAX_UNDO_AMOUNT = 50;
    private int undoSize;
    private LinkedList<Memento> undos;
    private LinkedList<Memento> redos;

    public Caretaker(){
        undos = new LinkedList<>();
        redos = new LinkedList<>();
    }

    public void addUndo(Memento undo){
        undos.addFirst(undo);
        undoSize++;
        if(undoSize > MAX_UNDO_AMOUNT){
            undos.removeLast();
        }
    }

    public boolean hasUndos(){
        return !undos.isEmpty();
    }

    public boolean hasRedos(){
        return !redos.isEmpty();
    }

    public Memento popUndo(){
        return undos.removeFirst();
    }

    public void addRedo(Memento redo){
        redos.addFirst(redo);
    }

    public Memento popRedo(){
        return redos.removeFirst();
    }

    public void clearRedos(){
        redos.clear();
    }
}