package de.simbuildings.tilemapper.ui.controllers;

import de.simbuildings.tilemapper.ui.models.TileModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by SimBuildings on 12.02.22 at 21:32
 */
public class PrimaryController implements Initializable {
    private final TileModel tileModel = new TileModel();
    @FXML
    public Button importButton;
    @FXML
    public Label fileLabel;
    @FXML
    public ComboBox<Integer> resolutionComboBox;
    @FXML
    public TextField blockTextField;
    @FXML
    public Button exportButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        blockTextField.disableProperty()
                .bind(tileModel.originalImageProperty().isNull());
        resolutionComboBox.disableProperty()
                .bind(tileModel.originalImageProperty().isNull());

        exportButton.disableProperty().bind(
                blockTextField.textProperty().isEmpty()
        );

        tileModel.blockNameProperty().bind(blockTextField.textProperty());
    }

    @FXML
    private void handleImport(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PNG image file", "*.png"));

        File originalImage = fileChooser.showOpenDialog(importButton.getScene().getWindow());
        if (originalImage != null) {
            setOriginalImage(originalImage);
        }
    }

    private void setOriginalImage(File originalImage) {
        try {
            tileModel.setOriginalImage(originalImage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleExport(ActionEvent actionEvent) {

    }
}
