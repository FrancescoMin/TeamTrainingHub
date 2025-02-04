package viste.first;

import ctrl_applicativo.CreazioneAllenamentoCtrlApplicativo;
import engineering.eccezioni.EccezioneAllenamentoInvalido;
import engineering.eccezioni.EccezioneCambioScena;
import engineering.eccezioni.EccezioneUtenteInvalido;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import engineering.bean.AllenamentoBean;
import javafx.stage.Stage;
import viste.first.utils.CambioScena;

import static viste.first.utils.FxmlFileName.PAGINA_HOME_ALLENATORE;

public class CreazioneAllenamentoCtrlGrafico {

    @FXML
    private Label labelErrori;

    @FXML
    private TextField giornoAllenamento;

    @FXML
    private TextField meseAllenamento;

    @FXML
    private TextField annoAllenamento;

    @FXML
    private TextField oraInizio;

    @FXML
    private TextField minutoInizio;

    @FXML
    private TextField oraFine;

    @FXML
    private TextField minutoFine;

    @FXML
    private TextField descrizioneAllenamento;

    @FXML
    private Button creaAllenamento;

    public void initialize() {
        labelErrori.setVisible(false);
    }

    @FXML
    private void tornaIndietro() throws EccezioneCambioScena {
        try {
            Stage stage = (Stage) labelErrori.getScene().getWindow();
            CambioScena cambioScena = new CambioScena();
            cambioScena.cambioScena(stage, PAGINA_HOME_ALLENATORE);

        } catch (EccezioneCambioScena e) {
            throw new EccezioneCambioScena(e.getMessage());
        }
    }

    @FXML
    public void creaAllenamento() {
        try {
            int giorno = Integer.parseInt(giornoAllenamento.getText());
            int mese = Integer.parseInt(meseAllenamento.getText());
            int anno = Integer.parseInt(annoAllenamento.getText());
            int oraIn = Integer.parseInt(oraInizio.getText());
            int minutoIn = Integer.parseInt(minutoInizio.getText());
            int oraFin = Integer.parseInt(oraFine.getText());
            int minutoFin = Integer.parseInt(minutoFine.getText());

            String descrizione = descrizioneAllenamento.getText();

            if(giorno < 1 || giorno > 31)       {throw new EccezioneUtenteInvalido("Giorno non valido");}
            if (mese < 1 || mese > 12)          {throw new EccezioneUtenteInvalido("Mese non valido");}
            if (anno < 2025 )                   {throw new EccezioneUtenteInvalido("Anno non valido");}
            if (oraIn < 0 || oraIn > 23)        {throw new EccezioneUtenteInvalido("Ora di inizio non valida");}
            if (minutoIn < 0 || minutoIn > 59)  {throw new EccezioneUtenteInvalido("Minuto di inizio non valido");}
            if (oraFin < 0 || oraFin > 23)      {throw new EccezioneUtenteInvalido("Ora di fine non valida");}
            if (minutoFin < 0 || minutoFin > 59){throw new EccezioneUtenteInvalido("Minuto di fine non valido");}

            String orarioInizio = String.format("%02d-%02d", oraIn, minutoIn);
            String orarioFine = String.format("%02d-%02d", oraFin, minutoFin);

            //creazione della data con cui verrà salvato l'allenamento
            String data = String.format("%02d-%02d-%04d", giorno, mese, anno);

            //creazione del ben dell'allenamento da far salvare al sistema
            AllenamentoBean allenamentobean = new AllenamentoBean(data, orarioInizio, orarioFine, descrizione);

            //richiediamo al sistema di salvare il bean dell'allenamento
            CreazioneAllenamentoCtrlApplicativo creazioneallenamentoctrlapplicativo = new CreazioneAllenamentoCtrlApplicativo();
            creazioneallenamentoctrlapplicativo.creaAllenamento(allenamentobean);

            //abbiamo completato il salvataggio e lo facciamo vedere con una stampa a schermo


            cambiaScena();

        } catch (EccezioneCambioScena | EccezioneAllenamentoInvalido e) {
            labelErrori.setText(e.getMessage());
            labelErrori.setVisible(true);
        }
        catch (Exception e) {
            labelErrori.setText("Errore con il format delle date");
            labelErrori.setVisible(true);
        }
    }

    private void cambiaScena() throws EccezioneCambioScena {
        //facciamo il cambio scena per tornare alla home dell'allenatore
        try {
            Stage stage = (Stage) labelErrori.getScene().getWindow();
            CambioScena cambioScena = new CambioScena();
            cambioScena.cambioScena(stage, PAGINA_HOME_ALLENATORE);

        } catch (EccezioneCambioScena e) {
            throw new EccezioneCambioScena(e.getMessage());
        }
    }
}
