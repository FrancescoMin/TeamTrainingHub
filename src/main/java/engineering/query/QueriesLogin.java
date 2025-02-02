package engineering.query;

import engineering.eccezioni.EccezioneAllenamentoInvalido;
import engineering.eccezioni.EccezioneGenerica;
import engineering.eccezioni.EccezioneSquadraInvalida;
import engineering.eccezioni.EccezioneUtenteInvalido;
import modelli.Login;

import java.sql.*;

public class QueriesLogin {

    private QueriesLogin() {
        //Add a private constructor to hide the implicit public one
    }


    public static ResultSet RecuperaUtenteRSPerEmail(Connection connection,String email) throws EccezioneUtenteInvalido {
        PreparedStatement statement = null;

        try {
            //creazione della query parametrica
            String SELECT_USER_BY_EMAIL_QUERY= "SELECT * FROM utenti where email = ? ";

            //preparazione dello statement
            statement = connection.prepareStatement(SELECT_USER_BY_EMAIL_QUERY);

            //setting dei parametri della query
            statement.setString(1, email);

            //esecuzione della query e restituzione del risultato
            return statement.executeQuery();

        } catch (SQLException e) {
            throw new EccezioneUtenteInvalido(e.getMessage());
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

    public static ResultSet recuperaAllenamentiPerData(Connection connection, String data) throws EccezioneGenerica {

        PreparedStatement statement = null;
        try {
            String query = "SELECT * from allenamento where data = ? ;";
            statement = connection.prepareStatement(query);

            statement.setString(1, data);

            ResultSet rs=  statement.executeQuery();

            //restituiamo il risultato di tutte le operazioni
            return rs;

        } catch (SQLException e) {
            throw new EccezioneGenerica (e.getMessage());
        }

    }


    public static ResultSet RecuperaAllenamentiRSPerEmail(Connection connection, String email) throws EccezioneAllenamentoInvalido {
        PreparedStatement statement = null;

        try {
            String query= "SELECT * FROM allenamento where utenti_email = ? ;";
            statement = connection.prepareStatement(query);

            statement.setString(1, email);

            return statement.executeQuery();

        } catch (SQLException e) {throw new EccezioneAllenamentoInvalido("Errore di recupera allenamenti per utente");}
    }

    public static int modificaSquadraPerEmail(Connection connection, String squadra, String email) throws EccezioneSquadraInvalida {
        PreparedStatement statement = null;

        try {
            String query= "UPDATE squadra set codice = ? where utenti_email = ? ;";
            statement = connection.prepareStatement(query);

            statement.setString(1, squadra);
            statement.setString(2, email);

            return statement.executeUpdate();

        } catch (SQLException e) {throw new EccezioneSquadraInvalida("Errore di modifica squadra per utente");}
    }
}

