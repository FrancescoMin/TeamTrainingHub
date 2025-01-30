import engineering.dao.UtenteDAOJSON;
import modelli.*;
import org.junit.Assert;
import org.junit.Test;

/**
 * Studente: Simone Manisi
 * Matricola: 0307584*/

public class TestPerUtente {
    private final String USERNAME = "testUser";
    private final String EMAIL = "testUser@gmail.com";
    private final Boolean ALLENATORE = false;

    /**
     * Testo che l'inserimento di un utente avvenga con successo se tutti i valori sono validi (email e username generati random)
     */
    @Test
    public void testInserisciUtenteDaUtenteConDatiValidi() {
        UtenteDAOJSON utenteDAOJSON = new UtenteDAOJSON();
        int res = -1;

        String randomUsername = generateRandomUsername();
        String randomEmail = generateRandomEmail();
        Boolean randomAllenatore = generateRandomAllenamento();

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
            utenteDAOJSON.inserisciUtente(assegnaUtente(USERNAME, EMAIL, ALLENATORE ));
        } catch (Exception e) {
            e.fillInStackTrace(); // Ignoro
        }
        try {
            utenteDAOJSON.recuperaUtenteDaEmail(EMAIL);
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
            utente = assegnaUtente(USERNAME, EMAIL, ALLENATORE);
            utenteDAOJSON.inserisciUtente(utente);
        } catch (Exception e) {
            e.fillInStackTrace(); // Ignoro
        }


        if(utenteDAOJSON.esisteUtenteDaEmail(EMAIL)){
            res = 1;
        }
        else{
            res = 0;
        }
        Assert.assertEquals(1, res);
    }

    private String generateRandomUsername() {
        return "testUsername" + System.currentTimeMillis();
    }

    private Boolean generateRandomAllenamento() {
        return Math.random() < 0.5;
    }

    private Utente assegnaUtente(String USERNAME, String EMAIL, Boolean ALLENATORE){
        if(ALLENATORE){return new Allenatore(USERNAME,EMAIL, USERNAME);}
        else{return new Giocatore(USERNAME, EMAIL, USERNAME);}
    }

    private String generateRandomEmail() {
        return "testEmail" + System.currentTimeMillis() + "@test.com";
    }
}

