package viste.first;

import ctrlApplicativo.HomepageGiocatoreCtrlApplicativo;
import engineering.eccezioni.EccezioneGenerica;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import viste.first.utils.CambioScena;

import static viste.first.utils.FxmlFileName.*;


public class HomepageGiocatoreCtrlGrafico {

    @FXML
    private Label welcomeLabel;

    HomepageGiocatoreCtrlApplicativo homepageGiocatoreCtrlApplicativo = new HomepageGiocatoreCtrlApplicativo();

    public void initialize() {
        String username;
        username=homepageGiocatoreCtrlApplicativo.getMessaggioBenvenuto();
        welcomeLabel.setText("Ciao " + username);
    }

    @FXML
    protected void entraSquadra(){
        try {
            if (homepageGiocatoreCtrlApplicativo.isUtenteInSquadra()) {
                throw new EccezioneGenerica("Sei già in una squadra");
            }
            cambio(PAGINA_ENTRAINSQUADRA);

        } catch (EccezioneGenerica eccezioneGenerica) {
            System.out.println(eccezioneGenerica.getMessage());
        }
    }

    @FXML
    protected void IscrizioneAllenamento(){
        try {

            cambio(PAGINA_ISCRIZIONE_ALLENAMENTO);

        } catch (EccezioneGenerica eccezioneGenerica) {
            System.out.println(eccezioneGenerica.getMessage());
        }
    }

    @FXML
    private void consultaAllenamenti() {
        // Logica per il pulsante "Consulta allenamenti"
        System.out.println("Consulta allenamenti cliccato");

        if(homepageGiocatoreCtrlApplicativo.isUtenteInSquadra()) {
            try {
                cambio(PAGINA_CONSULTA_ALLENAMENTI);

            } catch (EccezioneGenerica eccezioneGenerica) {
                System.out.println(eccezioneGenerica.getMessage());
            }
        }
        else {
            System.out.println("Non sei in una squadra, entrare in una squadra per consultare gli allenamenti");
        }
    }

    private void cambio(String string){
        Stage stage = (Stage) welcomeLabel.getScene().getWindow();
        CambioScena cambioScena = new CambioScena();
        cambioScena.cambioScena(stage, string);
    }
}