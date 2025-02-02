package viste.first.utils;

import engineering.bean.AllenatoreBean;
import engineering.bean.GiocatoreBean;
import engineering.bean.UtenteBean;
import engineering.pattern.Singleton;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import modelli.Utente;

import java.util.ArrayList;
import java.util.List;

public class GestoreTabella {

    Singleton istanza = Singleton.getInstance();

    // Callback interface for handling button actions in the controller
    public interface ButtonActionHandler {
        void handleAccept(UtenteBean utenteBean);
        void handleRefuse(UtenteBean utenteBean);
    }

    private ButtonActionHandler buttonActionHandler;

    // Constructor to set the ButtonActionHandler
    public GestoreTabella(ButtonActionHandler buttonActionHandler) {
        this.buttonActionHandler = buttonActionHandler;
    }

    // Method to populate the TableView with a list of UtenteBean objects
    public void populateTable(TableView<UtenteBean> tableView) {
        // Get columns by fx:id (these should be linked via SceneBuilder)
        TableColumn<UtenteBean, String> nameColumn = (TableColumn<UtenteBean, String>) tableView.getColumns().get(0);
        TableColumn<UtenteBean, Void> actionColumn = (TableColumn<UtenteBean, Void>) tableView.getColumns().get(1);

        // Set the value factory for the name column
        nameColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getEmail()));

        // Set the cell factory for the action column (buttons)
        actionColumn.setCellFactory(col -> createButtonCell());

        // Populate the TableView with the list of users (UtenteBean)
        tableView.getItems().setAll(recuperaUtentiBean());
    }

    // Method to refresh the TableView
    public void refreshTable(TableView<UtenteBean> tableView) {
        tableView.getItems().setAll(recuperaUtentiBean());
    }

    private List<UtenteBean> recuperaUtentiBean() {
        List<UtenteBean> utentiBean = new ArrayList<>();
        // Modify to retrieve UtenteBean objects instead of Utente
        List<Utente> utenti = istanza.getUtenteCorrente().getSquadra().getRichiesteIngresso();
        for (Utente utente : utenti) {
            UtenteBean utenteBean;
            if (utente.getAllenatore()) {
                utenteBean = new AllenatoreBean(utente.getUsername(), utente.getEmail(), utente.getPassword(), utente.getAllenamenti(), utente.getSquadra());
            } else {
                utenteBean = new GiocatoreBean(utente.getUsername(), utente.getEmail(), utente.getPassword(), utente.getAllenamenti(), utente.getSquadra());
            }
            utentiBean.add(utenteBean);
        }
        return utentiBean;
    }

    // Method to create button cells for the action column
    private TableCell<UtenteBean, Void> createButtonCell() {
        return new TableCell<UtenteBean, Void>() {
            private final Button acceptButton = new Button("Accept");
            private final Button refuseButton = new Button("Refuse");

            {// Set button actions that delegate to the controller's methods via the callback
                acceptButton.setOnAction(event -> {
                    UtenteBean utenteBean = getTableView().getItems().get(getIndex());
                    buttonActionHandler.handleAccept(utenteBean);
                });
                refuseButton.setOnAction(event -> {
                    UtenteBean utenteBean = getTableView().getItems().get(getIndex());
                    buttonActionHandler.handleRefuse(utenteBean);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);  // Remove the buttons if the cell is empty
                } else {
                    setGraphic(new HBox(10, acceptButton, refuseButton));  // Add buttons to the cell
                }
            }
        };
    }
}
