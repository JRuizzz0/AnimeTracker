module com.example.animetracker {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.animetracker to javafx.fxml;
    exports com.example.animetracker;


    opens com.example.animetracker.controller to javafx.fxml;
    exports com.example.animetracker.controller;
    exports com.example.animetracker.exceptions;

    opens com.example.animetracker.model to javafx.base;
    exports com.example.animetracker.model;
    exports com.example.animetracker.config;
    opens com.example.animetracker.config to javafx.base;
}