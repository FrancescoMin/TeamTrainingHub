package engineering.query;

import engineering.eccezioni.EccezioneSquadraInvalida;
import modelli.Squadra;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class QuerySquadra {

    private QuerySquadra() {
        //Add a private constructor to hide the implicit public one
    }

    public static int createSquadra(Connection connection, String nomeSquadra , String utentiEmail, String allenatore) throws EccezioneSquadraInvalida {
        try {
            //creazione della query parametrica
            String query= "INSERT INTO squadra (codice, utenti_email, allenatore) VALUES (?, ?, ?)";

            //preparazione dello statement
            PreparedStatement statement = connection.prepareStatement(query);

            //setting dei parametri della query
            statement.setString(1, nomeSquadra);
            statement.setString(2, utentiEmail);
            statement.setString(3, allenatore);

            //esecuzione della query e restituzione del risultato
            int i=0;


            System.out.println("In QuerySquadra email: "+utentiEmail + " nome squadra: "+nomeSquadra);
            i=statement.executeUpdate();
            System.out.println("Non lo so,facciamo la seconda prova");
            return  i;


        } catch (SQLException e) {
            throw new EccezioneSquadraInvalida("Errore di creazione della squadra");
        }
    }

    public static ResultSet getsquadrarsdanome(Connection connection, String nomeSquadra) throws EccezioneSquadraInvalida {
        PreparedStatement statement = null;
        try {
            //creazione della query parametrica
            String query = "SELECT ? FROM squadra where codice = ? ";

            //preparazione dello statement
            statement = connection.prepareStatement(query);

            //setting dei parametri della query
            statement.setString(1, "*");
            statement.setString(2, nomeSquadra);

            //esecuzione della query e restituzione del risultato
            return statement.executeQuery();
        } catch (SQLException e) {
            throw new EccezioneSquadraInvalida("Problema con la query di recupero della squadra");
        }
    }

    public static ResultSet recuperasquadrersperemail(Connection connection, String email) throws EccezioneSquadraInvalida {
        PreparedStatement statement = null;

        try {
            String query= "SELECT ? FROM squadra where utenti_email = ? ;";
            statement = connection.prepareStatement(query);

            statement.setString(1, "*");
            statement.setString(2, email);

            return statement.executeQuery();

        } catch (SQLException e) {throw new EccezioneSquadraInvalida("Errore di recupera allenamenti per utente");}
    }

    public static int eliminaRichiestaIscrizione(Connection connection, Squadra squadra, String utenteEmail) throws EccezioneSquadraInvalida {
        try {
            //creazione della query parametrica
            String query = "DELETE FROM richiesteiscrizione WHERE Squadra_codice = ? AND utenti_email = ?";

            //preparazione dello statement
            PreparedStatement statement = connection.prepareStatement(query);

            //setting dei parametri della query
            statement.setString(1, squadra.getNome());
            statement.setString(2, utenteEmail);

            //esecuzione della query
            return statement.executeUpdate();
        } catch (SQLException e) {
            throw new EccezioneSquadraInvalida("Errore di eliminazione della richiesta di iscrizione");
        }
    }

    public static int inserisciRichiestaIscrizione(Connection connection, Squadra squadra , String utenteEmail) throws EccezioneSquadraInvalida {
        try {
            //creazione della query parametrica
            String query = "INSERT INTO richiesteiscrizione (utenti_email, Squadra_codice, Squadra_utenti_email) VALUES ( ? , ? , ? )";

            //preparazione dello statement
            PreparedStatement statement = connection.prepareStatement(query);

            //setting dei parametri della query
            statement.setString(1, utenteEmail);
            statement.setString(2, squadra.getNome());
            statement.setString(3, squadra.getAllenatore());

            //esecuzione della query e restituzione del risultato
            return statement.executeUpdate();
        } catch (SQLException e) {
            throw new EccezioneSquadraInvalida("Errore di inserimento della richiesta di iscrizione");
        }
    }

    public static ResultSet getrichiestaiscrizionersperemail(Connection connection, Squadra squadra, String utenteEmail) throws EccezioneSquadraInvalida {
        try {
            //creazione della query parametrica
            String query = "SELECT ? FROM richiesteiscrizione where Squadra_codice = ? AND utenti_email = ?";

            //preparazione dello statement
            PreparedStatement statement = connection.prepareStatement(query);

            //setting dei parametri della query
            statement.setString(1, "*");
            statement.setString(2, squadra.getNome());
            statement.setString(3, utenteEmail);

            //esecuzione della query e restituzione del risultato
            return statement.executeQuery();
        } catch (SQLException e) {
            throw new EccezioneSquadraInvalida("Errore di recupero della richiesta di iscrizione");
        }
    }

    public static ResultSet getrichiesteiscrizionerspersquadra(Connection connection, Squadra squadra) throws EccezioneSquadraInvalida {
        try {
            //creazione della query parametrica
            String query = "SELECT ? FROM richiesteiscrizione where Squadra_codice = ?";

            //preparazione dello statement
            PreparedStatement statement = connection.prepareStatement(query);

            //setting dei parametri della query
            statement.setString(1, "*");
            statement.setString(2, squadra.getNome());

            //esecuzione della query e restituzione del risultato
            return statement.executeQuery();
        } catch (SQLException e) {
            throw new EccezioneSquadraInvalida("Errore di recupero delle richieste di iscrizione");
        }
    }
}
