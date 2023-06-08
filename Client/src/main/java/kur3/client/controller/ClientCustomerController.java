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
import kur3.server.entity.Customer;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class ClientCustomerController {

    @FXML
    private TableView<Customer> customerTable;
    @FXML
    private TableColumn<Customer, Long> idColumn;
    @FXML
    private TableColumn<Customer, String> nameColumn;
    @FXML
    private TableColumn<Customer, String> addressColumn;
    @FXML
    private TableColumn<Customer, String> legalAddressColumn;
    @FXML
    private TextField nameTextField;
    @FXML
    private TextField addressTextField;
    @FXML
    private TextField legalAddressTextField;
    @FXML
    private TextField nameTextField1;
    @FXML
    private TextField addressTextField1;
    @FXML
    private TextField legalAddressTextField1;

    @FXML
    public Button addButton;
    @FXML
    public Button editButton;
    @FXML
    public Button deleteButton;
    @FXML
    public Button backButton;

    private final RestTemplate restTemplate;
    private final String serverUrl = "http://localhost:8080";

    public ClientCustomerController() {
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
        String name = nameTextField.getText();
        String address = addressTextField.getText();
        String legalAddress = legalAddressTextField.getText();

        Customer customer = new Customer();
        customer.setName(name);
        customer.setAddress(address);
        customer.setLegalAddress(legalAddress);

        HttpEntity<Customer> request = new HttpEntity<>(customer);
        restTemplate.postForObject(serverUrl + "/add-customer", request, Void.class);

        clearInputFields();
        refreshTableData();

        showSuccessAlert("Customer added successfully");
    }

    @FXML
    private void editButton(ActionEvent event) {
        Customer selectedCustomer = customerTable.getSelectionModel().getSelectedItem();
        if (selectedCustomer != null) {
            String name = nameTextField1.getText();
            String address = addressTextField1.getText();
            String legalAddress = legalAddressTextField1.getText();

            selectedCustomer.setName(name);
            selectedCustomer.setAddress(address);
            selectedCustomer.setLegalAddress(legalAddress);

            HttpEntity<Customer> request = new HttpEntity<>(selectedCustomer);
            restTemplate.put(serverUrl + "/edit-customer/{id}", request, selectedCustomer.getCustomerId());

            clearInputFields();
            refreshTableData();

            showSuccessAlert("Customer updated successfully");
        } else {
            showErrorAlert("No customer selected");
        }
    }

    @FXML
    private void deleteButton(ActionEvent event) {
        Customer selectedCustomer = customerTable.getSelectionModel().getSelectedItem();
        if (selectedCustomer != null) {
            restTemplate.delete(serverUrl + "/delete-customer/{id}", selectedCustomer.getCustomerId());

            clearInputFields();
            refreshTableData();

            showSuccessAlert("Customer deleted successfully");
        } else {
            showErrorAlert("No customer selected");
        }
    }

    private void initializeTable() {
        idColumn.setCellValueFactory(cellData -> new SimpleLongProperty(cellData.getValue().getCustomerId()).asObject());
        nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        addressColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAddress()));
        legalAddressColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getLegalAddress()));

        customerTable.getColumns().setAll(idColumn, nameColumn, addressColumn, legalAddressColumn);
        customerTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showCustomerDetails(newValue));
    }

    private void showCustomerDetails(Customer customer) {
        if (customer != null) {
            nameTextField.setText(customer.getName());
            addressTextField.setText(customer.getAddress());
            legalAddressTextField.setText(customer.getLegalAddress());
        } else {
            clearInputFields();
        }
    }

    private void refreshTableData() {
        ResponseEntity<Customer[]> response = restTemplate.getForEntity(serverUrl + "/data-customer", Customer[].class);
        Customer[] customersArray = response.getBody();
        List<Customer> customersList = Arrays.asList(customersArray);
        customerTable.setItems(FXCollections.observableArrayList(customersList));
    }

    private void clearInputFields() {
        nameTextField.clear();
        addressTextField.clear();
        legalAddressTextField.clear();
        nameTextField1.clear();
        addressTextField1.clear();
        legalAddressTextField1.clear();
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
