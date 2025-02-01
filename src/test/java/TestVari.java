import modelli.Allenamento;
import modelli.Giocatore;
import org.junit.Assert;
import org.junit.Test;
import java.util.List;

public class TestVari {

    private final String USERNAME = "testUser";
    private final String EMAIL = "testUser@gmail.com";

    /**
     * Test che verifica il messaggio di benvenuto per un giocatore
     */
    @Test
    public void testMessaggioBenvenutoGiocatore() {
        Giocatore giocatore = new Giocatore(USERNAME, EMAIL, USERNAME);
        String messaggioBenvenuto = giocatore.getMessaggioBenvenuto();

        Assert.assertTrue("Il messaggio di benvenuto dovrebbe contenere il nome utente.",
                messaggioBenvenuto.contains(USERNAME));
    }

    /**
     * Test che verifica che la lista degli allenamenti sia vuota per un nuovo utente
     */
    @Test
    public void testRecuperoListaAllenamentiVuota() {
        Giocatore giocatore = new Giocatore(USERNAME, EMAIL, USERNAME);
        List<Allenamento> allenamenti = giocatore.getAllenamenti();

        Assert.assertTrue("La lista degli allenamenti dovrebbe essere vuota per un nuovo utente.",
                allenamenti.isEmpty());
    }
}
