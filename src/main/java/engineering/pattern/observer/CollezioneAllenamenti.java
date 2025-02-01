package engineering.pattern.observer;

import engineering.bean.AllenamentoBean;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class CollezioneAllenamenti extends Subject {

    private ObservableList<AllenamentoBean> allenamenti;

    private static CollezioneAllenamenti instance;

    // Singleton: restituisce l'unica istanza della collezione
    public static CollezioneAllenamenti getInstance() {
        if (instance == null) {
            instance = new CollezioneAllenamenti();
        }
        return instance;
    }

    // Costruttore privato per impedire istanze multiple
    private CollezioneAllenamenti() {
        allenamenti = FXCollections.observableArrayList();
    }

    // Aggiungi un allenamento alla lista
    public void aggiungiAllenamento(AllenamentoBean allenamento) {
        allenamenti.add(allenamento);
        notifyObservers();  // Notifica gli observer
    }

    // Rimuovi un allenamento dalla lista
    public void rimuoviAllenamento(AllenamentoBean allenamento) {
        allenamenti.remove(allenamento);
        notifyObservers();  // Notifica gli observer
    }

    // Restituisce la lista degli allenamenti
    public ObservableList<AllenamentoBean> getState() {
        return allenamenti;
    }
}
