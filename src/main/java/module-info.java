module com.app.imageeditor {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.base;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires java.desktop;

    opens com.app.imageeditor to javafx.fxml;

    exports com.app.imageeditor;
    exports com.app.imageeditor.controllers;
    opens com.app.imageeditor.controllers to javafx.fxml;


}
