package kur3.client.controller;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
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
import kur3.server.entity.Contract;
import kur3.server.entity.Contractor;
import kur3.server.entity.Customer;
import kur3.server.entity.Request;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class ClientContractController {

    @FXML
    private TableView<Contract> showContractsTable;
    @FXML
    private TableColumn<Contract, Long> idColumn;
    @FXML
    private TableColumn<Contract, String> nameColumn;
    @FXML
    private TableColumn<Contract, Double> priceColumn;
    @FXML
    private TableColumn<Contract, Request> requestColumn;
    @FXML
    private TableColumn<Contract, Customer> customerColumn;
    @FXML
    private TextField contractIdTextField;
    @FXML
    private TextField contractNameTextField;
    @FXML
    private TextField priceTextField;
    @FXML
    private TextField requestIdTextField;
    @FXML
    private TextField customerIdTextField;
    @FXML
    private TextField contractIdTextField1;
    @FXML
    private TextField contractNameTextField1;
    @FXML
    private TextField priceTextField1;
    @FXML
    private TextField requestIdTextField1;
    @FXML
    private TextField customerIdTextField1;
    @FXML
    private Button addButton;
    @FXML
    private Button backButton;
    @FXML
    private Button deleteButton;
    @FXML

    private final RestTemplate restTemplate;
    private final String serverUrl = "http://localhost:8080";

    public ClientContractController() {
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
        String contractName = contractNameTextField.getText();
        double price = Double.parseDouble(priceTextField.getText());
        String requestId = requestIdTextField.getText();
        String  customerId = customerIdTextField.getText();

        Request request = new Request();

        Customer customer = new Customer();

        request.setRequestId(Long.parseLong(requestId));

        customer.setCustomerId(Long.parseLong(customerId));

        Contract contract = new Contract(null, contractName, price, Long.parseLong(requestId), Long.parseLong(customerId));

        HttpEntity<Contract> requestEntity = new HttpEntity<>(contract);
        restTemplate.postForObject(serverUrl + "/contracts-add", requestEntity, Void.class);

        clearInputFields();
        refreshTableData();

        showSuccessAlert("Contract added successfully");
    }


    @FXML
    private void deleteButton(ActionEvent event) {
        Contract selectedContract = showContractsTable.getSelectionModel().getSelectedItem();
        if (selectedContract != null) {
            Long contractId = selectedContract.getContractId();
            restTemplate.exchange(serverUrl + "/contracts-delete/{id}", HttpMethod.DELETE, null, Void.class, contractId);
            refreshTableData();
            showSuccessAlert("Contract deleted successfully");
        } else {
            showErrorAlert("No contract selected");
        }
    }

    private void initializeTable() {
        TableColumn<Contract, Long> idColumn = new TableColumn<>("Договор ID");
        idColumn.setCellValueFactory(cellData -> new SimpleLongProperty(cellData.getValue().getContractId()).asObject());

        TableColumn<Contract, String> nameColumn = new TableColumn<>("Название");
        nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getContractName()));

        TableColumn<Contract, Double> priceColumn = new TableColumn<>("Цена");
        priceColumn.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getPrice()).asObject());

        TableColumn<Contract, Long> requestColumn = new TableColumn<>("ID заявки");
        requestColumn.setCellValueFactory(cellData -> new SimpleLongProperty(cellData.getValue().getRequest().getRequestId()).asObject());

        TableColumn<Contract, Long> customerColumn = new TableColumn<>("ID заказчика");
        customerColumn.setCellValueFactory(cellData -> new SimpleLongProperty(cellData.getValue().getCustomer().getCustomerId()).asObject());

        showContractsTable.getColumns().setAll(idColumn, nameColumn, priceColumn, requestColumn, customerColumn);

    }

    private void refreshTableData() {
        ResponseEntity<Contract[]> response = restTemplate.getForEntity(serverUrl + "/contracts", Contract[].class);
        Contract[] contractArray = response.getBody();
        List<Contract> contractList = Arrays.asList(contractArray);
        showContractsTable.setItems(FXCollections.observableArrayList(contractList));
    }

    private void clearInputFields() {
        contractNameTextField.clear();
        priceTextField.clear();
        requestIdTextField.clear();
        customerIdTextField.clear();
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

