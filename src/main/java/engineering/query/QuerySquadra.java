package engineering.query;

import engineering.altro.Connessione;
import engineering.eccezioni.EccezioneGenerica;
import modelli.Squadra;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class QuerySquadra {


    //public static String creaSquadraQuery = "insert into squadra (codice, utenti_email) values (?, ?)";

    public static int createSquadra(Connection connection, String nomeSquadra , String utenti_email) throws EccezioneGenerica {
        try {
            //creazione della query parametrica
            String query= "INSERT INTO squadra (codice, utenti_email, allenatore) VALUES (?, ?, ?)";


            //preparazione dello statement
            PreparedStatement statement = connection.prepareStatement(query);

            //setting dei parametri della query
            statement.setString(1, nomeSquadra);
            statement.setString(2, utenti_email);
            statement.setString(3, utenti_email);

            //esecuzione della query e restituzione del risultato
            int i=0;


            System.out.println("In QuerySquadra email: "+utenti_email + " nome squadra: "+nomeSquadra);
            i=statement.executeUpdate();
            System.out.println("Non lo so,facciamo la seconda prova");
            return  i;


        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new EccezioneGenerica(e.getMessage());
        }
    }

    public static ResultSet getSquadraRSDaNome(Connection connection, String nomeSquadra) throws EccezioneGenerica {
        PreparedStatement statement = null;

        try {
            //creazione della query parametrica
            String query = "SELECT * FROM squadra where codice = ? ";

            //preparazione dello statement
            statement = connection.prepareStatement(query);

            //setting dei parametri della query
            statement.setString(1, nomeSquadra);

            //esecuzione della query e restituzione del risultato
            return statement.executeQuery();
        } catch (SQLException e) {
            throw new EccezioneGenerica(e.getMessage());
        }
    }

    public static int inserisciRichiestaIscrizione(Connection connection, Squadra squadra , String utente) throws EccezioneGenerica {
        try {
            //creazione della query parametrica
            String query = "INSERT INTO richiesteiscrizione (utenti_email, Squadra_codice, Squadra_utenti_email) VALUES ( ? , ? , ? )";

            //preparazione dello statement
            PreparedStatement statement = connection.prepareStatement(query);

            //setting dei parametri della query
            statement.setString(1, utente);
            statement.setString(2, squadra.getNome());
            statement.setString(3, squadra.getAllenatore());

            //esecuzione della query e restituzione del risultato
            return statement.executeUpdate();
        } catch (SQLException e) {
            throw new EccezioneGenerica(e.getMessage());
        }
    }

    public static ResultSet getRichiesteIscrizioneRSPerSquadra(Connection connection, Squadra squadra) throws EccezioneGenerica {
        try {
            //creazione della query parametrica
            String query = "SELECT * FROM richiesteinscrizione where Squadra_codice = ? AND Squadra_utenti_email = ?";

            //preparazione dello statement
            PreparedStatement statement = connection.prepareStatement(query);

            //setting dei parametri della query
            statement.setString(1, squadra.getNome());
            statement.setString(2, squadra.getAllenatore());

            //esecuzione della query e restituzione del risultato
            return statement.executeQuery();
        } catch (SQLException e) {
            throw new EccezioneGenerica(e.getMessage());
        }
    }
}
