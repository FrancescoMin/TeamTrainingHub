package engineering.pattern.observer;

import modelli.Allenamento;

import java.util.ArrayList;
import java.util.List;

public class AllenamentiPubblicati implements Subject {
    private List<Observer> observers;
    private List<Allenamento> allenamenti;

    public AllenamentiPubblicati() {
        this.observers = new ArrayList<>();
        this.allenamenti = new ArrayList<>();
    }

    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update();
        }
    }

    public void aggiungiAllenamento(Allenamento allenamento) {
        allenamenti.add(allenamento);
        notifyObservers();
    }

    public void rimuoviAllenamento(Allenamento allenamento) {
        allenamenti.remove(allenamento);
        notifyObservers();
    }

    public List<Allenamento> getAllenamenti() {
        return allenamenti;
    }
}