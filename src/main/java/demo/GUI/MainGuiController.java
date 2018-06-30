package demo.GUI;

import demo.GUI.Alerts.Alerts;
import demo.controller.Entity.Customer;
import demo.model.service.CustomerService;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.util.StringConverter;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.net.URL;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.ResourceBundle;

/**
 * @author Artur Kuzmik on 18.21.6
 */


public class MainGuiController implements Initializable {

    @Autowired
    public CustomerService customerService;

    @Autowired
    public Alerts alerts;

    private ObservableList<Customer> data;

    @FXML
    private AnchorPane mainPane;

    @FXML
    private BorderPane borderPane;

    @FXML
    private TableView<Customer> table;

    @FXML
    private TabPane tabPane;

    @FXML
    private Tab debtorsTab;

    @FXML
    private TextField nameField;

    @FXML
    private TextField surnameField;

    @FXML
    private TextField costField;

    @FXML
    private TextField paidField;

    @FXML
    private TextField deleteField;

    @FXML
    private TextField editNameField;

    @FXML
    private TextField editSurnameField;

    @FXML
    private TextField editCostField;

    @FXML
    private TextField editPaidField;

    @FXML
    private TextField editSearchField;

    @FXML
    private DatePicker editDatePicker;

    @FXML
    private Button editButton;

    @FXML
    private TextField allPriceField;

    @FXML
    private DatePicker datePicker;

    @FXML
    private void closeButtonAction() {
        Platform.exit();
    }

    @FXML
    private void deleteButtonAction() {
        try {
            customerService.deleteCustomer(Long.parseLong(deleteField.getText()));
            data.removeIf(customer1 -> customer1.getId() == Long.parseLong(deleteField.getText()));
            deleteField.setText("");
        } catch (NoSuchElementException | NumberFormatException e) {
            alerts.deleteErrorAlert();
        }
    }

    @FXML
    private void addButtonAction() {
        try {
            Customer customer = addCustomerPrepare();

            customerService.addCustomer(customer);
            data.add(customer);


            nameField.setText("");
            surnameField.setText("");
            costField.setText("");
            paidField.setText("");
            datePicker.setPromptText("yyyy-MM-dd");

        } catch (IllegalArgumentException e) {
            alerts.addDataErrorAlert();
        } catch (NullPointerException e) {
            alerts.addEmptyErrorAlert();
        }
    }

    @FXML
    private void sortButtonAction() {
        data.sort(Comparator.comparing(Customer::getOrderDate)
                .thenComparing(Customer::getSurname));
    }

    @FXML
    private void allPriceButtonAction() {
        allPriceField.setText(String.valueOf(customerService.allPrice()));
    }

    @FXML
    private void debtorsButtonAction() {
        ObservableList<Customer> debtorsData = FXCollections.observableArrayList();
        data.stream()
                .filter(customer -> customer.getPaid() < customer.getCost())
                .forEach(debtorsData::add);

        table.setItems(debtorsData);
    }

    @FXML
    private void editButtonAction() {
        try {

            Customer customer = customerService.find(Long.parseLong(editSearchField.getText()));

            editNameField.setText(customer.getName());
            editSurnameField.setText(customer.getSurname());
            editCostField.setText(String.valueOf(customer.getCost()));
            editPaidField.setText(String.valueOf(customer.getPaid()));
            editDatePicker.setValue(LocalDate.parse(customer.getOrderDate()));

            setVisibleTrue();

        } catch (NullPointerException e) {
            alerts.deleteErrorAlert();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        tabPane.getSelectionModel().selectedItemProperty().addListener((ov, oldTab, newTab) -> {
            if (oldTab.getId().equals(debtorsTab.getId())) {
                table.setItems(data);
            }
        });

        mainPane.widthProperty().addListener((obs, oldVal, newVal) ->
                borderPane.setPrefWidth(newVal.doubleValue()));

        mainPane.heightProperty().addListener((obs, oldVal, newVal) ->
                borderPane.setPrefHeight(newVal.doubleValue()));


        table.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("id"));
        table.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("name"));
        table.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("surname"));
        table.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("orderDate"));
        table.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("cost"));
        table.getColumns().get(5).setCellValueFactory(new PropertyValueFactory<>("paid"));


        datePicker.setConverter(new StringConverter<LocalDate>() {
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

            @Override
            public String toString(LocalDate date) {
                if (date != null) {
                    return dateTimeFormatter.format(date);
                } else {
                    return "";
                }
            }

            @Override
            public LocalDate fromString(String string) {
                if (string != null && !string.isEmpty()) {
                    return LocalDate.parse(string, dateTimeFormatter);
                } else {
                    return null;
                }
            }
        });

        editDatePicker.setConverter(datePicker.getConverter());

    }

    @PostConstruct
    public void init() {
        List<Customer> customers = customerService.findAll();
        data = FXCollections.observableArrayList(customers);
        table.setItems(data);

    }

    private void setVisibleTrue() {
        editNameField.setVisible(true);
        editSurnameField.setVisible(true);
        editCostField.setVisible(true);
        editPaidField.setVisible(true);
        editButton.setVisible(true);
        editDatePicker.setVisible(true);

    }

    private void setVisibleFalse() {
        editNameField.setVisible(false);
        editSurnameField.setVisible(false);
        editCostField.setVisible(false);
        editPaidField.setVisible(false);
        editButton.setVisible(false);
        editDatePicker.setVisible(false);
    }

    private Customer addCustomerPrepare() {
        Customer customer;

        if (tabPane.getSelectionModel().getSelectedIndex() == 0) {
            customer = new Customer(nameField.getText(), surnameField.getText(),
                    datePicker.getValue().toString(), Double.parseDouble(costField.getText()),
                    Double.parseDouble(paidField.getText()));
        } else {
            customer = customerService.find(Long.parseLong(editSearchField.getText()));

            customer.setName(editNameField.getText());
            customer.setSurname(editSurnameField.getText());
            customer.setOrderDate(editDatePicker.getValue().toString());
            customer.setCost(Double.parseDouble(editCostField.getText()));
            customer.setPaid(Double.parseDouble(editPaidField.getText()));
            data.removeIf(customer1 -> customer1.getId() == Long.parseLong(editSearchField.getText()));
            setVisibleFalse();
        }

        return customer;
    }

}
