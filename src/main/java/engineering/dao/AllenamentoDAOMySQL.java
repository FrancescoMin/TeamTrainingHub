package engineering.dao;

import engineering.altro.Connessione;
import engineering.eccezioni.EccezioneGenerica;
import engineering.query.QueryAllenamento;
import modelli.Allenamento;
import modelli.Utente;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import static engineering.query.QueriesLogin.RecuperaAllenamentiRSPerEmail;

public class AllenamentoDAOMySQL implements AllenamentoDAO {

    private static final String desc = "descrizione";

    public List<Allenamento> recuperaAllenamentiPerEmail(String email) {

        //apriamo la connessione con il DB
        Connection conn=null;
        conn = Connessione.getInstance().getDBConnection();

        if (conn != null) {
            try (ResultSet rsAll = RecuperaAllenamentiRSPerEmail(conn, email);){
                //invocazione del metodo per la ricerca dell'utente in funzione della email

                List<Allenamento> allenamenti = new ArrayList<>();
                while (rsAll.next()){
                    allenamenti.add(new Allenamento(rsAll.getString("data"), rsAll.getString("orarioInizio"), rsAll.getString("orarioFine"), rsAll.getString(desc)));
                }
                return allenamenti;

            } catch (Exception e) {
                throw new EccezioneGenerica(e.getMessage());
            }
        }
        throw new EccezioneGenerica("Connessione con il DB non riuscita");
    }

    public void inserisciAllenamentoAdUtente(Allenamento allenamento, Utente utente) {

        Connection conn;
        int righeModificate = 0;

        //apriamo la connessione con il DB

        conn = Connessione.getInstance().getDBConnection();
        if (conn != null) {
            try {
                //invocazione del metodo per la ricerca dell'utente in funzione della email
                righeModificate = QueryAllenamento.createAllenamento(conn, allenamento, utente.getEmail());

                //controllo di aver modificato 1 riga nel DB prima di completare il codice
                if (righeModificate == 0) {
                    throw new EccezioneGenerica("Errore nella creazione della squadra");
                }
            } catch (EccezioneGenerica e) {
                throw new EccezioneGenerica(e.getMessage());
            }
        }

    }

    public List<Allenamento> getAllenamentiPerEmail(String email) {
        UtenteDAOJSON utenteDAOJSON = new UtenteDAOJSON();
        return getAllenamentiPerUtente(utenteDAOJSON.recuperaUtenteDaEmail(email));
    }

    public List<Allenamento> getAllenamentiPerUtente(Utente utente) {
        ResultSet rsAll=null;
        Connection conn;
        List<Allenamento> allenamenti = new ArrayList<>();
        //apriamo la connessione con il DB
        conn = Connessione.getInstance().getDBConnection();
        if (conn != null) {
            try {
                //invocazione del metodo per la ricerca dell'utente in funzione della email
                rsAll = RecuperaAllenamentiRSPerEmail(conn, utente.getEmail());
                while (rsAll.next()){
                    System.out.println("data allenamento: " + rsAll.getString("data") + "   durata: " + rsAll.getInt("durata") + "  descrizione: " + rsAll.getString(desc));

                    //metodo per l'aggiunta di un allenamento all'utente
                    allenamenti.add(new Allenamento(rsAll.getString("data"), rsAll.getString("orarioInizio"), rsAll.getString("orarioFine"), rsAll.getString(desc)));
                }
                return allenamenti;

            } catch (Exception e) {
                throw new EccezioneGenerica(e.getMessage());
            }
            finally {
                try {
                    if(rsAll!=null) {rsAll.close();}
                }
                catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        }
        throw new EccezioneGenerica("Connessione con il DB non riuscita");
    }
}