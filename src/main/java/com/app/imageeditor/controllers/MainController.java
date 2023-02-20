package com.app.imageeditor.controllers;
import com.app.imageeditor.Constants.ImageExtension;
import com.app.imageeditor.Utils.*;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class MainController {

    @FXML
    private RadioButton editButton;

    @FXML
    private Canvas canvas;

    @FXML
    private AnchorPane canvasPane;

    @FXML
    private ColorPicker colorPicker;
@FXML
    private ColorPicker colorPickerBrush;

    @FXML
    private CheckBox eraserBox;

    @FXML
    private TextField heightTextField;

    @FXML
    private ImageView imageView;

    @FXML
    private Button saveButton;

    @FXML
    private Spinner<Integer> sizeSpinner;

    @FXML
    private Button transparentButton;

    @FXML
    private TextField widthTextField;

    @FXML
    private ComboBox<ImageExtension> extensionComboBox;



    private Image currentImage;
    private ImageSaver imgSaver;
    private ImageProcessor imgProcessor;

    private double xOffset = 0;
    private double yOffset = 0;
    public void setImageWorkers(ImageSaver imgSaver, ImageProcessor imgProcessor){
        this.imgSaver=imgSaver;
        this.imgProcessor=imgProcessor;
    }

    private GraphicsContext graphicsContext;
    @FXML
    private void initialize(){

        graphicsContext=canvas.getGraphicsContext2D();

        editButton.setSelected(false);
        canvas.setOnMouseDragged(event -> {
            double size=(double)sizeSpinner.getValue();
            double x=event.getX()-size/2;
            double y=event.getY()-size/2;

            if (eraserBox.isSelected()){
                graphicsContext.clearRect(x,y,size,size);
            }
            else{
                graphicsContext.setFill(colorPickerBrush.getValue());
                graphicsContext.fillRect(x,y,size,size);
            }
        });

        sizeSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(
                1,
                1000
        ));
        canvasPane.setVisible(false);
        editButton.setOnAction(event -> {
            if(editButton.isSelected()){
                canvasPane.setVisible(true);
                imageView.setVisible(false);
            }
            else{
                canvasPane.setVisible(false);
                imageView.setVisible(true);

            }
        });

        extensionComboBox.getItems().setAll(Arrays.asList(ImageExtension.values()));
        extensionComboBox.setValue(ImageExtension.PNG);

    }

    /**
     * Saves the drawn canvas to the current Image
     */
    private void saveSnapshotCanvasImage(){

        Image image = canvas.snapshot(null, null);
        currentImage=image;
    }

    /**
     * Saves the current image to the canvas
     */
    private void drawCurrentImageToCanvas(){
        Image falseImage=currentImage;
        Image resizedImage=imgProcessor.resizeImage(falseImage,387,311);
        graphicsContext.drawImage(resizedImage,0,0);
    }
    @FXML
    private void onSaveClick() throws IOException {
        int width=0;
        int height=0;
        try {
            width = Integer.parseInt(widthTextField.getText());
            height = Integer.parseInt(heightTextField.getText());
            if (editButton.isSelected()) {
                saveSnapshotCanvasImage();
                exportImage(width, height);

            }
            else{
                if (currentImage != null) {
                    exportImage(width, height);
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Please select an image!", ButtonType.OK);
                    alert.show();
                }
            }
        }
        catch (NumberFormatException ex){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Wrong type!", ButtonType.OK);
            alert.show();
        }

    }

    /**
     * Exports the current Image on the user computer
     * @param width int
     * @param height int
     * @throws IOException
     */
    private void exportImage(int width, int height) throws IOException {
        currentImage = imgProcessor.resizeImage(currentImage, width, height);
        BufferedImage bf = imgProcessor.toBufferedImage(currentImage);
        DirectoryChooser chooser=new DirectoryChooser();
        chooser.setTitle("Choose a directory");
        chooser.setInitialDirectory(new File(System.getProperty("user.home")));
        File selectedDir=chooser.showDialog(null);
        String location=selectedDir.toString();
        location=location.replace("\\","\\\\");
        imgSaver.saveToFile(bf, "Untitled", extensionComboBox.getValue().toString(),location);
    }

    /**
     * Select an image from the user computer
     */
    @FXML
    private void onImageClick(){
        FileChooser fileChooser=new FileChooser();
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Icons JPG, PNG, GIF", "*.jpg", "*.gif", "*.png") );
        File selectedFile = fileChooser.showOpenDialog(null);

        currentImage=new Image(selectedFile.toURI().toString());
        int width=(int)currentImage.getWidth();
        int height=(int)currentImage.getHeight();


        drawCurrentImageToCanvas();
        widthTextField.setText(Integer.toString(width));
        heightTextField.setText(Integer.toString(height));


        imageView.setPreserveRatio(false);
        imageView.setImage(currentImage);
    }
    @FXML
    private void onTransparentClick(){
        saveSnapshotCanvasImage();
        Color color=colorPicker.getValue();
        currentImage=imgProcessor.removeWhiteBackground(currentImage,color);
        imageView.setImage(currentImage);
        drawCurrentImageToCanvas();

    }

}
