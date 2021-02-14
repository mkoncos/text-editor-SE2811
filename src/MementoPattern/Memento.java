package MementoPattern;

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
