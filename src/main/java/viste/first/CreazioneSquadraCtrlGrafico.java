package viste.first;

import ctrlApplicativo.CreazioneSquadraCtrlApplicativo;
import engineering.eccezioni.EccezioneGenerica;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import viste.first.utils.CambioScena;

import static viste.first.utils.FxmlFileName.*;

public class CreazioneSquadraCtrlGrafico {

    @FXML
    private TextField nomeSquadra;

    @FXML
    private Button creaSquadra;

    @FXML
    private Label messaggioErrore;

    @FXML
    protected void TornaIndietro(ActionEvent event) {
        //cambio scena alla pagina di login
        try {
            Stage stage = (Stage) nomeSquadra.getScene().getWindow();
            CambioScena cambioScena = new CambioScena();
            cambioScena.cambioScena(stage, PAGINA_HOME_ALLENATORE);

        } catch (EccezioneGenerica EccezioneGenerica) {
            System.out.println(EccezioneGenerica.getMessage());
        }
    }

    public void initialize() {
        System.out.println("Inizializzazione Temporanea della creazione della squadra");
    }

    @FXML
    public void CreaSquadra(ActionEvent actionEvent) {
        try {
            if (nomeSquadra.getText().isEmpty()) {
                messaggioErrore.setText("There are empty fields!");
                messaggioErrore.setVisible(true);

            } else {
                System.out.println("Nome squadra: " + nomeSquadra.getText());
                //implementazione della logica per la creazione della squadra

                //inizializziamo il controller applicativo
                CreazioneSquadraCtrlApplicativo creazioneSquadraCtrlApplicativo = new CreazioneSquadraCtrlApplicativo();

                //creazione della squadra con il nome inserito dall'utente e lo lego all'utenteBean
                creazioneSquadraCtrlApplicativo.creazioneSquadra(nomeSquadra.getText());

                //finita la logica cambio la scena
                //CODICE TEMPORANEO PER IL PASSAGGIO DI SCENE ALLA PAGINA DI REGISTRAZIONE
                try {
                    Stage stage = (Stage) nomeSquadra.getScene().getWindow();
                    CambioScena cambioScena = new CambioScena();
                    cambioScena.cambioScena(stage, PAGINA_HOME_ALLENATORE);

                } catch (EccezioneGenerica EccezioneGenerica) {
                    System.out.println(EccezioneGenerica.getMessage());
                }
            }
        } catch (EccezioneGenerica e) {
            messaggioErrore.setText(e.getMessage());
            messaggioErrore.setVisible(true);
        }


        //implementazione della logica per il controllo della corretta formattazione del nome della squadra
    }


}
