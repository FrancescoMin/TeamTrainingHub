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
    private final Singleton istanza;
    private final ButtonActionHandler buttonActionHandler;

    // Callback interface for handling button actions in the controller
    public interface ButtonActionHandler {
        void handleAccept(UtenteBean utenteBean);
        void handleRefuse(UtenteBean utenteBean);
    }

    // Constructor to set the ButtonActionHandler and initialize Singleton
    public GestoreTabella(ButtonActionHandler buttonActionHandler) {
        this.buttonActionHandler = buttonActionHandler;
        this.istanza = Singleton.getInstance(); // Initialize Singleton here
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

            // Costruttore della cella
            {
                // Inizializza le azioni dei pulsanti nel blocco di inizializzazione
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
                    setGraphic(null);  // Rimuovi i pulsanti se la cella è vuota
                } else {
                    setGraphic(new HBox(10, acceptButton, refuseButton));  // Aggiungi i pulsanti alla cella
                }
            }
        };
    }
}