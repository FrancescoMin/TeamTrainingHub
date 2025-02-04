package viste.first.basi;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import viste.first.utils.CambioScena;

public class BaseTabelleCtrlGrafico {

    @FXML
    protected Label titoloTabella;

    private static String paginaPrecedente;

    @FXML
    protected void onBackClick() {
        try {
            Stage stage = (Stage) titoloTabella.getScene().getWindow();
            CambioScena cambioScena = new CambioScena();
            cambioScena.cambioScena(stage, getPaginaPrecedente());

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void setPaginaPrecedente(String paginaPrecedente) {
        BaseTabelleCtrlGrafico.paginaPrecedente = paginaPrecedente;
    }
    public static String getPaginaPrecedente() {
        return paginaPrecedente;
    }

}
