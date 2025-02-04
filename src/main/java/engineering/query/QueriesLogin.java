package engineering.query;

import engineering.eccezioni.EccezioneAllenamentoInvalido;
import engineering.eccezioni.EccezioneUtenteInvalido;

import java.sql.*;

public class QueriesLogin {

    private QueriesLogin() {
        //Add a private constructor to hide the implicit public one
    }


    public static ResultSet recuperautentersperemail(Connection connection, String email) throws EccezioneUtenteInvalido {
        PreparedStatement statement = null;

        try {
            //creazione della query parametrica
            String string= "SELECT ? FROM utenti where email = ? ";

            //preparazione dello statement
            statement = connection.prepareStatement(string);

            statement.setString(1, "*");
            //setting dei parametri della query
            statement.setString(2, email);

            //esecuzione della query e restituzione del risultato
            return statement.executeQuery();

        } catch (SQLException e) {
            throw new EccezioneUtenteInvalido(e.getMessage());
        }
    }

    public static ResultSet recuperaallenamentirsperemail(Connection connection, String email) throws EccezioneAllenamentoInvalido {
        PreparedStatement statement = null;

        try {
            String query= "SELECT ? FROM allenamento where utenti_email = ? ;";
            statement = connection.prepareStatement(query);

            statement.setString(1, "*");
            statement.setString(2, email);

            return statement.executeQuery();

        } catch (SQLException e) {throw new EccezioneAllenamentoInvalido("Errore di recupera allenamenti per utente");}
    }
}

