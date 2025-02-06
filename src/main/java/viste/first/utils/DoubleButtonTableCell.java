package viste.first.utils;


import engineering.bean.UtenteBean;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import viste.first.GestisciRichiesteCtrlGrafico;


// Classe TableCell personalizzata per aggiungere due bottoni nella cella
public class DoubleButtonTableCell extends TableCell<UtenteBean, Boolean> {

    private final Button approveButton = new Button("Accetta");
    private final Button rejectButton = new Button("Rifiuta");

    public DoubleButtonTableCell(GestisciRichiesteCtrlGrafico gestisciRichiesteCtrlGrafico) {

        approveButton.setOnAction(e -> {
            TableRow<UtenteBean> tableRow = getTableRow();
            if (tableRow != null) {
                UtenteBean utenteBean = tableRow.getItem();
                handlePendingButton(gestisciRichiesteCtrlGrafico, utenteBean, true);
            }
        });

        rejectButton.setOnAction(e -> {
            TableRow<UtenteBean> tableRow = getTableRow();
            if (tableRow != null) {
                UtenteBean utenteBean = tableRow.getItem();
                handlePendingButton(gestisciRichiesteCtrlGrafico, utenteBean, false);
            }
        });


        approveButton.setStyle("-fx-background-color: #1DB954; -fx-text-fill: white; -fx-pref-height: 25px; ; -fx-pref-width: 120px; " +
                "-fx-min-width: 80px; -fx-min-height: 25px; -fx-background-radius: 5px; -fx-stroke: 0; -fx-border-radius: 5px;");

        rejectButton.setStyle("-fx-background-color: red; -fx-text-fill: white;-fx-pref-height: 25px; ; -fx-pref-width: 120px; " +
                "-fx-min-width: 80px; -fx-min-height: 25px; -fx-background-radius: 5px; -fx-stroke: 0; -fx-border-radius: 5px;");
    }

    public void handlePendingButton(GestisciRichiesteCtrlGrafico gestisciRichiesteCtrlGrafico, UtenteBean utenteBean, boolean approve) {
        gestisciRichiesteCtrlGrafico.handlerButton(utenteBean,approve);
    }

    @Override
    protected void updateItem(Boolean item, boolean empty) {
        super.updateItem(item, empty);
        if (empty) {
            setGraphic(null);
        } else {
            setGraphic(createButtonBox(approveButton, rejectButton));
        }
    }

    private HBox createButtonBox(Button... buttons) {
        HBox buttonBox = new HBox(5); // 5 è lo spazio tra i bottoni
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.getChildren().addAll(buttons);
        return buttonBox;
    }
}