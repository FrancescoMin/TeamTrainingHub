package viste.second;

import java.util.Scanner;

public abstract class GenericaCLI {
    protected String pagina="";
    protected String prossimaPagina="";
    protected Scanner scanner = new Scanner(System.in);
    public GenericaCLI() {
        //costruttore vuoto di default
    }
    protected void stampaPagina(){
        System.out.println("Benvenuto nella pagina " + pagina);
    }

    public void start() {}

    protected void spostamento(String nomeClasse){
        try {
            scanner.close();
            // Ottieni la classe a partire dal nome della classe (assicurati che il nome completo della classe sia passato)
            Class<?> clazz = Class.forName(nomeClasse);

            // Crea una nuova istanza della classe
            Object instance = clazz.getDeclaredConstructor().newInstance();

            // Invoca il metodo start() sull'istanza creata
            clazz.getMethod("start").invoke(instance);
        } catch (Exception e) {
            System.out.println("Errore durante l'inizializzazione della classe: " + e.getMessage());
        }
    }
}
