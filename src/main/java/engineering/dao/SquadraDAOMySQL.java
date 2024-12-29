package engineering.dao;

import engineering.altro.Connessione;
import engineering.eccezioni.EccezioneGenerica;
import engineering.query.QuerySquadra;
import modelli.*;

import java.sql.*;


import static engineering.query.QuerySquadra.*;

public class SquadraDAOMySQL implements SquadraDAO {

    public SquadraDAOMySQL() {}

    public void creaSquadraPerAllenatore(Utente utente, Squadra squadra) {
        try{
            System.out.println("Creazione della squadra per l'allenatore");

            creaSquadra(squadra, utente);
            IscrizioneUtenteASquadra(utente, squadra);
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
                righeModificate = QuerySquadra.createSquadra(conn, squadra.getNome(), utente.getEmail());

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

    public void IscrizioneUtenteASquadra(Utente utente, Squadra squadra) {
        //effettivamente noi non iscriviamo l'utente alla squadra, ma possiamo immaginare che il metodo faccia qualcosa
        System.out.println("Iscrizione dell'utente alla squadra");
    }
    public void visualizzaTutteLeSquadre() {
        System.out.println("Visualizzazione di tutte le squadre");
    }
}
