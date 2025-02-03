package viste.first.utils;

import engineering.bean.AllenamentoBean;
import engineering.pattern.observer.CollezioneAllenamenti;
import engineering.pattern.observer.Observer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;

public class IscrizioneAllenamentiTabella implements Observer {
    private TableView<AllenamentoBean> tableView;
    private ObservableList<AllenamentoBean> allenamentiList;
    private CollezioneAllenamenti collezioneAllenamenti;

    public IscrizioneAllenamentiTabella(TableView<AllenamentoBean> tableView, CollezioneAllenamenti collezioneAllenamenti) {
        this.tableView = tableView;
        this.collezioneAllenamenti = collezioneAllenamenti;
        this.allenamentiList = FXCollections.observableArrayList();
        this.tableView.setItems(allenamentiList);
        this.collezioneAllenamenti.attach(this); // Registrati come osservatore
        popolaTabella(); // Popola la tabella inizialmente
    }

    @Override
    public void update() {
        popolaTabella(); // Aggiorna la tabella quando viene notificato
        // Fai un refresh della TableView per essere sicuro che i cambiamenti siano visibili
    }

    public void popolaTabella() {
        allenamentiList.clear();
        allenamentiList.addAll(collezioneAllenamenti.getAllenamenti());
    }

    // Aggiungi un allenamento alla collezione
    public void aggiungiAllenamento(AllenamentoBean allenamento) {
        collezioneAllenamenti.addAllenamento(allenamento);
    }

    // Rimuovi un allenamento dalla collezione
    public void rimuoviAllenamento(AllenamentoBean allenamento) {
        collezioneAllenamenti.removeAllenamento(allenamento);
    }
}