package engineering.dao;

import engineering.altro.Connessione;
import engineering.eccezioni.EccezioneGenerica;
import engineering.query.QueryAllenamento;
import engineering.query.QuerySquadra;
import modelli.Allenamento;
import modelli.Utente;

import java.sql.Connection;

public class AllenamentoDAOMySQL implements AllenamentoDAO {

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
}