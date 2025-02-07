package viste.first.utils;

import engineering.bean.AllenamentoBean;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableRow;
import javafx.scene.layout.HBox;
import viste.first.IscrizioneAllenamentoCtrlGrafico;

// Classe TableCell personalizzata per aggiungere due bottoni nella cella
public class BottoneSingolo extends TableCell<AllenamentoBean, String> {

    private final Button linkButton = new Button("Iscriviti");

    public BottoneSingolo(IscrizioneAllenamentoCtrlGrafico iscrizioneAllenamentoCtrlGrafico) {

        linkButton.setOnAction(event -> {
            TableRow<AllenamentoBean> tableRow = getTableRow();
            if (tableRow != null) {
                AllenamentoBean allenamentoBean = tableRow.getItem();
                handlerSingolButton(iscrizioneAllenamentoCtrlGrafico,allenamentoBean);
            }
        });

        linkButton.setStyle("-fx-background-color: #1DB954; -fx-text-fill: white; -fx-pref-height: 25px; -fx-pref-width: 75px; " +
                "-fx-min-width: -1; -fx-min-height: -1; -fx-background-radius: 20; -fx-border-radius: 20; -fx-font-size: 12px");
    }

    private void handlerSingolButton(IscrizioneAllenamentoCtrlGrafico iscrizioneAllenamentoCtrlGrafico, AllenamentoBean allenamentoBean) {
        iscrizioneAllenamentoCtrlGrafico.gestoreBottone(allenamentoBean);
    }

    @Override
    protected void updateItem(String item, boolean empty) {
        super.updateItem(item, empty);
        if (empty) {
            setGraphic(null);
        } else {
            setGraphic(createButtonBox(linkButton));
        }
    }


    private HBox createButtonBox(Button... buttons) {
        HBox buttonBox = new HBox(5); // 5 è lo spazio tra i bottoni
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.getChildren().addAll(buttons);
        return buttonBox;
    }
}
