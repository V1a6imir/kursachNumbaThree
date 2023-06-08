package kur3.client.controller;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import kur3.server.entity.ObjectData;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class ParseController {

    public Button showMap;
    public Button infoObjClient;
    public Button goToCharacteristicsPage;
    public Button goToContractorPage;
    public Button goToCustomerPage;
    public Button goToRequestPage;
    public Button goToContractPage;

    @FXML
    private TableView<ObjectData> showObjectLabel;

    @FXML
    private TableColumn<ObjectData, String> idColumn;

    @FXML
    private TableColumn<ObjectData, String> nameColumn;



    @FXML
    private void goToCharacteristicsPage(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/characteristics.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root, 600, 400);
        Stage stage = (Stage) goToCharacteristicsPage.getScene().getWindow(); // Получаем текущее окно
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void goToCustomerPage(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/customer.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root, 600, 400);
        Stage stage = (Stage) goToCustomerPage.getScene().getWindow(); // Получаем текущее окно
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void goToContractorPage(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/contractor.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root, 600, 400);
        Stage stage = (Stage) goToCustomerPage.getScene().getWindow(); // Получаем текущее окно
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    private void goToRequestPage(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/request.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root, 600, 400);
        Stage stage = (Stage) goToRequestPage.getScene().getWindow(); // Получаем текущее окно
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    private void goToContractPage(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/contract.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root, 600, 400);
        Stage stage = (Stage) goToRequestPage.getScene().getWindow(); // Получаем текущее окно
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    public void infoObjClient(ActionEvent event) {
        // Создание соединения с базой данных
        String url = "jdbc:mysql://localhost/sample";
        String username = "root";
        String password = "wmacrus1";

        RestTemplate restTemplate = new RestTemplate();

       List<ObjectData> newData = Arrays.stream(restTemplate.getForObject("http://localhost:8080/parse-data",
                ObjectData[].class)).toList();

        try {
            ObservableList<ObjectData> observableData = FXCollections.observableArrayList(newData);

            // Инициализация таблицы
            initializeMasterTable();

            // Установка данных в TableView
            showObjectLabel.setItems(observableData);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
        @FXML
    private void initializeMasterTable() {
        TableColumn<ObjectData, Long> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(cell -> new SimpleLongProperty(cell.getValue().getId()).asObject());

        TableColumn<ObjectData, String> nameColumn = new TableColumn<>("Название");
        nameColumn.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getName()));

        showObjectLabel.getColumns().setAll(idColumn, nameColumn);
    }
    @FXML
    private void showMap() throws IOException {
        String mapHtml = "<html><head></head><body>" +
                "<a href=\"https://yandex.ru/maps/?um=constructor%3Ac0b3dc08e975a6234f3a714a7ad8c76abe665f01fa322828eb81d09e09d63653&amp;source=constructorStatic\" target=\"_blank\">" +
                "<img src=\"https://api-maps.yandex.ru/services/constructor/1.0/static/?um=constructor%3Ac0b3dc08e975a6234f3a714a7ad8c76abe665f01fa322828eb81d09e09d63653&amp;width=600&amp;height=450&amp;lang=ru_RU\" alt=\"\" style=\"border: 0;\" />" +
                "</a>" +
                "</body></html>";

        WebView webView = new WebView();
        webView.getEngine().loadContent(mapHtml);

        Stage mapStage = new Stage();
        mapStage.setTitle("Map");
        mapStage.setScene(new Scene(webView, 600, 400));
        mapStage.show();
    }
}
