package Model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Pipe {
    private BooleanProperty isUpset;

    private StringProperty diameterPipe;

    private StringProperty wallThicknessPipe;

    @Override
    public String toString() {
        String upsetLetter = isUpset.get() ? "В" : "Н";
        return String.format("НКТ%s-%sx%s", upsetLetter, diameterPipe.get(), wallThicknessPipe.get());
    }

    public boolean isUpset() {
        return isUpset.get();
    }

    public void setUpset(boolean upset) {
        isUpset = new SimpleBooleanProperty(upset);
    }

    public String getDiameterPipe() {
        return diameterPipe.get();
    }

    public void setDiameterPipe(String diameterPipe) {
        this.diameterPipe = new SimpleStringProperty(diameterPipe);
    }

    public String getWallThicknessPipe() {
        return wallThicknessPipe.get();
    }

    public void setWallThicknessPipe(String wallThicknessPipe) {
        this.wallThicknessPipe = new SimpleStringProperty(wallThicknessPipe);
    }

    public Pipe() {
    }

    public Pipe(String diameterPipe, String wallThicknessPipe, boolean isUpset) {
        this.diameterPipe = new SimpleStringProperty(diameterPipe);
        this.wallThicknessPipe = new SimpleStringProperty(wallThicknessPipe);
        this.isUpset = new SimpleBooleanProperty(isUpset);
    }
}
