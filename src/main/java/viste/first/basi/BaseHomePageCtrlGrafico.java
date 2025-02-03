package viste.first.basi;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import viste.first.utils.CambioScena;

import static viste.first.utils.FxmlFileName.PAGINA_PRINCIPALE;

public class BaseHomePageCtrlGrafico {

    @FXML
    private  Button tornaAlLogin;

    public void tornaAlLogin() {
        System.out.println("Torno al login");
        try {
            Stage stage = (Stage) tornaAlLogin.getScene().getWindow();
            CambioScena cambioScena = new CambioScena();
            cambioScena.cambioScena(stage, PAGINA_PRINCIPALE);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
