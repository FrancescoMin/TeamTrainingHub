package engineering.pattern.observer;

import modelli.Allenamento;
import java.util.ArrayList;
import java.util.List;

public class CollezioneAllenamenti extends Subject {

    private static CollezioneAllenamenti collezioneAllenamenti = null;
    private final List<Allenamento> allenamenti = new ArrayList<>();

    private CollezioneAllenamenti() {
        super();
    }

    public static synchronized CollezioneAllenamenti getInstance() {
        if (collezioneAllenamenti == null) {
            collezioneAllenamenti = new CollezioneAllenamenti();
        }
        return collezioneAllenamenti;
    }

    public void addAllenamento(Allenamento allenamento) {
        allenamenti.add(allenamento);
        System.out.println("[Subject] Aggiunto allenamento: " + allenamento.getData() + ". Notifico gli osservatori...");
        notifyObservers();
    }

    public void removeAllenamento(Allenamento allenamento) {
        allenamenti.remove(allenamento);
        System.out.println("[Subject] Rimosso allenamento: " + allenamento.getData() + ". Notifico gli osservatori...");
        notifyObservers();
    }

    public List<Allenamento> getAllenamenti() {
        return new ArrayList<>(allenamenti);
    }

    public void popolaTabella(List<Allenamento> nuoviAllenamenti) {
        this.allenamenti.clear();
        this.allenamenti.addAll(nuoviAllenamenti);
        System.out.println("[Subject] Collezione popolata con " + allenamenti.size() + " allenamenti. Notifico gli osservatori...");
        notifyObservers();
    }
}