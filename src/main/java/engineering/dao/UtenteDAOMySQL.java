package engineering.dao;


import engineering.altro.Connessione;
import engineering.eccezioni.EccezionePasswordErrata;
import engineering.eccezioni.EccezioneSquadraInvalida;
import engineering.eccezioni.EccezioneUtenteInvalido;
import engineering.query.QueryRegistrazione;
import modelli.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


import static engineering.query.QueriesLogin.*;

public class UtenteDAOMySQL implements UtenteDAO {

    public static final String PASSWORD = "password";
    public static final String EMAIL = "email";
    public static final String USERNAME = "username";

    public void aggiornaUtente(Utente utente) throws EccezioneUtenteInvalido {
        Connection conn = null;
        //apriamo la connessione con il DB
        conn = Connessione.getInstance().getDBConnection();
        if (conn != null) {
            try {
                //l'aggiornamento dell'utente avverrà sempre controllando se l'utente ha una squadra non di default
                if (!utente.getSquadra().getNome().isEmpty()) {

                    //se l'utente è iscritto a una squadra, controllo che la squadra esisti e non sia quella di default
                    SquadraDAOMySQL squadraDAOMySQL = new SquadraDAOMySQL();
                    Squadra appoggio = squadraDAOMySQL.getSquadraDaUtente(utente);

                    //se la squadra non è quella di default, iscrivo l'utente alla squadra
                    if (!appoggio.getNome().isEmpty()) {
                        squadraDAOMySQL.iscrizioneUtenteASquadra(utente, utente.getSquadra());
                    }

                    //da implementare l'aggiornamento degli allenamenti
                }
            } catch (EccezioneSquadraInvalida e) {
                throw new EccezioneUtenteInvalido(e.getMessage());
            }
        } else {
            throw new EccezioneUtenteInvalido("Connessione con il DB non riuscita mentre si aggiornava l'utente " + utente.getEmail());
        }
    }

    public Utente recuperaUtenteDaEmail(String email) throws EccezioneUtenteInvalido, EccezioneSquadraInvalida {
        //poi recupero il resto dell'utente
        Connection conn=null;
        Utente utente;

        //apriamo la connessione con il DB
        conn = Connessione.getInstance().getDBConnection();
        if (conn != null) {
            try(ResultSet rs = recuperautentersperemail(conn, email)) {

                //invocazione del metodo per la ricerca dell'utente in funzione della variabile di ricerca


                if (!rs.next()) {
                    throw new EccezioneUtenteInvalido("Utente non trovato");
                }

                //controllo se un utente ha degli allenamenti

                List<Allenamento> allenamenti = new ArrayList<>();

                AllenamentoDAOMySQL allenamentoDAOMySQL = new AllenamentoDAOMySQL();
                allenamenti = allenamentoDAOMySQL.recuperaAllenamentiPerEmail(email);

                Squadra squadra = new Squadra();

                SquadraDAOMySQL squadraDAOMySQL = new SquadraDAOMySQL();
                squadra= squadraDAOMySQL.getSquadraDaEmail(email);

                if (rs.getBoolean("allenatore")) {
                    utente = new Allenatore(rs.getString(USERNAME), rs.getString(EMAIL), rs.getString(PASSWORD) , allenamenti, squadra );

                } else {
                    utente = new Giocatore(rs.getString(USERNAME), rs.getString(EMAIL), rs.getString(PASSWORD) , allenamenti , squadra );
                }
                return utente;
            }
            catch (SQLException e) {
                throw new EccezioneUtenteInvalido("Errore di recupero dell'utente");
            }
            catch(EccezioneSquadraInvalida e)
            {
                throw new EccezioneSquadraInvalida(e.getMessage());

            }
            catch (EccezioneUtenteInvalido e) {
                throw new EccezioneUtenteInvalido(e.getMessage());
            }
        }
        throw new EccezioneUtenteInvalido("Connessione con il DB non riuscita");
    }
    public Utente recuperaUtenteDaLogin(Login login) throws EccezioneUtenteInvalido, EccezioneSquadraInvalida, EccezionePasswordErrata {
        try {
            Utente utente = recuperaUtenteDaEmail(login.getEmail());
            if(utente.getPassword().equals(login.getPassword())) {
                return utente;
            }
            else {
                throw new EccezionePasswordErrata("Credenziali errate, assicurati di aver inserito correttamente email e password");
            }
        }
        catch (EccezioneUtenteInvalido e) {
            throw new EccezioneUtenteInvalido(e.getMessage());
        }
        catch(EccezioneSquadraInvalida e) {
            throw new EccezioneSquadraInvalida(e.getMessage());
        }
        catch (EccezionePasswordErrata e) {
            throw new EccezionePasswordErrata(e.getMessage());
        }
    }

    public Boolean esisteUtenteDaEmail(String email) throws EccezioneUtenteInvalido{
        try{
            return recuperaUtenteDaEmail(email) != null;
        }
        catch(Exception e) {
            throw new EccezioneUtenteInvalido(e.getMessage());
        }
    }

    public void inserisciUtenteDaRegistrazione(Registrazione registrazione) throws EccezioneUtenteInvalido {
        int result = 0;

        Connection conn = null;
        conn = Connessione.getInstance().getDBConnection();

        if(conn!=null)
        {
            try {
                result = QueryRegistrazione.inserisciUtenteQuery(conn, registrazione);
                if (result < 1) {
                    throw new EccezioneUtenteInvalido("Errore nell'inserimento dell'utente dalla registrazione");
                }
            }
            catch (EccezioneUtenteInvalido e)
            {
                throw new EccezioneUtenteInvalido(e.getMessage());
            }

        }
    }

}