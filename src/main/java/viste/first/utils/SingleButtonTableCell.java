package viste.first.utils;

import engineering.bean.AllenamentoBean;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.HBox;

// Classe TableCell personalizzata per aggiungere due bottoni nella cella
public class SingleButtonTableCell extends TableCell<AllenamentoBean, String> {

    private final Button linkButton = new Button("Iscriviti");

    public SingleButtonTableCell() {

        linkButton.setOnAction(event -> {
            AllenamentoBean playlist = getTableView().getItems().get(getIndex());
            // Ottieni l'oggetto Clipboard
            Clipboard clipboard = Clipboard.getSystemClipboard();

            // Crea un oggetto ClipboardContent
            ClipboardContent content = new ClipboardContent();

            // Aggiungi il testo alla clipboard
            content.putString(playlist.getData());

            // Imposta il contenuto della clipboard
            clipboard.setContent(content);
        });

        linkButton.setStyle("-fx-background-color: #1DB954; -fx-text-fill: white; -fx-pref-height: 25px; -fx-pref-width: 75px; " +
                "-fx-min-width: -1; -fx-min-height: -1; -fx-background-radius: 20; -fx-border-radius: 20; -fx-font-size: 12px");
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
