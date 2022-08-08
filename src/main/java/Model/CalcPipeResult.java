package Model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class CalcPipeResult {
    private final StringProperty namePipe;
    private final StringProperty weightPipe;
    private final StringProperty lengthPipe;
    private final StringProperty countPipe;

    public CalcPipeResult(){
        this(null,null,null,null);
    }

    public CalcPipeResult(String namePipe, String weightPipe, String lengthPipe, String countPipe) {
        this.namePipe = new SimpleStringProperty(namePipe);
        this.weightPipe = new SimpleStringProperty(weightPipe);
        this.lengthPipe = new SimpleStringProperty(lengthPipe);
        this.countPipe = new SimpleStringProperty(countPipe);
    }

    public String getNamePipe() {
        return namePipe.get();
    }

    public StringProperty namePipeProperty() {
        return namePipe;
    }

    public void setNamePipe(String namePipe) {
        this.namePipe.set(namePipe);
    }

    public String getWeightPipe() {
        return weightPipe.get();
    }

    public StringProperty weightPipeProperty() {
        return weightPipe;
    }

    public void setWeightPipe(String weightPipe) {
        this.weightPipe.set(weightPipe);
    }

    public String getLengthPipe() {
        return lengthPipe.get();
    }

    public StringProperty lengthPipeProperty() {
        return lengthPipe;
    }

    public void setLengthPipe(String lengthPipe) {
        this.lengthPipe.set(lengthPipe);
    }

    public String getCountPipe() {
        return countPipe.get();
    }

    public StringProperty countPipeProperty() {
        return countPipe;
    }

    public void setCountPipe(String countPipe) {
        this.countPipe.set(countPipe);
    }
}
