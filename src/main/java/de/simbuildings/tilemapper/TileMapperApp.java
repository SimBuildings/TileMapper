package de.simbuildings.tilemapper;

import de.simbuildings.tilemapper.common.Persistable;
import de.simbuildings.tilemapper.ui.common.FXMLSceneLoader;
import de.simbuildings.tilemapper.ui.injection.DaggerTileMapperAppComponent;
import de.simbuildings.tilemapper.ui.injection.JfxApplicationComponent;
import de.simbuildings.tilemapper.ui.injection.TileMapperAppComponent;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.List;

/**
 * Created by SimBuildings on 09.10.21 at 23:44
 */
public class TileMapperApp extends Application {

    private static final TileMapperAppComponent LAUNCHER = DaggerTileMapperAppComponent.create();

    public static final int SCENE_WIDTH = 400;
    public static final int SCENE_HEIGHT = 893;
    private Persistable resourcepackModel;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        loadFonts();
        JfxApplicationComponent applicationComponent = LAUNCHER.JfxApplication()
                .primaryStage(primaryStage)
                .application(this)
                .build();

        FXMLSceneLoader fxmlSceneLoader = applicationComponent.fxmlSceneLoader();
        FXMLLoader fxmlLoader = fxmlSceneLoader.load("/fxml/imageSplittingView.fxml");
        Parent root = fxmlLoader.load();
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        // resourcepackModel.save();
    }

    private void loadFonts() {
        List<String> fontPaths = List.of(
                "/font/Inter/Inter-Bold.otf",
                "/font/Inter/Inter-SemiBold.otf",
                "/font/Inter/Inter-Medium.otf",
                "/font/Inter/Inter-Regular.otf"
        );
        fontPaths.forEach(path -> Font.loadFont(getClass().getResourceAsStream(path), 14));
    }
}