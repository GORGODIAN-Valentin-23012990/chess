module com.valoo.chess {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;

    opens com.valoo.chess to javafx.fxml;
    exports com.valoo.chess;
    exports com.valoo.chess.pieces;
    opens com.valoo.chess.pieces to javafx.fxml;
    exports com.valoo.chess.controller;
    opens com.valoo.chess.controller to javafx.fxml;
    exports com.valoo.chess.fonctionnalites;
    opens com.valoo.chess.fonctionnalites to javafx.fxml;
}