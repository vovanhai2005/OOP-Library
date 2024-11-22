module org.example.ooplibrary {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires javafx.swing;
    requires com.google.gson;
    requires java.sql;
    requires java.net.http;

    exports org.example.ooplibrary.Controller;
    opens org.example.ooplibrary.Controller to javafx.fxml;
    exports org.example.ooplibrary.Core;
    opens org.example.ooplibrary.Core to javafx.fxml;
    opens org.example.ooplibrary.Object to javafx.base;
}