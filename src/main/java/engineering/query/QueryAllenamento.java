package engineering.query;

import engineering.eccezioni.EccezioneAllenamentoInvalido;
import modelli.Allenamento;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class QueryAllenamento {

    private QueryAllenamento() {
        //Add a private constructor to hide the implicit public one
    }

    public static final String CREA_ALLENAMENTO_QUERY = "INSERT INTO allenamento (data, descrizione , utenti_email,orarioInizio , orarioFine ) VALUES (?, ?, ? ,?, ?)";

    public static int createAllenamento(Connection connection, Allenamento allenamento , String utentiEmail) throws EccezioneAllenamentoInvalido {
        try {

            //preparazione dello statement
            PreparedStatement statement = getPreparedStatement(connection, allenamento, utentiEmail);

            //esecuzione della query e restituzione del risultato
            int i=0;

            i=statement.executeUpdate();

            return  i;


        } catch (SQLException e) {
            throw new EccezioneAllenamentoInvalido("Errore nella creazione dell'allenamento nel DB");
        }
    }

    private static PreparedStatement getPreparedStatement(Connection connection, Allenamento allenamento, String utentiEmail) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(CREA_ALLENAMENTO_QUERY);

        //setting dei parametri della query
        statement.setString(1, allenamento.getData());
        statement.setString(2, allenamento.getDescrizione());
        statement.setString(3, utentiEmail);
        statement.setString(4, allenamento.getOrarioInizio());
        statement.setString(5, allenamento.getOrarioFine());
        return statement;
    }

}
