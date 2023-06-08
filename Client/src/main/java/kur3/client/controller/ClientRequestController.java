package kur3.client.controller;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import kur3.server.entity.Contractor;
import kur3.server.entity.ObjectData;
import kur3.server.entity.Request;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class ClientRequestController {
    @FXML
    private TableView<Request> requestTable;
    @FXML
    private TableColumn<Request, Long> idColumn;
    @FXML
    private TableColumn<Request, Date> arriveDateColumn;
    @FXML
    private TableColumn<Request, Contractor> contractorColumn;
    @FXML
    private TextField arriveDateTextField;
    @FXML
    private TextField contractorIdTextField;
    @FXML
    private TextField arriveDateTextField1;
    @FXML
    private TextField contractorIdTextField1;

    @FXML
    public Button addButton;
    @FXML
    public Button deleteButton;
    @FXML
    public Button editButton;
    @FXML
    public Button backButton;

    private final RestTemplate restTemplate;
    private final String serverUrl = "http://localhost:8080";

    public ClientRequestController() {
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
        Date arriveDate = parseDate(arriveDateTextField.getText());
        String contractorId = contractorIdTextField.getText();
        Contractor contractor = new Contractor();


        contractor.setContractorId(Long.parseLong(contractorId));


        Request requestEntity = new Request(null, arriveDate, Long.parseLong(contractorId));

        HttpEntity<Request> request = new HttpEntity<>(requestEntity);
        restTemplate.postForObject(serverUrl + "/add-request", request, Void.class);

        clearInputFields();
        refreshTableData();

        showSuccessAlert("Request added successfully");
    }

    @FXML
    private void deleteButton(ActionEvent event) {
        Request selectedRequest = requestTable.getSelectionModel().getSelectedItem();
        if (selectedRequest != null) {
            restTemplate.delete(serverUrl + "/delete-request/{id}", selectedRequest.getRequestId());

            clearInputFields();
            refreshTableData();

            showSuccessAlert("Request deleted successfully");
        } else {
            showErrorAlert("No request selected");
        }
    }
    @FXML
    private void editButton(ActionEvent event) {
        Request selectedRequest = requestTable.getSelectionModel().getSelectedItem();
        if (selectedRequest != null) {
            Date arriveDate = parseDate(arriveDateTextField1.getText());
            String contractorId = contractorIdTextField1.getText();
            Contractor contractor = new Contractor();


            contractor.setContractorId(Long.parseLong(contractorId));
            Request requestCl = new Request(null, arriveDate, Long.parseLong(contractorId));


            HttpEntity<Request> request = new HttpEntity<>(requestCl);
            restTemplate.put(serverUrl + "/edit-request/{id}", request, selectedRequest.getRequestId());

            clearInputFields();
            refreshTableData();

            showSuccessAlert("Customer updated successfully");
        } else {
            showErrorAlert("No customer selected");
        }
    }

    private void initializeTable() {
        TableColumn<Request, Long> idColumn = new TableColumn<>("ID Заявки");
        idColumn.setCellValueFactory(cellData -> new SimpleLongProperty(cellData.getValue().getRequestId()).asObject());

        TableColumn<Request, Date> arriveDateColumn = new TableColumn<>("Дата заявки");
        arriveDateColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getArriveDate()));

        TableColumn<Request, Long> contractorColumn = new TableColumn<>("ID заявителя");
        contractorColumn.setCellValueFactory(cellData -> new SimpleLongProperty(cellData.getValue().getContractor().getContractorId()).asObject());

        requestTable.getColumns().setAll(idColumn, arriveDateColumn, contractorColumn);

    }

    private void refreshTableData() {
        ResponseEntity<Request[]> response = restTemplate.getForEntity(serverUrl + "/data-request", Request[].class);
        Request[] requestsArray = response.getBody();
        List<Request> requestsList = Arrays.asList(requestsArray);
        requestTable.setItems(FXCollections.observableArrayList(requestsList));
    }

    private void clearInputFields() {
        arriveDateTextField.clear();
        contractorIdTextField.clear();
        arriveDateTextField1.clear();
        contractorIdTextField1.clear();
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
    private Date parseDate(String dateString) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return format.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    private String formatDate(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }
}
