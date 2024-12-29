package engineering.query;

import engineering.altro.Connessione;
import engineering.eccezioni.EccezioneGenerica;
import modelli.Allenamento;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class QueryAllenamento {

    public static String creaAllenamentoQuery = "INSERT INTO allenamento (data,durata, descrizione, utenti_email) VALUES (?, ?, ? ,? )";

    public static int createAllenamento(Connection connection, Allenamento allenamento , String utenti_email) throws EccezioneGenerica {
        try {

            //creazione della query parametrica


            //preparazione dello statement
            Connection conn = Connessione.getInstance().getDBConnection();
            PreparedStatement statement = conn.prepareStatement(creaAllenamentoQuery);

            //setting dei parametri della query
            statement.setString(1, allenamento.getData());
            statement.setString(2, String.valueOf(allenamento.getDurata()));
            statement.setString(3, allenamento.getDescrizione());
            statement.setString(4, utenti_email);

            //esecuzione della query e restituzione del risultato
            int i=0;

            System.out.println("email: "+utenti_email + " inserito per allenamento in data: "+allenamento.getData() + " con durata: "+allenamento.getDurata() + " e descrizione: "+allenamento.getDescrizione());
            i=statement.executeUpdate();
            return  i;


        } catch (SQLException e) {
            e.printStackTrace();
            throw new EccezioneGenerica(e.getMessage());
        }
    }

}
