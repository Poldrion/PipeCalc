package Controller;

import Animation.Shake;
import Model.CalcPipeResult;
import Model.Calculator;
import Model.Pipe;
import Model.PipeSize;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;


public class MainController {
    private static final ObservableList<CalcPipeResult> CALC_PIPE_RESULTS = FXCollections.observableArrayList();
    private static final String FILE_NAME = "previousResultsFile.txt";
    private static final Pipe pipe = new Pipe();
    private static final Pattern DIGIT_PATTERN = Pattern.compile("(\\d+\\.?\\d*)?");
    private static final ObservableList<String> PIPES_DIAMETER = FXCollections.observableArrayList("33.40", "42.16", "46.26", "60.32",
            "73.02", "88.90", "101.60", "114.30");
    private static final PipeSize[] PIPE_SIZES = PipeSize.values();

    @FXML // fx:id="calcBtn"
    private Button calcBtn;
    @FXML // fx:id="clearBtn"
    private Button clearBtn;
    @FXML // fx:id="countPipe"
    private TextField countPipe;
    @FXML // fx:id="diameterPipe"
    private ComboBox<String> diameterPipe;
    @FXML // fx:id="lengthPipe"
    private TextField lengthPipe;
    @FXML // fx:id="notUpset"
    private RadioButton notUpset;
    @FXML // fx:id="saveBtn"
    private Button saveBtn;
    @FXML // fx:id="upset"
    private RadioButton upset;
    @FXML // fx:id="wallThicknessPipe"
    private ListView<String> wallThicknessPipe;
    @FXML // fx:id="weightPipe"
    private TextField weightPipe;
    @FXML
    private TableView<CalcPipeResult> prevResults;
    @FXML
    private TableColumn<CalcPipeResult, String> pipeNamePrevResultTable;
    @FXML
    private TableColumn<CalcPipeResult, String> pipeWeightPrevResultTable;
    @FXML
    private TableColumn<CalcPipeResult, String> pipeLengthPrevResultTable;
    @FXML
    private TableColumn<CalcPipeResult, String> pipeCountPrevResultTable;

    @FXML
        // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        loadPrevResults();
        pipeNamePrevResultTable.setCellValueFactory(cellData -> cellData.getValue().namePipeProperty());
        pipeWeightPrevResultTable.setCellValueFactory(cellData -> cellData.getValue().weightPipeProperty());
        pipeLengthPrevResultTable.setCellValueFactory(cellData -> cellData.getValue().lengthPipeProperty());
        pipeCountPrevResultTable.setCellValueFactory(cellData -> cellData.getValue().countPipeProperty());

        prevResults.setItems(CALC_PIPE_RESULTS);

        MultipleSelectionModel<String> wallThicknessSelection = wallThicknessPipe.getSelectionModel();
        diameterPipe.setItems(PIPES_DIAMETER);
        diameterPipe.setOnAction(actionEvent -> {
            wallThicknessPipe.setItems(setDataWallThicknessPipe(diameterPipe.getValue()));
            if (!wallThicknessPipe.getItems().isEmpty()) {
                wallThicknessPipe.getSelectionModel().select(0);
            }
            setPipeParameter(wallThicknessPipe.getSelectionModel().getSelectedItem());
            calcBtn.setDisable(false);
            saveBtn.setDisable(false);
        });
        wallThicknessSelection.selectedItemProperty().addListener((observableValue, oldValue, newValue) -> setPipeParameter(newValue));

        upset.setOnAction(event -> setPipeParameter(wallThicknessPipe.getSelectionModel().getSelectedItem()));

        notUpset.setOnAction(event -> setPipeParameter(wallThicknessPipe.getSelectionModel().getSelectedItem()));

        countPipe.textProperty().addListener((observable, oldValue, newValue) -> {
            validateInputInformation(countPipe, oldValue, newValue);
            lengthPipe.setDisable(true);
            weightPipe.setDisable(true);
        });

        lengthPipe.textProperty().addListener((observable, oldValue, newValue) -> {
            validateInputInformation(lengthPipe, oldValue, newValue);
            countPipe.setDisable(true);
            weightPipe.setDisable(true);
        });

        weightPipe.textProperty().addListener((observable, oldValue, newValue) -> {
            validateInputInformation(weightPipe, oldValue, newValue);
            lengthPipe.setDisable(true);
            countPipe.setDisable(true);
        });

        if (diameterPipe.getValue() == null) {
            calcBtn.setDisable(true);
            saveBtn.setDisable(true);
        }

        calcBtn.setOnAction(event -> calcBtnClicked());
        saveBtn.setOnAction(event -> {
            saveBtnClicked();
            loadPrevResults();
        });
        clearBtn.setOnAction(event -> {
            clearBtnClicked();
            loadPrevResults();
        });
    }

    private void calcBtnClicked() {
        String length = String.valueOf(0.00);
        String weight = String.valueOf(0.00);
        String count = String.valueOf(0);

        if (checkEmpty()) return;

        if (!weightPipe.isDisable()) {
            weight = weightPipe.getText();
            length = String.valueOf(Calculator.convertWeightToLength(weight));
            count = String.valueOf(Calculator.convertWeightToCount(weight));
        }

        if (!lengthPipe.isDisable()) {
            length = lengthPipe.getText();
            weight = String.valueOf(Calculator.convertLengthToWeight(length));
            count = String.valueOf(Calculator.convertLengthToCount(length));
        }

        if (!countPipe.isDisable()) {
            count = countPipe.getText();
            weight = String.valueOf(Calculator.convertCountToWeight(count));
            length = String.valueOf(Calculator.convertCountToLength(count));
        }

        countPipe.setText(count);
        lengthPipe.setText(length);
        weightPipe.setText(weight);

        countPipe.setDisable(false);
        lengthPipe.setDisable(false);
        weightPipe.setDisable(false);
    }

    @FXML
    private void saveBtnClicked() {
        if (checkEmpty()) return;
        if (weightPipe.getText().equals("0.00") && lengthPipe.getText().equals("0.00") && countPipe.getText().equals("0"))
            return;

        if (!Files.exists(Path.of(FILE_NAME))) {
            try {
                Files.createFile(Path.of("previousResultsFile.txt"));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        String result = String.format("%s|%s|%s|%s",
                pipe, weightPipe.getText(), lengthPipe.getText(), countPipe.getText());


        try (FileWriter fileWriter = new FileWriter(FILE_NAME, true);
             FileReader fileReader = new FileReader(FILE_NAME);
             BufferedReader br = new BufferedReader(fileReader)) {
            Set<String> uniqueResults = new HashSet<>();
            while (br.ready()) {
                uniqueResults.add(br.readLine());
            }
            if (!uniqueResults.contains(result)) {
                fileWriter.write(result);
                fileWriter.append('\n');
                fileWriter.flush();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void clearBtnClicked() {
        if (!Files.exists(Path.of(FILE_NAME))) {
            try {
                Files.createFile(Path.of("previousResultsFile.txt"));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        try (FileWriter fileWriter = new FileWriter(FILE_NAME, false)) {
            fileWriter.write("");
            fileWriter.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void loadPrevResults() {
        CALC_PIPE_RESULTS.clear();
        if (!Files.exists(Path.of(FILE_NAME))) {
            try {
                Files.createFile(Path.of("previousResultsFile.txt"));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        try (FileReader fileReader = new FileReader(FILE_NAME);
             BufferedReader br = new BufferedReader(fileReader)) {
            while (br.ready()) {
                String[] temp = br.readLine().split("\\|");
                CALC_PIPE_RESULTS.add(new CalcPipeResult(temp[0], temp[1], temp[2], temp[3]));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean checkEmpty() {
        if (weightPipe.getText().isEmpty() && lengthPipe.getText().isEmpty() && countPipe.getText().isEmpty()) {
            Shake weightTxtFieldAnimation = new Shake(weightPipe);
            Shake lengthTxtFieldAnimation = new Shake(lengthPipe);
            Shake countTxtFieldAnimation = new Shake(countPipe);
            weightTxtFieldAnimation.playAnimation();
            lengthTxtFieldAnimation.playAnimation();
            countTxtFieldAnimation.playAnimation();
            return true;
        }
        return false;
    }

    private void setPipeParameter(String wallThicknessPipe) {
        pipe.setDiameterPipe(diameterPipe.getValue());
        pipe.setWallThicknessPipe(wallThicknessPipe);
        pipe.setUpset(upset.isSelected());
    }

    private void validateInputInformation(TextField textField, String oldValue, String newValue) {
        if (!DIGIT_PATTERN.matcher(newValue).matches()) textField.setText(oldValue);
        if (textField.getText().isEmpty()) textField.setText(String.valueOf(0));
        if (textField.getText().length() > 1 && textField.getText().matches("^0\\d*"))
            textField.setText(textField.getText().replaceFirst("0", ""));
    }

    private ObservableList<String> setDataWallThicknessPipe(String diameter) {
        LinkedList<String> pipeSize = new LinkedList<>();
        for (PipeSize element : PIPE_SIZES) {
            pipeSize.add(element.toString());
        }
        List<String> result = pipeSize.stream()
                .filter(element -> element.contains(diameter))
                .map(element -> element.split("x")[1])
                .collect(Collectors.toList());

        return FXCollections.observableArrayList(result);
    }

    public PipeSize getPipeParameter() {
        Map<String, PipeSize> map = new HashMap<>();
        for (PipeSize element : PIPE_SIZES) {
            map.put(element.getSize(), element);
        }
        return map.get(pipe.toString().replaceFirst("НКТ[НВ]-", ""));
    }

    @FXML
    public boolean getUpset() {
        return pipe.isUpset();
    }

}

