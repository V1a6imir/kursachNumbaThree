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
import kur3.server.entity.Contractor;
import kur3.server.entity.ObjectData;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class ClientContractorController {

    @FXML
    private TableView<Contractor> showContractorsTable;
    @FXML
    private TableColumn<Contractor, Long> idColumn;
    @FXML
    private TableColumn<Contractor, String> nameColumn;
    @FXML
    private TableColumn<Contractor, ObjectData> objectIdColumn;
    @FXML
    private TextField contractorIdTextField;
    @FXML
    private TextField contractorNameTextField;
    @FXML
    private TextField objectIdTextField;
    @FXML
    private TextField contractorNameTextField1;
    @FXML
    private TextField objectIdTextField1;
    @FXML
    private Button addButton;
    @FXML
    private Button backButton;
    @FXML
    private Button deleteButton;
    @FXML
    private Button editButton;




    private final RestTemplate restTemplate;
    private final String serverUrl = "http://localhost:8080";

    public ClientContractorController() {
        restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
    }

    @FXML
    private void initialize() {
        initializeTable();
        refreshTableData();
    }

    @FXML
    private void addButton(ActionEvent event) {
        String contractorName = contractorNameTextField.getText();
        String objectId = objectIdTextField.getText();
        ObjectData objectData = new ObjectData();


        objectData.setId(Long.parseLong(objectId));
        Contractor contractor = new Contractor(null, contractorName, Long.parseLong(objectId));

        HttpEntity<Contractor> request = new HttpEntity<>(contractor);
        restTemplate.postForObject(serverUrl + "/add-contractor", request, Void.class);

        clearInputFields();
        refreshTableData();

        showSuccessAlert("Contractor added successfully");
    }

    @FXML
    private void editButton(ActionEvent event) {
        Contractor selectedContractor = showContractorsTable.getSelectionModel().getSelectedItem();
        if (selectedContractor != null) {
            String name = contractorNameTextField1.getText();
            String objectId = objectIdTextField1.getText();
            ObjectData objectData = new ObjectData();


            objectData.setId(Long.parseLong(objectId));
            Contractor contractor = new Contractor(null, name, Long.parseLong(objectId));


            HttpEntity<Contractor> request = new HttpEntity<>(contractor);
            restTemplate.put(serverUrl + "/edit-contractor/{id}", request, selectedContractor.getContractorId());

            clearInputFields();
            refreshTableData();

            showSuccessAlert("Customer updated successfully");
        } else {
            showErrorAlert("No customer selected");
        }
    }

    @FXML
    private void deleteButton(ActionEvent event) {
        Contractor selectedContractor = showContractorsTable.getSelectionModel().getSelectedItem();
        if (selectedContractor != null) {
            Long contractorId = selectedContractor.getContractorId();
            restTemplate.exchange(serverUrl + "/delete-contractor/{id}", HttpMethod.DELETE, null, Void.class, contractorId);
            refreshTableData();
            showSuccessAlert("Contractor deleted successfully");
        } else {
            showErrorAlert("No contractor selected");
        }
    }

    private void initializeTable() {
        TableColumn<Contractor, Long> idColumn = new TableColumn<>("ID Исполнителя");
        idColumn.setCellValueFactory(cellData -> new SimpleLongProperty(cellData.getValue().getContractorId()).asObject());

        TableColumn<Contractor, String> nameColumn = new TableColumn<>("Наименование");
        nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getContractorName()));

        TableColumn<Contractor, Long> objectIdColumn = new TableColumn<>("ID ОКН");
        objectIdColumn.setCellValueFactory(cellData -> new SimpleLongProperty(cellData.getValue().getObjectData().getId()).asObject());

        showContractorsTable.getColumns().setAll(idColumn, nameColumn, objectIdColumn);
    }

    private void refreshTableData() {
        ResponseEntity<Contractor[]> response = restTemplate.getForEntity(serverUrl + "/data-contractor", Contractor[].class);
        Contractor[] contractorArray = response.getBody();
        List<Contractor> contractorList = Arrays.asList(contractorArray);
        showContractorsTable.setItems(FXCollections.observableArrayList(contractorList));
    }

    private void clearInputFields() {
        contractorNameTextField.clear();
        objectIdTextField.clear();
        contractorNameTextField1.clear();
        objectIdTextField1.clear();
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
    private void backButton(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/client3.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root, 800, 600);
        Stage stage = (Stage) backButton.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

}
