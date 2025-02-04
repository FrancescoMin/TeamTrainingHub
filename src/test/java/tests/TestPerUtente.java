package tests;

import engineering.dao.UtenteDAOJSON;
import modelli.*;
import org.junit.Assert;
import org.junit.Test;

/**
 * Studente: Simone Manisi
 * Matricola: 0307584*/

public class TestPerUtente {
    private final String testUser = "testUser";
    private final String email = "testUser@gmail.com";
    private final boolean allenatore = false;

    /**
     * Testo che l'inserimento di un utente avvenga con successo se tutti i valori sono validi (email e username generati random)
     */
    @Test
    public void testInserisciUtenteDaUtenteConDatiValidi() {
        UtenteDAOJSON utenteDAOJSON = new UtenteDAOJSON();
        int res = -1;

        String randomUsername = generateRandomUsername();
        String randomEmail = generateRandomEmail();
        boolean randomAllenatore = generateRandomAllenamento();

        Utente utenteValido = assegnaUtente(randomUsername, randomEmail, randomAllenatore);

        try {
            utenteDAOJSON.inserisciUtente(utenteValido);

            if (utenteDAOJSON.recuperaUtente(utenteValido) != null) {
                res = 1;
            }

        } catch (Exception e) {
            res = 0;
        }
        Assert.assertEquals(1, res);
    }

    /**
     * Testo che l'inserimento restituisca l'eccezione "EmailAlreadyInUseException" se provo a registrare un utente con una
     * email già in uso, in questo caso admin@gmail.com (Un supervisore già presente nel file system)
     */
    @Test
    public void testRecuperaUtenteDaEmailConUtenteEsistente() {
        UtenteDAOJSON utenteDAOJSON = new UtenteDAOJSON();
        int res;
        try {
            utenteDAOJSON.inserisciUtente(assegnaUtente(testUser, email, allenatore));
        } catch (Exception e) {
            e.fillInStackTrace(); // Ignoro
        }
        try {
            utenteDAOJSON.recuperaUtenteDaEmail(email);
            res = 1; // Se la registrazione riesce, il test fallisce

        } catch (Exception e) {
            res = 0; // Se si verifica un'eccezione diversa, il test fallisce
        }
        Assert.assertEquals(1, res);
    }

    /**
     * Testo che l'inserimento restituisca l'eccezione "UsernameAlreadyInUseException" se provo a registrare un utente con un
     * nome utente già in uso, in questo caso admin(Un supervisore già presente nel file system).
     * La mail invece viene generata random
     */
    @Test

    public void testEsisteUtenteDaEmailConUtenteEsistente() {
        UtenteDAOJSON utenteDAOJSON = new UtenteDAOJSON();
        int res;
        try {
            Utente utente;
            utente = assegnaUtente(testUser, email, allenatore);
            utenteDAOJSON.inserisciUtente(utente);
        } catch (Exception e) {
            e.fillInStackTrace(); // Ignoro
        }


        if(utenteDAOJSON.esisteUtenteDaEmail(email)){
            res = 1;
        }
        else{
            res = 0;
        }
        Assert.assertEquals(1, res);
    }

    private String generateRandomUsername() {
        return testUser + System.currentTimeMillis();
    }

    private String generateRandomEmail() {
        return "testEmail" + System.currentTimeMillis() + "@test.com";
    }

    private Utente assegnaUtente(String string, String string1, boolean aBoolean){
        if(aBoolean){return new Allenatore(string,string1, string);}
        else{return new Giocatore(string, string1, string);}
    }

    private boolean generateRandomAllenamento() {
        return Math.random() < 0.5;
    }
}

