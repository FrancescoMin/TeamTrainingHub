package engineering.query;

import engineering.altro.Connessione;
import engineering.eccezioni.EccezioneGenerica;
import modelli.Allenamento;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class QueryAllenamento {

    private QueryAllenamento() {
        //Add a private constructor to hide the implicit public one
    }

    public static String creaAllenamentoQuery = "INSERT INTO allenamento (data,orarioInizio , orarioFine, descrizione, utenti_email) VALUES (?, ?, ? ,? )";

    public static int createAllenamento(Connection connection, Allenamento allenamento , String utenti_email) throws EccezioneGenerica {
        try {

            //preparazione dello statement
            PreparedStatement statement = getPreparedStatement(connection, allenamento, utenti_email);

            //esecuzione della query e restituzione del risultato
            int i=0;

            System.out.println("email: "+utenti_email + " inserito per allenamento in data: "+allenamento.getData() + " con orario inizio " + allenamento.getOrarioInizio() + "con orario finale " + allenamento.getOrarioFine() + " e descrizione: "+allenamento.getDescrizione());
            i=statement.executeUpdate();
            return  i;


        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new EccezioneGenerica(e.getMessage());
        }
    }

    private static PreparedStatement getPreparedStatement(Connection connection, Allenamento allenamento, String utenti_email) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(creaAllenamentoQuery);

        //setting dei parametri della query
        statement.setString(1, allenamento.getData());
        statement.setString(2, String.valueOf(allenamento.getOrarioInizio()));
        statement.setString(3, String.valueOf(allenamento.getOrarioFine()));
        statement.setString(3, allenamento.getDescrizione());
        statement.setString(4, utenti_email);
        return statement;
    }

}
