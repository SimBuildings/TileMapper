package de.simbuildings.tilemapper.ui.imagesplitting;

import dagger.Lazy;
import de.simbuildings.tilemapper.ui.common.ExportModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.util.Optional;
import java.util.ResourceBundle;

public class ImageSplittingController implements Initializable {
    private final TileModel tileModel;
    private final ExportModel exportModel;

    private final DragAndDropModel dragAndDropModel = new DragAndDropModel();

    private final Lazy<Stage> resourcepackStage;
    private final Lazy<Stage> conflictStage;

    @FXML
    public Button importButton;
    @FXML
    public Label fileLabel;
    @FXML
    public ComboBox<Integer> resolutionComboBox;
    @FXML
    public TextField blockTextField;
    @FXML
    public TilePreview tilePreview;
    @FXML
    public Button exportButton;
    @FXML
    public DragAndDropOverlay dragAndDropOverlay;
    @FXML
    public Parent root;

    @Inject
    public ImageSplittingController(TileModel tileModel,
                                    ExportModel exportModel,
                                    @Named("resourcepack") Lazy<Stage> resourcepackStage,
                                    @Named("conflict") Lazy<Stage> conflictStage) {
        this.tileModel = tileModel;
        this.exportModel = exportModel;
        this.resourcepackStage = resourcepackStage;
        this.conflictStage = conflictStage;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        bindSettingsDisableBinding();
        bindExportDisableBinding();

        dragAndDropOverlay.setDragAndDropModel(dragAndDropModel);
        tilePreview.setTileModel(tileModel);

        tileModel.setFileLabelText("Select original image to split");
        fileLabel.textProperty().bind(tileModel.fileLabelTextProperty());

        resolutionComboBox.setItems(tileModel.validTargetResolutionsProperty());

        tileModel.blockNameProperty().bind(blockTextField.textProperty());
        tileModel.targetResolutionProperty().bind(resolutionComboBox.valueProperty());

        setUpDragAndDrop();
    }

    private void bindExportDisableBinding() {
        exportButton.disableProperty().bind(
                tileModel.blockNameProperty().isEmpty()
                        .or(tileModel.targetResolutionProperty().isEqualTo(0))
        );
    }

    private void bindSettingsDisableBinding() {
        blockTextField.disableProperty()
                .bind(tileModel.originalImageProperty().isNull());
        resolutionComboBox.disableProperty()
                .bind(tileModel.originalImageProperty().isNull());
    }

    private void setUpDragAndDrop() {
        root.setOnDragOver(event -> event.acceptTransferModes(TransferMode.COPY_OR_MOVE));

        root.setOnDragEntered(event -> dragAndDropModel.setDraggingProperty(true));
        root.setOnDragExited(event -> dragAndDropModel.setDraggingProperty(false));
        root.setOnDragDropped(event -> {
            dragAndDropModel.setDraggingProperty(false);
            Dragboard dragboard = event.getDragboard();
            if (dragboard.hasFiles()) {
                setOriginalImage(dragboard.getFiles().get(0));
            }
        });
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
        resolutionComboBox.setValue(null);
        try {
            tileModel.setOriginalImage(originalImage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleSettingsButton(ActionEvent actionEvent) {
        Stage stage = resourcepackStage.get();
        stage.show();
    }

    @FXML
    private void handleExport(ActionEvent actionEvent) {
        File outputDirectory = new DirectoryChooser()
                .showDialog(importButton.getScene().getWindow());

        Optional.ofNullable(outputDirectory)
                .map(File::toPath)
                .ifPresent(this::safeExportCtmBlock);
    }

    private void safeExportCtmBlock(Path outputDirectory) { // TODO: create safe export api?
        updateExportJob(outputDirectory);
        if (tileModel.hasConflict(outputDirectory)) {
            conflictStage.get().showAndWait();
            return;
        }
        try {
            tileModel.export(outputDirectory);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void updateExportJob(Path outputDirectory) {
        exportModel.setCurrentExportJob(
                new ExportModel.ExportJob(
                        tileModel.getCtmExporter(),
                        outputDirectory));
    }
}