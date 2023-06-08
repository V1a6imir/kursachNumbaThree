module kursachNumbaThree.Client.main {
    exports kur3.client to javafx.graphics;
    exports kur3.client.controller;
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.jsoup;
    requires spring.web;
    requires kursachNumbaThree.Server.main;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.databind;
    requires spring.core;
    requires javafx.web;

    opens kur3.client.controller to javafx.fxml;

}