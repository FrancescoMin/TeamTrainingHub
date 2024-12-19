package engineering.query;

import engineering.altro.Connessione;
import engineering.eccezioni.EccezioneGenerica;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class QuerySquadra {


    public static String creaSquadraQuery = "insert into squadra (codice, utenti_email) values (?, ?)";

    public static int createSquadra(Connection connection, String nomeSquadra , String utenti_email) throws EccezioneGenerica {
        try {

            //creazione della query parametrica
            String query= "INSERT INTO squadra (codice, utenti_email) VALUES (?, ?)";

            //preparazione dello statement
            Connection conn = Connessione.getInstance().getDBConnection();

            PreparedStatement statement = conn.prepareStatement(query);

            //setting dei parametri della query
            statement.setString(1, nomeSquadra);
            statement.setString(2, utenti_email);

            //esecuzione della query e restituzione del risultato
            int i=0;


            System.out.println("email: "+utenti_email + " nome squadra: "+nomeSquadra);
            i=statement.executeUpdate();
            System.out.println("Non lo so,facciamo la seconda prova");
            return  i;


        } catch (SQLException e) {
            e.printStackTrace();
            throw new EccezioneGenerica(e.getMessage());
        }
    }


}
