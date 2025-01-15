package engineering.dao;


import engineering.altro.Connessione;
import engineering.eccezioni.EccezioneGenerica;
import engineering.query.QueryRegistrazione;
import modelli.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


import static engineering.query.QueriesLogin.*;

public class UtenteDAOMySQL implements UtenteDAO {

    public static String password = "password";
    public static String email1 = "email";
    public static String username = "username";

    public Utente recuperaUtenteDaEmail(String email) throws EccezioneGenerica {

        List<Allenamento> allenamenti = new ArrayList<>();
        Squadra squadra = new Squadra();

        //recupero prima allenamenti e squadra
        try {


        }
        catch (EccezioneGenerica e)
        {
            throw new EccezioneGenerica(e.getMessage());
        }

        //poi recupero il resto dell'utente
        Connection conn=null;
        ResultSet rs = null;
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

                AllenamentoDAOMySQL allenamentoDAOMySQL = new AllenamentoDAOMySQL();
                allenamenti = allenamentoDAOMySQL.recuperaAllenamentiPerEmail(email);

                SquadraDAOMySQL squadraDAOMySQL = new SquadraDAOMySQL();
                squadra= squadraDAOMySQL.getSquadraDaEmail(email);
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
                }
                catch (SQLException e) {System.out.println("Errore nella liberazione dei result set in recuperaUtenteDaEmail");}
            }

        }
        throw new EccezioneGenerica("Connessione con il DB non riuscita");
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
                catch (SQLException e) {System.out.println("Errore di liberazione dei result set in EsisteUtenteDaEmail");}
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
            try {
                result = QueryRegistrazione.InserisciUtenteQuery(conn, registrazione);
                if (result > 0) {
                    System.out.println("A new user was inserted successfully!");
                }

            }
            catch (EccezioneGenerica e) {
                throw new EccezioneGenerica(e.getMessage());
            }

        }
    }

    public void handleDAOException(Exception e) throws EccezioneGenerica {
        throw new EccezioneGenerica(e.getMessage());
    }
}