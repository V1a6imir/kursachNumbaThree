package kur3.client.controller;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import kur3.server.entity.ObjectData;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import kur3.server.entity.ObjectCharacteristics;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class ObjectCharacteristicsController {

    @FXML
    private TableView<ObjectCharacteristics> showCharacteristicsLabel;
    @FXML
    private TableColumn<ObjectCharacteristics, Long> idColumn;
    @FXML
    private TableColumn<ObjectCharacteristics, String> addressColumn;
    @FXML
    private TableColumn<ObjectCharacteristics, String> userColumn;
    @FXML
    private TableColumn<ObjectCharacteristics, String> workColumn;
    @FXML
    private TableColumn<ObjectCharacteristics, ObjectData> objIdColumn;
    @FXML
    private TextField idTextField;
    @FXML
    private TextField addressTextField;
    @FXML
    private TextField userTextField;
    @FXML
    private TextField typeOfWorkTextField;
    @FXML
    private TextField objectIdTextField;
    @FXML
    public Button handleAddButton;
    @FXML
    public Button handleBackButton;
    @FXML
    public Button handleDeleteButton;

    private final RestTemplate restTemplate;
    private final String serverUrl = "http://localhost:8080";

    public ObjectCharacteristicsController() {
        restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
    }

    @FXML
    private void initialize() {
        initializeTable();
        refreshTableData();
    }

    @FXML
    private void handleAddButton(ActionEvent event) {
        String address = addressTextField.getText();
        String user = userTextField.getText();
        String typeOfWork = typeOfWorkTextField.getText();
        String objectId = objectIdTextField.getText();
        ObjectData objectData = new ObjectData();

        objectData.setId(Long.parseLong(objectId));
        ObjectCharacteristics objectCharacteristics = new ObjectCharacteristics(address, user, typeOfWork, Long.parseLong(objectId));


        HttpEntity<ObjectCharacteristics> request = new HttpEntity<>(objectCharacteristics);
        restTemplate.postForObject(serverUrl + "/add-char", request, Void.class);

        clearInputFields();
        refreshTableData();

        showSuccessAlert("Object characteristics added successfully");
    }

    @FXML
    private void handleDeleteButton(ActionEvent event) {
        ObjectCharacteristics selectedCharacteristics = showCharacteristicsLabel.getSelectionModel().getSelectedItem();
        if (selectedCharacteristics != null) {
            Long id = selectedCharacteristics.getId();
            restTemplate.exchange(serverUrl + "/{id}", HttpMethod.DELETE, null, Void.class, id);
            refreshTableData();
            showSuccessAlert("Object characteristics deleted successfully");
        } else {
            showErrorAlert("No object characteristics selected");
        }
    }

    private void initializeTable() {
        TableColumn<ObjectCharacteristics, Long> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(cellData -> new SimpleLongProperty(cellData.getValue().getId()).asObject());

        TableColumn<ObjectCharacteristics, String> addressColumn = new TableColumn<>("АДРЕС");
        addressColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAddress()));

        TableColumn<ObjectCharacteristics, String> userColumn = new TableColumn<>("ПОЛЬЗОВАТЕЛЬ");
        userColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getUser()));

        TableColumn<ObjectCharacteristics, String> workColumn = new TableColumn<>("ВИД РАБОТ");
        workColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getType_of_work()));

        TableColumn<ObjectCharacteristics, Long> objectIdColumn = new TableColumn<>("ID ОКН");
        objectIdColumn.setCellValueFactory(cellData -> new SimpleLongProperty(cellData.getValue().getObjectData().getId()).asObject());

        showCharacteristicsLabel.getColumns().setAll(idColumn, addressColumn, userColumn, workColumn, objectIdColumn);
    }


    private void refreshTableData() {
        ResponseEntity<ObjectCharacteristics[]> response = restTemplate.getForEntity(serverUrl + "/data", ObjectCharacteristics[].class);
        System.out.println(1);
        ObjectCharacteristics[] characteristicsArray = response.getBody();
        System.out.println(2);
        List<ObjectCharacteristics> characteristicsList = Arrays.asList(characteristicsArray);
        System.out.println();
        showCharacteristicsLabel.setItems(FXCollections.observableArrayList(characteristicsList));
    }

    private void clearInputFields() {
        addressTextField.clear();
        userTextField.clear();
        typeOfWorkTextField.clear();
        objectIdTextField.clear();
    }

    private void showSuccessAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    @FXML
    private void handleBackButton(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/client3.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root, 800, 600);
        Stage stage = (Stage) handleBackButton.getScene().getWindow(); // Получаем текущее окно
        stage.setScene(scene);
        stage.show();
    }

}
