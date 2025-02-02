package viste.first.basi;

import engineering.eccezioni.EccezioneGenerica;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import viste.first.utils.CambioScena;

public class BaseTabelleCtrlGrafico {

    @FXML
    protected Label titoloTabella;

    public static String paginaPrecedente;

    @FXML
    protected void onBackClick() {
        try {
            Stage stage = (Stage) titoloTabella.getScene().getWindow();
            CambioScena cambioScena = new CambioScena();
            cambioScena.cambioScena(stage, paginaPrecedente);

        } catch (EccezioneGenerica e) {
            System.out.println(e.getMessage());
        }
    }

}
