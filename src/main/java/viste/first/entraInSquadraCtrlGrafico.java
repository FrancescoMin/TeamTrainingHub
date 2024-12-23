package viste.first;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class entraInSquadraCtrlGrafico {

    @FXML
    private TextField scriviSquadraTextField;

    @FXML
    private Button richiediIngressoButton;

    @FXML
    public void initialize() {
        richiediIngressoButton.setOnAction(event -> handleRichiediIngressoButtonAction());
    }

    private void handleRichiediIngressoButtonAction() {
        String nomeSquadra = scriviSquadraTextField.getText();
        if (nomeSquadra != null && !nomeSquadra.isEmpty()) {
            // Logica per gestire la squadra in input
            System.out.println("Nome della squadra inserito: " + nomeSquadra);
            // Close the dialog
            Stage stage = (Stage) richiediIngressoButton.getScene().getWindow();
            stage.close();
        } else {
            System.out.println("Il nome della squadra non può essere vuoto.");
        }
    }
}