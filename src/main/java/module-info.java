module com.valoo.chess {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;

    opens com.valoo.chess to javafx.fxml;
    exports com.valoo.chess;
}