package engineering.query;

import engineering.eccezioni.EccezioneGenerica;
import modelli.Allenamento;
import modelli.Login;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class QueriesLogin {

    /*
    public static int InserimentoUtente(Statement stmt, Login login) throws SQLException {
        try {
            String query= "SELECT * FROM utenti where username = ? and password = ? ;";

            int risultato = stmt.executeUpdate(query);
            return risultato;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

     */

    public static ResultSet RecuperaUtenteRSPerEmail(Connection connection, String email) throws EccezioneGenerica {
        PreparedStatement statement = null;

        try {
            //creazione della query parametrica
            String query= "SELECT * FROM utenti where email = ? ";

            //preparazione dello statement
            statement = connection.prepareStatement(query);

            //setting dei parametri della query
            statement.setString(1, email);

            //esecuzione della query e restituzione del risultato
            ResultSet rs;
            rs = statement.executeQuery();
            rs.next();
            return rs;

        } catch (SQLException e) {
            throw new EccezioneGenerica(e.getMessage());
        }
    }

    public static ResultSet RecuperaUtenteRSPerLogin(Connection connection, Login login) throws SQLException {
        ResultSet rs = null;

        rs= RecuperaUtenteRSPerEmail(connection, login.getEmail());

        if(rs == null) {
            System.out.println("Utente non trovato, restituisco null al DAO");
            throw new EccezioneGenerica("Utente non trovato dalle query");
        }

        while (rs.next()) {
            if (rs.getString("password").equals(login.getPassword())) {
                System.out.println("Utente trovato, restituisco il risultato al DAO");
                return rs;
            }
        }
        throw new EccezioneGenerica("Password errata, utente non trovato dalla query");
    }

    public static ResultSet recuperaAllenamentiPerData(Connection connection, String data) throws SQLException {

        PreparedStatement statement = null;

        try {
            String query = "SELECT * from allenamento where data = ? ;";
            statement = connection.prepareStatement(query);

            statement.setString(1, data);

            ResultSet rs=  statement.executeQuery();

            //restituiamo il risultato di tutte le operazioni
            return rs;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public static ResultSet RecuperaSquadreRSPerEmail(Connection connection, String email) throws SQLException {
        PreparedStatement statement = null;

        try {
            String query= "SELECT * FROM squadra where utenti_email = ? ;";
            statement = connection.prepareStatement(query);

            statement.setString(1, email);

            return statement.executeQuery();

        } catch (SQLException e) {throw new EccezioneGenerica("Errore di recupera allenamenti per utente");}
    }

    public static ResultSet RecuperaAllenamentiRSPerEmail(Connection connection, String email) throws SQLException {
        PreparedStatement statement = null;

        try {
            String query= "SELECT * FROM allenamento where utenti_email = ? ;";
            statement = connection.prepareStatement(query);

            statement.setString(1, email);

            return statement.executeQuery();

        } catch (SQLException e) {throw new EccezioneGenerica("Errore di recupera allenamenti per utente");}
    }

    public static int modificaSquadraPerEmail(Connection connection, String squadra, String email) throws SQLException {
        PreparedStatement statement = null;

        try {
            String query= "UPDATE squadra set codice = ? where utenti_email = ? ;";
            statement = connection.prepareStatement(query);

            statement.setString(1, squadra);
            statement.setString(2, email);

            return statement.executeUpdate();

        } catch (SQLException e) {throw new EccezioneGenerica("Errore di modifica squadra per utente");}
    }
}

