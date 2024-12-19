package viste.first;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;

public class CreazioneAllenamentoControllerGrafico{


    @FXML
    private TextField nomeSquadra;

    @FXML
    private TextField giornoAllenamento;

    @FXML
    private TextField meseAllenamento;

    @FXML
    private TextField annoAllenamento;

    @FXML
    private TextField durataAllenamento;

    @FXML
    private TextField descrizioneAllenamento;

    @FXML
    private Button creaAllenamento;

    public void initialize() {
        System.out.println("Inizializzazione Temporanea della Pagina di Creazione Allenamento");
        // Populate the ChoiceBox with items

    }

    @FXML
    public void CreaAllenamento() {
        System.out.println("Creazione Allenamento");

    }

}
