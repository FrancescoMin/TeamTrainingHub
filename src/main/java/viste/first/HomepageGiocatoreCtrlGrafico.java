package viste.first;

import ctrl_applicativo.HomepageGiocatoreCtrlApplicativo;
import engineering.eccezioni.EccezioneCambioScena;
import engineering.eccezioni.EccezioneUtenteInvalido;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import viste.first.utils.CambioScena;

import static viste.first.utils.FxmlFileName.*;


public class HomepageGiocatoreCtrlGrafico {

    @FXML
    private Label welcomeLabel;

    @FXML
    private Label mostraErrori;

    HomepageGiocatoreCtrlApplicativo homepagegiocatorectrlapplicativo = new HomepageGiocatoreCtrlApplicativo();

    public void initialize() {
        String username;
        username= homepagegiocatorectrlapplicativo.getMessaggioBenvenuto();
        welcomeLabel.setText("Ciao " + username);
    }

    @FXML
    protected void entraSquadra(){
        try {
            if (homepagegiocatorectrlapplicativo.isUtenteInSquadra()) {
                throw new EccezioneUtenteInvalido("Sei già in una squadra");
            }
            cambio(PAGINA_ENTRAINSQUADRA);
        }
        catch (EccezioneUtenteInvalido | EccezioneCambioScena e) {
            mostra(e.getMessage());
        }
    }

    @FXML
    protected void iscrizioneAllenamento(){
        if (homepagegiocatorectrlapplicativo.isUtenteInSquadra()) {
            try {
                cambio(PAGINA_ISCRIZIONE_ALLENAMENTO);
            }
            catch (EccezioneCambioScena e) {
                mostra("Errore con il cambio di scena alla pagina di iscrizione ad un allenamento");
            }
        }
        else {
            mostra("Non sei in una squadra, non puoi iscriverti ad un allenamento");
        }

    }

    @FXML
    private void consultaAllenamenti() {
        // Logica per il pulsante "Consulta allenamenti"
        if(homepagegiocatorectrlapplicativo.isUtenteInSquadra()) {
            try {
                ConsultaAllenamentiCtrlGrafico.setPaginaHome(PAGINA_HOME_GIOCATORE);
                cambio(PAGINA_CONSULTA_ALLENAMENTI);

            } catch (EccezioneCambioScena e) {
                mostra("Errore con il cambio di scena alla pagina di consultazione degli allenamenti");
            }
        }
        else {
            mostra("Non sei in una squadra, non puoi consultare gli allenamenti");
        }
    }

    public void mostra(String messaggio){
        // Imposta il testo della Label
        mostraErrori.setText(messaggio);

        // Cambia il colore del testo della Label in un colore che contrasta bene con il verde (#1DB954)
        mostraErrori.setStyle("-fx-text-fill: blue; -fx-font-size: 16px;"); // Bianco, ma puoi usare un altro colore che ti piace
        mostraErrori.setVisible(true);
    }

    private void cambio(String string){
        try {
            Stage stage = (Stage) welcomeLabel.getScene().getWindow();
            CambioScena cambioScena = new CambioScena();
            cambioScena.cambioScena(stage, string);
        }
        catch (EccezioneCambioScena e){
            throw new EccezioneCambioScena(e.getMessage());
        }
    }
}