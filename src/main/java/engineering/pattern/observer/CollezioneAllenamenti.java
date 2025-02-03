package engineering.pattern.observer;

import engineering.bean.AllenamentoBean;

import java.util.ArrayList;
import java.util.List;

public class CollezioneAllenamenti extends Subject {
    private final List<AllenamentoBean> allenamenti = new ArrayList<>();

    public void addAllenamento(AllenamentoBean allenamento) {
        allenamenti.add(allenamento);
        notifyObservers(); // Notifica gli osservatori
    }

    public void removeAllenamento(AllenamentoBean allenamento) {
        allenamenti.remove(allenamento);
        notifyObservers(); // Notifica gli osservatori
    }

    public List<AllenamentoBean> getAllenamenti() {
        return allenamenti;
    }

    public void popolaTabella(List<AllenamentoBean> allenamenti) {
        this.allenamenti.clear();
        this.allenamenti.addAll(allenamenti);
        notifyObservers(); // Notifica gli osservatori
    }
}