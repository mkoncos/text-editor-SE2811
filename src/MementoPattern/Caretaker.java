/*
 * SE 2811- Presentation
 * CareTaker Class
 * Matej Koncos, Ian Gresser, Garin Jankowski
 */
package MementoPattern;

import java.util.LinkedList;

public class Caretaker {
    private final int MAX_UNDO_AMOUNT = 50;
    private LinkedList<Memento> undos;
    private LinkedList<Memento> redos;

    public Caretaker(){
        undos = new LinkedList<>();
        redos = new LinkedList<>();
    }

    public void addUndo(Memento undo){
        undos.addFirst(undo);
        if(undos.size() > MAX_UNDO_AMOUNT){
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

    /**
     * for debugging
     */
    public void printStackSizes(){
        String r = ":";
        for(int i = 0; i < redos.size();i++){
            r += "|";
        }

        String u = ":";
        for(int i = 0; i < undos.size();i++){
            u += "|";
        }

        System.out.println("U/R");
        System.out.println(u);
        System.out.println(r);
    }
}