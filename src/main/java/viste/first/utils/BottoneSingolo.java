package viste.first.utils;

import engineering.bean.AllenamentoBean;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import viste.first.IscrivitiAllenamentoCtrlGrafico;


// Classe TableCell personalizzata per aggiungere due bottoni nella cella
public class BottoneSingolo extends TableCell<AllenamentoBean, Boolean> {

    private final Button approveButton = new Button("V");

    public BottoneSingolo(IscrivitiAllenamentoCtrlGrafico iscrivitiAllenamentoCtrlGrafico) {

        approveButton.setOnAction(e -> {
            TableRow<AllenamentoBean> tableRow = getTableRow();
            if (tableRow != null) {
                AllenamentoBean allenamento = tableRow.getItem();
                handlePendingButton(iscrivitiAllenamentoCtrlGrafico, allenamento, true);
            }
        });


        approveButton.setStyle("-fx-background-color: #1DB954; -fx-text-fill: white; -fx-pref-height: 25px; -fx-pref-width: 25px; " +
                "-fx-min-width: -1; -fx-min-height: -1; -fx-background-radius: 50%; -fx-stroke: 50; -fx-border-radius: 50%;");

    }

    public void handlePendingButton(IscrivitiAllenamentoCtrlGrafico iscrivitiAllenamentoCtrlGrafico, AllenamentoBean allenamento, boolean approve) {
        iscrivitiAllenamentoCtrlGrafico.handlerButton(allenamento);
    }

    @Override
    protected void updateItem(Boolean item, boolean empty) {
        super.updateItem(item, empty);
        if (empty) {
            setGraphic(null);
        } else {
            setGraphic(createButtonBox(approveButton));
        }
    }

    private HBox createButtonBox(Button... buttons) {
        HBox buttonBox = new HBox(5); // 5 è lo spazio tra i bottoni
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.getChildren().addAll(buttons);
        return buttonBox;
    }
}