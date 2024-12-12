package engineering.dao;


import engineering.altro.Connessione;
import engineering.eccezioni.EccezzioneGenerica;
import engineering.eccezioni.UtenteNonEsistenteEccezione;
import modelli.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


import static engineering.query.QueriesLogin.*;
import static engineering.query.QueryRegistrazione.InserisciUtenteQuery;

public class UtenteDAOMySQL implements UtenteDAO {

    public Utente recuperaUtenteDaEmail(String email) throws EccezzioneGenerica {
        Statement stmt = null, stmtSquad = null, stmtAll = null;
        Connection conn;
        ResultSet rs = null, rsSquad = null, rsAll = null;
        Utente utente;

        //apriamo la connessione con il DB
        conn = Connessione.getInstance().getDBConnection();
        if (conn != null) {
            try {

                //invocazione del metodo per la ricerca dell'utente in funzione della variabile di ricerca
                rs = RecuperaUtenteRSPerEmail(conn, email);

                if(rs == null) throw new EccezzioneGenerica("Utente non esistente, generato dal DAO");

                System.out.println("email: " + rs.getString("email"));
                System.out.println("username: " + rs.getString("username"));
                System.out.println("password: " + rs.getString("password"));



                System.out.println("Descrizione degli allenamenti e squadre per l'utente");
                //controllo se un utente ha degli allenamenti
                rsAll = RecuperaAllenamentiRSPerEmail(conn, email);

                if(rsAll == null) throw new EccezzioneGenerica("Allenamenti non esistenti");

                List<Allenamento> allenamenti = new ArrayList<>();

                while (rsAll.next()){
                    System.out.println("data allenamento: " + rsAll.getString("data"));
                    System.out.println("durata: " + rsAll.getInt("durata"));
                    System.out.println("descrizione: " + rsAll.getString("descrizione"));

                    //metodo per l'aggiunta di un allenamento all'utente
                    allenamenti.add(new Allenamento(rsAll.getString("data"), rsAll.getInt("durata"), rsAll.getString("descrizione")));
                }

                rsSquad = RecuperaSquadreRSPerEmail(conn, email);

                if(rsSquad == null) throw new EccezzioneGenerica("Squadre non esistenti");

                Squadra squadra = null;

                while (rsSquad.next()) {
                    System.out.println("codice squadra: " + rsSquad.getString("codice"));
                    System.out.println("utente_email: " + rsSquad.getString("utenti_email"));

                    //metodo per l'aggiunta di una squadra all'utente
                    squadra = new Squadra(rsSquad.getString("codice"));
                }

                if (rs.getBoolean("allenatore")) {
                    System.out.println("Utente allenatore");
                    utente = new Allenatore(rs.getString("username"), rs.getString("email"), rs.getString("password") , allenamenti, squadra );
                    return utente;
                } else {
                    System.out.println("Utente non allenatore");
                    utente = new Giocatore(rs.getString("username"), rs.getString("email"), rs.getString("password") , allenamenti , squadra );
                    return utente;
                }

            } catch (SQLException e) {throw new EccezzioneGenerica(e.getMessage());}

            finally
            {
                try {if (conn != null) conn.close();}
                catch (SQLException e) {System.out.println("Seconda eccezione rilevata");}
            }

        }
        throw new EccezzioneGenerica("Connessione con il DB non riuscita");
    }

    public Boolean esisteUtenteDaLogin(Login login) throws EccezzioneGenerica {
        try
        {
            return esisteUtenteDaEmail(login.getEmail());
        }
        catch (EccezzioneGenerica e)
        {
            throw new EccezzioneGenerica(e.getMessage());
        }
    }
    public Boolean esisteUtenteDaEmail(String email) throws EccezzioneGenerica{
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

                //restituisco vero solamente se l'utente esiste e il result set è diverso da null
                return rs != null;
            }
            catch(EccezzioneGenerica e)
            {
                throw new EccezzioneGenerica(e.getMessage());
            }
            finally {
                try {
                    if (conn != null) conn.close();
                } catch (SQLException e) {
                    System.out.println("Seconda eccezione rilevata");
                }
            }
        }
        throw new EccezzioneGenerica("Connessione con il DB non riuscita");
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

            } catch (EccezzioneGenerica e)
            {throw new EccezzioneGenerica(e.getMessage());}

            finally {
                try {if(conn!=null) conn.close();}
                catch (SQLException e) {throw new EccezzioneGenerica("Errore nella chiusura della connessione con il database");}
            }
        }
    }


    public Utente recuperaUtenteDaLogin(Login login) throws  EccezzioneGenerica {
        try {
            return recuperaUtenteDaEmail(login.getEmail());
        }
        catch (EccezzioneGenerica e)
        {
            throw new EccezzioneGenerica(e.getMessage());
        }
    }


    public void handleDAOException(Exception e) throws EccezzioneGenerica {
        throw new EccezzioneGenerica(e.getMessage());
    }
}


