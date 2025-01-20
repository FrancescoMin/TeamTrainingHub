package viste.first.utils;

import engineering.bean.AllenatoreBean;
import engineering.bean.GiocatoreBean;
import engineering.bean.UtenteBean;
import engineering.pattern.Singleton;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;

import modelli.Utente;

import java.util.List;

public class GestoreTabella {

    Singleton istanza =Singleton.getInstance();

    // Callback interface for handling button actions in the controller
    public interface ButtonActionHandler {
        void handleAccept(Utente utente);
        void handleRefuse(Utente utente);
    }

    private ButtonActionHandler buttonActionHandler;

    // Constructor to set the ButtonActionHandler
    public GestoreTabella(ButtonActionHandler buttonActionHandler) {
        this.buttonActionHandler = buttonActionHandler;
    }

    // Method to populate the TableView with a list of Utente objects
    public void populateTable(TableView<Utente> tableView) {
        // Get columns by fx:id (these should be linked via SceneBuilder)
        TableColumn<Utente, String> nameColumn = (TableColumn<Utente, String>) tableView.getColumns().get(0);
        TableColumn<Utente, Void> actionColumn = (TableColumn<Utente, Void>) tableView.getColumns().get(1);

        // Set the value factory for the name column
        nameColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getEmail()));

        // Set the cell factory for the action column (buttons)
        actionColumn.setCellFactory(col -> createButtonCell());

        // Populate the TableView with the list of users
        //tableView.getItems().setAll(receuperaUtenti());
        tableView.getItems().setAll(istanza.getUtenteCorrente().getSquadra().getRichiesteIngresso());
    }

    // Method to refresh the TableView
    public void refreshTable(TableView<Utente> tableView) {
//        tableView.getItems().setAll(receuperaUtenti());
        tableView.getItems().setAll(istanza.getUtenteCorrente().getSquadra().getRichiesteIngresso());
    }

    private List<UtenteBean> receuperaUtenti(){
        List<Utente> utenti= istanza.getUtenteCorrente().getSquadra().getRichiesteIngresso();
        List<UtenteBean> utentiBean = null;
        for(Utente utente:utenti){
            if(utente.getAllenatore()){
                UtenteBean utenteBean = new AllenatoreBean(utente.getUsername(), utente.getEmail(), utente.getPassword(), utente.getAllenamenti(),utente.getSquadra());
                utentiBean.add(utenteBean);
            }
            else {
                UtenteBean utenteBean = new GiocatoreBean(utente.getUsername(), utente.getEmail(), utente.getPassword(), utente.getAllenamenti(), utente.getSquadra());
                utentiBean.add(utenteBean);
            }
        }
        return utentiBean;
    }

    // Method to create button cells for the action column
    private TableCell<Utente, Void> createButtonCell() {
        return new TableCell<Utente, Void>() {
            private final Button acceptButton = new Button("Accept");
            private final Button refuseButton = new Button("Refuse");

            {
                // Set button actions that delegate to the controller's methods via the callback
                acceptButton.setOnAction(event -> {
                    Utente utente = getTableView().getItems().get(getIndex());
                    buttonActionHandler.handleAccept(utente);
                });
                refuseButton.setOnAction(event -> {
                    Utente utente = getTableView().getItems().get(getIndex());
                    buttonActionHandler.handleRefuse(utente);
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
