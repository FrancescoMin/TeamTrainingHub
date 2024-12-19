package engineering.dao;


import engineering.altro.Connessione;
import engineering.eccezioni.EccezioneGenerica;
import modelli.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


import static engineering.query.QueriesLogin.*;
import static engineering.query.QueryRegistrazione.InserisciUtenteQuery;

public class UtenteDAOMySQL implements UtenteDAO {

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

                if(rs == null) throw new EccezioneGenerica("Utente non esistente, generato dal DAO");

                System.out.println("email: " + rs.getString("email") + "    username: " + rs.getString("username") + "  password: " + rs.getString("password"));


                System.out.println("Descrizione degli allenamenti:");
                //controllo se un utente ha degli allenamenti
                rsAll = RecuperaAllenamentiRSPerEmail(conn, email);

                List<Allenamento> allenamenti = new ArrayList<>();

                while (rsAll.next()){
                    System.out.println("data allenamento: " + rsAll.getString("data") + "   durata: " + rsAll.getInt("durata") + "  descrizione: " + rsAll.getString("descrizione"));

                    //metodo per l'aggiunta di un allenamento all'utente
                    allenamenti.add(new Allenamento(rsAll.getString("data"), rsAll.getInt("durata"), rsAll.getString("descrizione")));
                }

                rsSquad = RecuperaSquadreRSPerEmail(conn, email);

                if(rsSquad == null) throw new EccezioneGenerica("Squadre non esistenti");

                Squadra squadra = null;

                System.out.println("Codici squadra:");
                while (rsSquad.next()) {
                    System.out.println(rsSquad.getString("codice"));

                    //metodo per l'aggiunta di una squadra all'utente
                    squadra = new Squadra(rsSquad.getString("codice"));
                }

                System.out.println("Finito lavoro dell'utenteDAOMySQL");

                if (rs.getBoolean("allenatore")) {
                    System.out.println("Utente allenatore");
                    utente = new Allenatore(rs.getString("username"), rs.getString("email"), rs.getString("password") , allenamenti, squadra );
                    rs.close();
                    rsSquad.close();
                    rsAll.close();
                    return utente;
                } else {
                    System.out.println("Utente non allenatore");
                    utente = new Giocatore(rs.getString("username"), rs.getString("email"), rs.getString("password") , allenamenti , squadra );
                    return utente;
                }

            } catch (SQLException e) {throw new EccezioneGenerica(e.getMessage());}

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
        Statement stmt = null;
        Connection conn;
        ResultSet rs = null;

        //apriamo la connessione con il DB
        conn = Connessione.getInstance().getDBConnection();
        if (conn != null) {
            try
            {
                //invocazione del metodo per la ricerca dell'utente in funzione della email
                rs = RecuperaUtenteRSPerEmail(conn, email);

                System.out.println("Creazione dell'utente");

                //restituisco vero solamente se l'utente esiste e il result set è diverso da null
                return rs != null;
            }
            catch(EccezioneGenerica e)
            {
                throw new EccezioneGenerica(e.getMessage());
            }
            finally {
                try {
                    if (conn != null) conn.close();
                } catch (SQLException e) {
                    System.out.println("Seconda eccezione rilevata");
                }
            }
        }
        throw new EccezioneGenerica("Connessione con il DB non riuscita");
    }

    public void inserisciUtenteDaRegistrazione(Registrazione registrazione) {
        Statement stmt = null;
        Connection conn = null;
        ResultSet rs = null;
        int result = 0;

        conn = Connessione.getInstance().getDBConnection();

        if(conn!=null)
        {
            try
            {
                result = InserisciUtenteQuery(conn, registrazione);
                if (result > 0) {
                    System.out.println("A new user was inserted successfully!");
                }

            } catch (EccezioneGenerica e)
            {throw new EccezioneGenerica(e.getMessage());}

            finally {
                try {if(conn!=null) conn.close();}
                catch (SQLException e) {throw new EccezioneGenerica("Errore nella chiusura della connessione con il database");}
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
            finally {
                try {
                    if (conn != null) conn.close();
                } catch (SQLException e) {
                    System.out.println("Seconda eccezione rilevata");
                }
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


