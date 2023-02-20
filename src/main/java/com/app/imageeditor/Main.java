package com.app.imageeditor;

import com.app.imageeditor.Constants.ImageType;
import com.app.imageeditor.Utils.*;
import com.app.imageeditor.controllers.MainController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    private ImageWorker imgSaver= ImageFactory.getInstance().create(ImageType.SAVER);
    private ImageWorker imgProcessor=ImageFactory.getInstance().create(ImageType.PROCESSOR);
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("MainView.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 460, 490);
        MainController controller=fxmlLoader.getController();
        String css = this.getClass().getResource("style.css").toExternalForm();
        scene.getStylesheets().add(css);
        controller.setImageWorkers((ImageSaver) imgSaver, (ImageProcessor) imgProcessor);
        stage.setTitle("Image processing");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}