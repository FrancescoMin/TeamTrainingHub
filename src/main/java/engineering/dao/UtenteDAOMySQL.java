package engineering.dao;


import engineering.altro.Connessione;
import engineering.eccezioni.EccezioneGenerica;
import engineering.query.QueryRegistrazione;
import modelli.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


import static engineering.query.QueriesLogin.*;
import static engineering.query.QueryRegistrazione.*;

public class UtenteDAOMySQL implements UtenteDAO {

    public static String password = "password";
    public static String email1 = "email";
    public static String username = "username";

    public Utente recuperaUtenteDaEmail(String email) throws EccezioneGenerica {
        Connection conn;
        ResultSet rs = null, rsSquad = null, rsAll = null;
        Utente utente;

        //apriamo la connessione con il DB
        conn = Connessione.getInstance().getDBConnection();
        if (conn != null) {
            try {

                System.out.println("Inizio lavoro dell'utenteDAOMySQL");
                //invocazione del metodo per la ricerca dell'utente in funzione della variabile di ricerca
                rs = RecuperaUtenteRSPerEmail(conn, email);

                if (!rs.next()) {
                    throw new EccezioneGenerica("Utente non trovato");
                }

                System.out.println("Descrizione degli allenamenti:");
                //controllo se un utente ha degli allenamenti
                rsAll = RecuperaAllenamentiRSPerEmail(conn, email);

                List<Allenamento> allenamenti = new ArrayList<>();

                while (rsAll.next()){
                    System.out.println("data allenamento: " + rsAll.getString("data") + "  descrizione: " + rsAll.getString("descrizione") + " orario inizio: " + rsAll.getString("orarioInizio") + " orario fine: " + rsAll.getString("orarioFine"));

                    //metodo per l'aggiunta di un allenamento all'utente
                    allenamenti.add(new Allenamento(rsAll.getString("data"), rsAll.getString("orarioInizio"), rsAll.getString("orarioFine"), rsAll.getString("descrizione")));
                }

                rsSquad = RecuperaSquadreRSPerEmail(conn, email);

                Squadra squadra = new Squadra();

                System.out.println("Codici squadra:");
                while (rsSquad.next()) {
                    System.out.println(rsSquad.getString("codice"));

                    //creo una squadra senza lista di iscrizioni
                    squadra = new Squadra(rsSquad.getString("codice"), rsSquad.getString("allenatore"));

                    //aggiungo la lista di iscrizioni alla squadra
                    SquadraDAOMySQL squadraDAOMySQL = new SquadraDAOMySQL();
                    squadra.setRichiesteIngresso(squadraDAOMySQL.getRichiesteIscrizionePerSquadra(squadra));
                }

                System.out.println("Finito lavoro dell'utenteDAOMySQL");

                if (rs.getBoolean("allenatore")) {
                    System.out.println("Utente allenatore");
                    utente = new Allenatore(rs.getString(username), rs.getString(email1), rs.getString(password) , allenamenti, squadra );

                } else {
                    System.out.println("Utente non allenatore");
                    utente = new Giocatore(rs.getString(username), rs.getString(email1), rs.getString(password) , allenamenti , squadra );
                }
                return utente;

            }
            catch (SQLException e) {
                throw new EccezioneGenerica(e.getMessage());
            }

            finally
            {
                try
                {
                    if (rs!=null) {rs.close();}
                    if (rsSquad!=null) {rsSquad.close();}
                    if(rsAll!=null) {rsAll.close();}
                }
                catch (SQLException e) {System.out.println("Seconda eccezione rilevata");}
            }

        }
        throw new EccezioneGenerica("Connessione con il DB non riuscita");
    }

    public Boolean esisteUtenteDaLogin(Login login) throws EccezioneGenerica {
        try
        {
            return esisteUtenteDaEmail(login.getEmail());
        }
        catch (EccezioneGenerica e)
        {
            throw new EccezioneGenerica(e.getMessage());
        }
    }
    public Boolean esisteUtenteDaEmail(String email) throws EccezioneGenerica{
        Connection conn;
        ResultSet rs = null;

        //apriamo la connessione con il DB
        conn = Connessione.getInstance().getDBConnection();
        if (conn != null) {
            try
            {
                //invocazione del metodo per la ricerca dell'utente in funzione della email
                rs = RecuperaUtenteRSPerEmail(conn, email);
                return rs.next();

            }
            catch(Exception e)
            {
                throw new EccezioneGenerica(e.getMessage());
            }
            finally {
                try
                {
                    if (rs!=null) {rs.close();}
                }
                catch (SQLException e) {System.out.println("Seconda eccezione rilevata");}
            }
        }
        return false;
    }

    public void inserisciUtenteDaRegistrazione(Registrazione registrazione) {
        Connection conn = null;
        ResultSet rs = null;
        int result = 0;

        conn = Connessione.getInstance().getDBConnection();

        if(conn!=null)
        {
            try
            {
                result = QueryRegistrazione.InserisciUtenteQuery(conn, registrazione);
                if (result > 0) {
                    System.out.println("A new user was inserted successfully!");
                }

            } catch (EccezioneGenerica e)
            {throw new EccezioneGenerica(e.getMessage());}

            finally {
                try
                {
                    if (rs!=null) {rs.close();}
                }
                catch (SQLException e) {System.out.println("Seconda eccezione rilevata");}
            }
        }
    }

    public void modificaSquadraPerUtente(Squadra squadra, Utente utente){
        Connection conn;
        int righeModificate = 0;

        //apriamo la connessione con il DB
        conn = Connessione.getInstance().getDBConnection();
        if (conn != null) {
            try
            {
                //invocazione del metodo per la ricerca dell'utente in funzione della email
                righeModificate = modificaSquadraPerEmail(conn, squadra.getNome(), utente.getEmail());

                //controllo di aver modificato 1 riga nel DB prima di completare il codice
                if (righeModificate==0)
                {
                    throw new EccezioneGenerica("Errore nella modifica della squadra");
                }
            }
            catch(Exception e)
            {
                throw new EccezioneGenerica(e.getMessage());
            }
        }
        throw new EccezioneGenerica("Connessione con il DB non riuscita nella modifica della squadra");
    }

    public Utente recuperaUtenteDaLogin(Login login) throws  EccezioneGenerica {
        try {
            return recuperaUtenteDaEmail(login.getEmail());
        }
        catch (EccezioneGenerica e)
        {
            throw new EccezioneGenerica(e.getMessage());
        }
    }


    public void handleDAOException(Exception e) throws EccezioneGenerica {
        throw new EccezioneGenerica(e.getMessage());
    }
}