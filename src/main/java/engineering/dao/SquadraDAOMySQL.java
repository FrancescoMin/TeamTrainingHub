package engineering.dao;

import engineering.altro.Connessione;
import engineering.eccezioni.EccezioneSquadraInvalida;
import engineering.eccezioni.EccezioneUtenteInvalido;
import engineering.query.QuerySquadra;
import modelli.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class SquadraDAOMySQL implements SquadraDAO {

    public SquadraDAOMySQL() {
        //costruttore vuoto di default
    }

    public Squadra getSquadraDaNome(String nomeSquadra) throws EccezioneSquadraInvalida {

        Connection conn;
        Squadra squadra;

        //apriamo la connessione con il DB
        conn = Connessione.getInstance().getDBConnection();
        if (conn != null) {
            try(ResultSet rs = QuerySquadra.getSquadraRSDaNome(conn,nomeSquadra)) {
                //invocazione del metodo per la ricerca della squadra in funzione del nome

                if(rs.next()) {
                    squadra = new Squadra(rs.getString("codice"), rs.getString("allenatore"));
                    System.out.println("Squadra trovata da getSquadraDaNome: " + squadra.getNome());
                    return squadra;
                }
                return new Squadra();
            }
            catch(EccezioneSquadraInvalida | SQLException e) {
                throw new EccezioneSquadraInvalida("Problema con la sessione nella ricerca della squadra");
            }
        }
        else {
            throw new EccezioneSquadraInvalida("Errore di connessione con il DB nella ricerca della squadra per il nome " + nomeSquadra);
        }
    }

    public Squadra getSquadraDaEmail(String email) throws EccezioneSquadraInvalida{
        Connection conn;
        Squadra squadra;

        //apriamo la connessione con il DB
        conn = Connessione.getInstance().getDBConnection();
        if (conn != null) {
            try(ResultSet rs = QuerySquadra.RecuperaSquadreRSPerEmail(conn, email)) {
                //invocazione del metodo per la ricerca della squadra in funzione del nome
                ;
                if(rs.next()) {
                    squadra = new Squadra(rs.getString("codice"), rs.getString("allenatore"));
                    System.out.println("Squadra trovata da getSquadraDaEmail: " + squadra.getNome());

                    squadra.setRichiesteIngresso(getRichiesteIscrizionePerSquadra(squadra));
                    return squadra;
                }
                return new Squadra();
            }
            catch(SQLException e) {
                throw new EccezioneSquadraInvalida("Problema con la sessione nella ricerca della squadra per l'utente " + email);
            }
        }
        else {
            throw new EccezioneSquadraInvalida("Connessione con il DB non riuscita nella ricerca della squadra per l'utente " + email);
        }
    }

    public Squadra getSquadraDaUtente(Utente utente) throws EccezioneSquadraInvalida{
        try {
            return getSquadraDaEmail(utente.getEmail());
        }
        catch (EccezioneSquadraInvalida e) {
            throw new EccezioneSquadraInvalida(e.getMessage());
        }
    }

    public List<Utente> getRichiesteIscrizionePerSquadra(Squadra squadra) throws EccezioneSquadraInvalida{
        Connection conn;
        List<Utente> utenti = new ArrayList<>();

        //apriamo la connessione con il DB
        conn = Connessione.getInstance().getDBConnection();
        if (conn != null) {
            try(ResultSet rs = QuerySquadra.getRichiesteIscrizioneRSPerSquadra(conn, squadra))
            {
                //invocazione del metodo per la ricerca delle richieste di iscrizione in funzione della squadra
                ;
                while(rs.next()) {
                    System.out.println("Richiesta di iscrizione per la squadra: " + squadra.getNome());
                    UtenteDAOMySQL utenteDAO = new UtenteDAOMySQL();
                    Utente utente = utenteDAO.recuperaUtenteDaEmail(rs.getString("utenti_email"));
                    utenti.add(utente);
                }
                return utenti;
            }
            catch(EccezioneUtenteInvalido e) {
                throw new EccezioneUtenteInvalido("Errore nel recupero degli utenti dalle richieste di iscrizione");
            }
            catch(SQLException e) {
                throw new EccezioneSquadraInvalida("Errore di recupero della richiesta di iscrizione");
            }
            catch (EccezioneSquadraInvalida e) {
                throw new EccezioneSquadraInvalida(e.getMessage());
            }
        }
        else {
            throw new EccezioneSquadraInvalida("Connessione con il DB non riuscita nella ricerca delle richieste di iscrizione");
        }
    }

    public void creaSquadraPerAllenatore(Utente utente, Squadra squadra) throws EccezioneSquadraInvalida {
        try{
            System.out.println("Creazione della squadra per l'allenatore");

            creaSquadra(squadra, utente);
        }
        catch (EccezioneSquadraInvalida e) {
            throw new EccezioneSquadraInvalida(e.getMessage());
        }

    }
    public void creaSquadra(Squadra squadra, Utente utente) throws EccezioneSquadraInvalida {
        Connection conn;
        int righeModificate = 0;

        //apriamo la connessione con il DB

        conn = Connessione.getInstance().getDBConnection();
        if (conn != null) {
            try
            {
                //invocazione del metodo per la ricerca dell'utente in funzione della email
                righeModificate = QuerySquadra.createSquadra(conn, squadra.getNome(), utente.getEmail(), squadra.getAllenatore());

                System.out.println("Creazione della squadra con nome: " + squadra.getNome() + " e utente_email: " + utente.getEmail());

                //controllo di aver modificato 1 riga nel DB prima di completare il codice
                if (righeModificate==0)
                {
                    throw new EccezioneSquadraInvalida("Errore nella creazione della squadra");
                }
            }
            catch(EccezioneSquadraInvalida e) {
                throw new EccezioneSquadraInvalida(e.getMessage());
            }
        }
        else {
            throw new EccezioneSquadraInvalida("Connessione con il DB non riuscita nella creazione della squadra");
        }
    }

    public void aggiornaSquadra(Squadra squadra) throws EccezioneSquadraInvalida{
        Connection conn;

        conn = Connessione.getInstance().getDBConnection();
        if (conn != null) {
            try {
                for (Utente utente : squadra.getRichiesteIngresso()) {
                    ResultSet rs = null;
                    rs = QuerySquadra.getRichiestaIscrizioneRSPerEmail(conn, squadra ,utente.getEmail());
                    if(!rs.next()) {
                        aggiungiRichiestaASquadra(squadra, utente);
                    }
                    rs.close();
                }
            }
            catch (SQLException e) {
                throw new EccezioneSquadraInvalida("Errore nell'aggiornamento della squadra nell'aggiornamento della squadra");
            }
        }
        else {
            throw new EccezioneSquadraInvalida("Connessione con il DB non riuscita nell'aggiornamento della squadra");
        }
    }

    public void aggiungiRichiestaASquadra(Squadra squadra, Utente utente) throws EccezioneSquadraInvalida{

        Connection conn;
        int rs=0;

        //apriamo la connessione con il DB
        conn = Connessione.getInstance().getDBConnection();
        if (conn != null) {
            try {
                //invoco il metodo che si occupa di aggiornare il DB con la richiesta di iscrizione
                rs = QuerySquadra.inserisciRichiestaIscrizione(conn, squadra, utente.getEmail());

                //se l'operazione è andata a buon fine il valore di rs sarà diverso da 0
                if(rs==0) {
                    //in caso di errore lancio un eccezione per segnalare il problema
                    throw new EccezioneSquadraInvalida("Errore nell'inserimento della richiesta di iscrizione");
                }
                //se l'operazione è andata a buon fine non faccio nulla di particolare
            }
            catch(EccezioneSquadraInvalida e) {
                throw new EccezioneSquadraInvalida(e.getMessage());
            }
        }
        else {
            throw new EccezioneSquadraInvalida("Connessione con il DB non riuscita nella ricerca della squadra");
        }
    }

    public void IscrizioneUtenteASquadra(Utente utente, Squadra squadra) throws EccezioneSquadraInvalida{
        try {
            //Prima di compiere l'iscrizione devo controllare se la relazione esiste già
            if (!verificaEsistenzaSquadra(utente.getEmail())) {
                //L'iscrizione della squadra per un utente è simile alla creazione per un allenatore solamente che devo prima eliminare la richiesta di iscrizione
                eliminaRichiestaIscrizione(squadra, utente);
                creaSquadra(squadra, utente);
            }
        }
        catch (EccezioneSquadraInvalida e) {
            throw new EccezioneSquadraInvalida(e.getMessage());
        }
    }

    public void eliminaRichiestaIscrizione(Squadra squadra, Utente utente) throws EccezioneSquadraInvalida {
        Connection conn;
        int rs=0;

        //apriamo la connessione con il DB
        conn = Connessione.getInstance().getDBConnection();
        if (conn != null) {
            try {
                //invoco il metodo che si occupa di eliminare la richiesta di iscrizione
                rs=QuerySquadra.eliminaRichiestaIscrizione(conn, squadra, utente.getEmail());
                //se l'operazione è andata a buon fine il valore di rs sarà diverso da 0
                if(rs<0) {
                    //in caso di errore lancio un eccezione per segnalare il problema
                    throw new EccezioneSquadraInvalida("Errore nell'eliminazione della richiesta di iscrizione in eliminaRichiestaIscrizione in SquadraDAOMySQL");
                }
                //se l'operazione è andata a buon fine non faccio nulla
            }
            catch(EccezioneSquadraInvalida e) {
                throw new EccezioneSquadraInvalida(e.getMessage());
            }
        }
        else {
            throw new EccezioneSquadraInvalida("Connessione con il DB non riuscita nella ricerca della squadra per l'eliminazione della richiesta di iscrizione");
        }
    }

    public Boolean verificaEsistenzaSquadra(String nomeSquadra) throws EccezioneSquadraInvalida {
        try
        {
            //utilizzo il metodo già implementato per verificare l'esistenza della squadra
            return getSquadraDaNome(nomeSquadra).getNome().isEmpty();

        }
        catch (EccezioneSquadraInvalida e)
        {
            //se arrivo qui vuol dire che ho lanciato un'eccezione e la probabilmente la squadra non esiste
            throw new EccezioneSquadraInvalida(e.getMessage());
        }
    }

}
