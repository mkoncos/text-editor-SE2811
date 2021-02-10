package MementoPattern;

public class Originator {
    private String currentState;

    public String getCurrentState(){
        return currentState;
    }

    public Memento saveState(){
        return new Memento(currentState);
    }

    public void setState(Memento memento){
        currentState = memento.getState();
    }
}