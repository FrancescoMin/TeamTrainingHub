package tests;

import engineering.dao.SquadraDAOJSON;
import engineering.dao.UtenteDAOJSON;
import modelli.*;
import org.junit.Assert;
import org.junit.Test;

/**
 * Studente: Francesco Minotti
*/


public class TestPerSquadra {

    private final String testUser = "testUser";
    private final String email = "testUser@gmail.com";
    private final String squadraTest = "squadraTest";

    /**
     * Test che verifica la corretta creazione di una nuova squadra
     */
    @Test
    public void testCreaSquadraPerAllenatoreConSquadraValida() {
        int res=-1;
        Allenatore allenatore = new Allenatore(generateRandomUsername(), generateRandomEmail(), generateRandomEmail());
        Squadra squadra = new Squadra(generateRandomTeam(), allenatore.getEmail());

        SquadraDAOJSON squadraDAOJSON = new SquadraDAOJSON();
        UtenteDAOJSON utenteDAOJSON = new UtenteDAOJSON();
        utenteDAOJSON.inserisciUtente(allenatore);

        try{
            squadraDAOJSON.creaSquadraPerAllenatore(allenatore, squadra);

            if( (!squadraDAOJSON.getSquadraDaNome(squadra.getNome()).getNome().isEmpty()) ){
                res = 1;
            }

        }catch (Exception e){
            res = 0;
        }
        Assert.assertEquals(1, res);
    }

    /**
     * Test che verifica che la lista degli allenamenti sia vuota per un nuovo utente
     */
    @Test
    public void testGetSquadraDaNomeConSquadraEsistente() {
        int res = -1;
        Allenatore allenatore = new Allenatore(generateRandomUsername(), generateRandomEmail(), generateRandomEmail());
        Squadra squadra = new Squadra(squadraTest, allenatore.getEmail());

        UtenteDAOJSON utenteDAOJSON = new UtenteDAOJSON();
        utenteDAOJSON.inserisciUtente(allenatore);

        SquadraDAOJSON squadraDAOJSON = new SquadraDAOJSON();
        try {
            squadraDAOJSON.creaSquadraPerAllenatore(allenatore, squadra);
        } catch (Exception e) {
            e.fillInStackTrace(); // Ignoro
        }
        try {
            if (squadraDAOJSON.getSquadraDaNome(squadraTest) != null) {
                res = 1;
            }
        } catch (Exception e) {
            res = 0;
        }
        Assert.assertEquals(1, res);
    }

    @Test
    public void testIscrizioneUtenteASquadraConSquadraValida() {
    int res=-1;

    Allenatore allenatore = new Allenatore(generateRandomUsername(), generateRandomEmail(), generateRandomEmail());
    Squadra squadra = new Squadra(generateRandomTeam(), allenatore.getEmail());
    Giocatore giocatore = new Giocatore(testUser, email, email);

    UtenteDAOJSON utenteDAOJSON = new UtenteDAOJSON();
    SquadraDAOJSON squadraDAOJSON = new SquadraDAOJSON();

    try {
        utenteDAOJSON.inserisciUtente(allenatore);
        utenteDAOJSON.inserisciUtente(giocatore);
}
    catch (Exception e) {
        e.fillInStackTrace(); // Ignoro
    }
    try {
        squadraDAOJSON.creaSquadraPerAllenatore(allenatore, squadra);
        squadraDAOJSON.iscrizioneUtenteASquadra(giocatore, squadra);
    }
    catch (Exception e) {
        e.fillInStackTrace(); // Ignoro
    }
    try {
        Utente utente = utenteDAOJSON.recuperaUtenteDaEmail(giocatore.getEmail());
        Squadra squadraUtente = utente.getSquadra();
        if(squadraUtente.getNome().equals(squadra.getNome())){
            res = 1;
        }
    }
    catch (Exception e) {
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

    private String generateRandomTeam() {
        return squadraTest + System.currentTimeMillis();
    }

}
