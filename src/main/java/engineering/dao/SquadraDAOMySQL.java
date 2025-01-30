package engineering.dao;

import engineering.altro.Connessione;
import engineering.eccezioni.EccezioneGenerica;
import engineering.query.QuerySquadra;
import modelli.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


import static engineering.query.QueriesLogin.modificaSquadraPerEmail;

public class SquadraDAOMySQL implements SquadraDAO {

    public SquadraDAOMySQL() {
        //costruttore vuoto di default
    }

    public Squadra getSquadraDaNome(String nomeSquadra) throws EccezioneGenerica {

        Connection conn;
        Squadra squadra;
        ResultSet rs = null;

        //apriamo la connessione con il DB
        conn = Connessione.getInstance().getDBConnection();
        if (conn != null) {
            try {
                //invocazione del metodo per la ricerca della squadra in funzione del nome
                rs = QuerySquadra.getSquadraRSDaNome(conn,nomeSquadra);
                if(rs.next()) {
                    squadra = new Squadra(rs.getString("codice"), rs.getString("allenatore"));
                    System.out.println("Squadra trovata da getSquadraDaNome: " + squadra.getNome());
                    return squadra;
                }
                return new Squadra();
            }
            catch(Exception e) {
                throw new EccezioneGenerica(e.getMessage());
            }
        }
        else {
            throw new EccezioneGenerica("Connessione con il DB non riuscita nella ricerca della squadra");
        }
    }
    public Squadra getSquadraDaEmail(String email) throws EccezioneGenerica{
        Connection conn;
        Squadra squadra;
        ResultSet rs = null;

        //apriamo la connessione con il DB
        conn = Connessione.getInstance().getDBConnection();
        if (conn != null) {
            try {
                //invocazione del metodo per la ricerca della squadra in funzione del nome
                rs = QuerySquadra.RecuperaSquadreRSPerEmail(conn, email);
                if(rs.next()) {
                    squadra = new Squadra(rs.getString("codice"), rs.getString("allenatore"));
                    System.out.println("Squadra trovata da getSquadraDaEmail: " + squadra.getNome());

                    squadra.setRichiesteIngresso(getRichiesteIscrizionePerSquadra(squadra));
                    return squadra;
                }
                return new Squadra();
            }
            catch(Exception e) {
                throw new EccezioneGenerica(e.getMessage());
            }
        }
        else {
            throw new EccezioneGenerica("Connessione con il DB non riuscita nella ricerca della squadra in getSquadraDaEmail");
        }
    }
    public Squadra getSquadraDaUtente(Utente utente) throws EccezioneGenerica{
        return getSquadraDaEmail(utente.getEmail());
    }
    public List<Utente> getRichiesteIscrizionePerSquadra(Squadra squadra){
        Connection conn;
        ResultSet rs = null;
        List<Utente> utenti = new ArrayList<>();

        //apriamo la connessione con il DB
        conn = Connessione.getInstance().getDBConnection();
        if (conn != null) {
            try
            {
                //invocazione del metodo per la ricerca delle richieste di iscrizione in funzione della squadra
                rs = QuerySquadra.getRichiesteIscrizioneRSPerSquadra(conn, squadra);
                while(rs.next()) {
                    System.out.println("Richiesta di iscrizione per la squadra: " + squadra.getNome());
                    UtenteDAOMySQL utenteDAO = new UtenteDAOMySQL();
                    Utente utente = utenteDAO.recuperaUtenteDaEmail(rs.getString("utenti_email"));
                    utenti.add(utente);
                }
                return utenti;
            }
            catch(Exception e) {
                throw new EccezioneGenerica(e.getMessage());
            }
            finally {
                try {if (rs!=null) {rs.close();}}
                catch (SQLException e) {System.out.println("Seconda eccezione rilevata");}
            }
        }
        else {
            System.out.println("Connessione con il DB non riuscita nella ricerca delle richieste di iscrizione");
            throw new EccezioneGenerica("Connessione con il DB non riuscita nella ricerca delle richieste di iscrizione");
        }
    }

    public void creaSquadraPerAllenatore(Utente utente, Squadra squadra) {
        try{
            System.out.println("Creazione della squadra per l'allenatore");

            creaSquadra(squadra, utente);
        }
        catch (EccezioneGenerica e)
        {
            throw new EccezioneGenerica(e.getMessage());
        }

    }
    public void creaSquadra(Squadra squadra, Utente utente) {
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
                    throw new EccezioneGenerica("Errore nella creazione della squadra");
                }
            }
            catch(EccezioneGenerica e) {
                throw new EccezioneGenerica(e.getMessage());
            }
        }
        else {
            System.out.println("Connessione con il DB non riuscita nella creazione della squadra");
            throw new EccezioneGenerica("Connessione con il DB non riuscita nella creazione della squadra");
        }

    }

    public void aggiornaSquadra(Squadra squadra){
        Connection conn;

        conn = Connessione.getInstance().getDBConnection();
        if (conn != null) {
            try {
                for (Utente utente : squadra.getRichiesteIngresso()) {
                    ResultSet rs = null;
                    rs = QuerySquadra.getRichiestaIscrizioneRSPerEmail(conn, squadra ,utente.getEmail());
                    if(!rs.next()) {
                        System.out.println("Aggiunta della richiesta di iscrizione per la squadra: " + squadra.getNome());
                        aggiungiRichiestaASquadra(squadra, utente);
                    }
                }
                System.out.println("Aggiornamento delle richieste di iscrizione completato per la squadra: " + squadra.getNome());
            }
            catch (Exception e) {
                throw new EccezioneGenerica(e.getMessage());
            }
        }
        else {
            throw new EccezioneGenerica("Connessione con il DB non riuscita nell'aggiornamento della squadra in aggiornaSquadra");
        }
    }

    public void aggiungiRichiestaASquadra(Squadra squadra, Utente utente) {

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
                    throw new EccezioneGenerica("Errore nell'inserimento della richiesta di iscrizione in aggiungiRichiestaASquadra in SquadraDAOMySQL");
                }
                //se l'operazione è andata a buon fine non faccio nulla di particolare
            }
            catch(Exception e) {
                throw new EccezioneGenerica(e.getMessage());
            }
        }
        else {
            throw new EccezioneGenerica("Connessione con il DB non riuscita nella ricerca della squadra");
        }
    }

    public void IscrizioneUtenteASquadra(Utente utente, Squadra squadra) {
        //Prima di compiere l'iscrizione devo controllare se la relazione esiste già
        if(!verificaEsistenzaSquadra(utente.getEmail())){
            //L'iscrizione della squadra per un utente è simile alla creazione per un allenatore solamente che devo prima eliminare la richiesta di iscrizione
            eliminaRichiestaIscrizione(squadra, utente);
            creaSquadra(squadra,utente);
        }
    }

    public void eliminaRichiestaIscrizione(Squadra squadra, Utente utente) {
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
                    throw new EccezioneGenerica("Errore nell'eliminazione della richiesta di iscrizione in eliminaRichiestaIscrizione in SquadraDAOMySQL");
                }
                //se l'operazione è andata a buon fine non faccio nulla
            }
            catch(Exception e) {
                throw new EccezioneGenerica(e.getMessage());
            }
        }
        else {
            throw new EccezioneGenerica("Connessione con il DB non riuscita nella ricerca della squadra");
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
    public void visualizzaTutteLeSquadre() {
        System.out.println("Visualizzazione di tutte le squadre non implementato");
    }
    public Boolean verificaEsistenzaSquadraUtente(Squadra squadra, Utente utente){
        Connection conn;
        ResultSet rs = null;

        //apriamo la connessione con il DB
        conn = Connessione.getInstance().getDBConnection();
        if (conn != null) {
            try
            {
                //invocazione del metodo per la ricerca dell'utente in funzione della email
                rs = QuerySquadra.getRichiestaIscrizioneRSPerEmail(conn, squadra, utente.getEmail());
                return rs.next();
            }
            catch(Exception e)
            {
                throw new EccezioneGenerica(e.getMessage());
            }
            finally {
                try {if (rs!=null) {rs.close();}}
                catch (SQLException e) {System.out.println("Seconda eccezione rilevata");}
            }
        }
        else {
            throw new EccezioneGenerica("Connessione con il DB non riuscita nella verifica dell'esistenza della squadra");
        }
    }

    public Boolean verificaEsistenzaSquadra(String nomeSquadra){
        try
        {
            //utilizzo il metodo già implementato per verificare l'esistenza della squadra
            getSquadraDaNome(nomeSquadra);

            //se arrivo qui, la squadra esiste e non ho lanciato eccezioni
            return true;
        }
        catch (EccezioneGenerica e)
        {
            //se arrivo qui vuol dire che ho lanciato un'eccezione e la probabilmente la squadra non esiste
            System.out.println("eccezione lanciata in verificaEsistenzaSquadra: "+e.getMessage());
            return false;
        }
    }

}
