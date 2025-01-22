package viste.first;

import engineering.eccezioni.EccezioneGenerica;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import viste.first.utils.CambioScena;

import static viste.first.utils.FxmlFileName.PAGINA_PRINCIPALE;

public class BaseVisteCtrlGrafico {

    @FXML
    private Button tornaAlLogin;


    public void TornaAlLogin(ActionEvent actionEvent) {
        System.out.println("Torno al login");
        try {
            Stage stage = (Stage) tornaAlLogin.getScene().getWindow();
            CambioScena cambioScena = new CambioScena();
            cambioScena.cambioScena(stage, PAGINA_PRINCIPALE);

        } catch (EccezioneGenerica eccezioneGenerica) {
            System.out.println(eccezioneGenerica.getMessage());
        }
    }
}
