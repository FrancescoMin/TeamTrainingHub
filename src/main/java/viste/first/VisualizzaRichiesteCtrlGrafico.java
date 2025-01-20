package viste.first;


import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.control.*;
import modelli.Utente;
import viste.first.utils.GestoreTabella;

import java.net.URL;
import java.util.*;

public class VisualizzaRichiesteCtrlGrafico implements Initializable, GestoreTabella.ButtonActionHandler {

    @FXML
    private TableColumn<Utente, String> giocatoreColonna;

    @FXML
    private TableColumn<Utente, Void> approveColumn;

    private GestoreTabella gestoreTabella;

    @FXML
    private TableView<Utente> tabellaRichieste;

    @FXML
    protected void Ricarica(){gestoreTabella.refreshTable(tabellaRichieste);}

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        gestoreTabella = new GestoreTabella(this);  // 'this' refers to the controller

        // Populate the TableView with the list of users
        gestoreTabella.populateTable(tabellaRichieste);
    }

    @FXML
    protected void onBackClick(ActionEvent event) {
        System.out.println("Back clicked");
    }
    @Override
    public void handleAccept(Utente utente) {
        // Show an alert to indicate acceptance
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Accept User");
        alert.setHeaderText(null);
        alert.setContentText("User " + utente.getEmail() + " has been accepted!");
        alert.showAndWait();
    }

    // Implementation of the handleRefuse method from ButtonActionHandler
    @Override
    public void handleRefuse(Utente utente) {
        // Show an alert to indicate refusal
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Refuse User");
        alert.setHeaderText(null);
        alert.setContentText("User " + utente.getEmail() + " has been refused!");
        alert.showAndWait();
    }

    /** Public perché deve essere chiamata da DoubleButtonTableCell, è l'azione che viene compiuta al click del bottone Accept o Reject */
    public void handlerButton() {}
}