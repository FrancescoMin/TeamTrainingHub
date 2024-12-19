package viste.first;

import controllerApplicativo.CreazioneSquadraControllerApplicativo;
import engineering.bean.AllenatoreBean;
import engineering.bean.UtenteBean;
import engineering.eccezioni.EccezioneGenerica;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import viste.first.utils.CambioScena;

import static viste.first.utils.FxmlFileName.*;

public class CreazioneSquadraControllerGrafico {

    @FXML
    private TextField nomeSquadra;

    @FXML
    private Button creaSquadra;

    @FXML
    private Label messaggioErrore;


    public void initialize() {
        System.out.println("Inizializzazione Temporanea della creazione della squadra");
    }

    @FXML
    public void CreaSquadra(ActionEvent actionEvent) {

        if (nomeSquadra.getText().isEmpty()) {
            messaggioErrore.setText("There are empty fields!");
            messaggioErrore.setVisible(true);
        }

        else
        {
            System.out.println("Nome squadra: " + nomeSquadra.getText());
            //implementazione della logica per la creazione della squadra

            CreazioneSquadraControllerApplicativo creazioneSquadraControllerApplicativo = new CreazioneSquadraControllerApplicativo();

            UtenteBean utenteBeanDiProva= new AllenatoreBean("UserProva","a", "1");

            //creazione della squadra con il nome inserito dall'utente e lo lego all'utenteBean
            creazioneSquadraControllerApplicativo.creazioneSquadra(utenteBeanDiProva, nomeSquadra.getText());

            //finita la logica cambio scena
            //CODICE TEMPORANEO PER IL PASSAGGIO DI SCENE ALLA PAGINA DI REGISTRAZIONE
            try {
                Stage stage = (Stage) nomeSquadra.getScene().getWindow();
                CambioScena cambioScena = new CambioScena();
                cambioScena.cambioScena(stage, PAGINA_HOME);

            } catch (EccezioneGenerica EccezioneGenerica) {
                System.out.println(EccezioneGenerica.getMessage());
            }
        }


        //implementazione della logica per il controllo della corretta formattazione del nome della squadra
    }


}
