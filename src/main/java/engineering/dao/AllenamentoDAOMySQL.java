package engineering.dao;

import engineering.altro.Connessione;
import engineering.eccezioni.EccezioneAllenamentoInvalido;
import engineering.query.QueryAllenamento;
import modelli.Allenamento;
import modelli.Utente;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import static engineering.query.QueriesLogin.RecuperaAllenamentiRSPerEmail;

public class AllenamentoDAOMySQL implements AllenamentoDAO {

    private static final String DESC = "descrizione";

    public void inserisciAllenamentoAdUtente(Allenamento allenamento, Utente utente) throws EccezioneAllenamentoInvalido{

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
                    throw new EccezioneAllenamentoInvalido("Errore nella creazione dell'allenamento per l'utente " + utente.getEmail());
                }
            } catch (EccezioneAllenamentoInvalido e) {
                throw new EccezioneAllenamentoInvalido(e.getMessage());
            }
        }

    }

    public List<Allenamento> recuperaAllenamentiPerEmail(String email) throws EccezioneAllenamentoInvalido{

        //apriamo la connessione con il DB
        Connection conn=null;
        conn = Connessione.getInstance().getDBConnection();

        if (conn != null) {
            try (ResultSet rsAll = RecuperaAllenamentiRSPerEmail(conn, email);){
                //invocazione del metodo per la ricerca dell'utente in funzione della email

                List<Allenamento> allenamenti = new ArrayList<>();
                while (rsAll.next()){
                    allenamenti.add(new Allenamento(rsAll.getString("data"), rsAll.getString("orarioInizio"), rsAll.getString("orarioFine"), rsAll.getString(DESC)));
                }
                return allenamenti;

            } catch (Exception e) {
                throw new EccezioneAllenamentoInvalido(e.getMessage());
            }
        }
        throw new EccezioneAllenamentoInvalido("Connessione con il DB per il recupero degli allenamenti non riuscita");
    }

    public List<Allenamento> getAllenamentiPerEmail(String email) throws EccezioneAllenamentoInvalido {
        Connection conn=null;
        List<Allenamento> allenamenti = new ArrayList<>();
        //apriamo la connessione con il DB
        conn = Connessione.getInstance().getDBConnection();
        if (conn != null) {
            try(ResultSet rsAll = RecuperaAllenamentiRSPerEmail(conn, email))
            {
                //invocazione del metodo per la ricerca dell'utente in funzione della email
                while (rsAll.next()){
                    System.out.println("data allenamento: " + rsAll.getString("data") + "   durata: " + rsAll.getInt("durata") + "  descrizione: " + rsAll.getString(DESC));

                    //metodo per l'aggiunta di un allenamento all'utente
                    allenamenti.add(new Allenamento(rsAll.getString("data"), rsAll.getString("orarioInizio"), rsAll.getString("orarioFine"), rsAll.getString(DESC)));
                }
                return allenamenti;

            } catch (Exception e) {
                throw new EccezioneAllenamentoInvalido(e.getMessage());
            }
        }
        throw new EccezioneAllenamentoInvalido("Connessione con il DB non riuscita nel recupero degli allenamenti per l'utente " + email);
    }

    public List<Allenamento> getAllenamentiPerUtente(Utente utente) throws EccezioneAllenamentoInvalido {
        try{
            return recuperaAllenamentiPerEmail(utente.getEmail());
        } catch (EccezioneAllenamentoInvalido e) {
            throw new EccezioneAllenamentoInvalido(e.getMessage());
        }
    }
}