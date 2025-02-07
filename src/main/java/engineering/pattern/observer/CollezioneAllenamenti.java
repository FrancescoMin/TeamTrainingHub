package engineering.pattern.observer;

import engineering.bean.AllenamentoBean;
import modelli.Allenamento;

import java.util.ArrayList;
import java.util.List;

public class CollezioneAllenamenti extends Subject {

    private static CollezioneAllenamenti collezioneAllenamenti = null;

    private final List<Allenamento> allenamenti = new ArrayList<>();

    public static CollezioneAllenamenti getInstance() { //Pattern Memoria
        if (collezioneAllenamenti == null) {
            collezioneAllenamenti = new CollezioneAllenamenti();
        }
        return collezioneAllenamenti;
    }

    public void addAllenamento(Allenamento allenamento) {
        allenamenti.add(allenamento);
        notifyObservers(); // Notifica gli osservatori
    }

    public void removeAllenamento(Allenamento allenamento) {
        allenamenti.remove(allenamento);
        notifyObservers(); // Notifica gli osservatori
    }

    public List<Allenamento> getAllenamenti() {return allenamenti;}

    public void popolaTabella(List<Allenamento> allenamenti) {
        this.allenamenti.clear();
        this.allenamenti.addAll(allenamenti);

        notifyObservers(); // Notifica gli osservatori
    }
}