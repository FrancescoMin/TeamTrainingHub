package engineering.pattern.observer;

import java.util.ArrayList;
import java.util.List;

public abstract class Subject {

    // Lista di Observer che si iscrivono per ricevere la notifica
    private final List<Observer> observers;

    // Costruttore
    protected Subject() {
        observers = new ArrayList<>();
    }

    // Aggiunge un observer alla lista dei subscribers
    public void attach(Observer newObserver) {
        observers.add(newObserver);
    }

    // Rimuove un observer dalla lista dei subscribers
    public void detach(Observer removeObserver) {
        observers.remove(removeObserver);
    }

    // Notifica tutti gli observer registrati chiamando il metodo update()
    protected void notifyObservers() {
        for (Observer observer : observers) {
            observer.update();
        }
    }
}

