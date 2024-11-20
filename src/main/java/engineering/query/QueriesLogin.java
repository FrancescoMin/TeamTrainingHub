package engineering.query;

import modelli.Allenamento;
import modelli.Login;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class QueriesLogin {

    //public QueriesLogin() {}

    public static ResultSet RecuperaUtentePerLogin(Statement stmt, Login login) throws SQLException {
        try {
            String query= "SELECT * FROM utenti where email = '" + login.getEmail() + "' and password = '" +login.getPassword()+"' ;";
            ResultSet resultSet = stmt.executeQuery(query);
            return resultSet;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<Allenamento> recuperaAllenamentiPerData(Statement stmt, String PrimaryKey) throws SQLException {

        ResultSet rs = null;
        String query = "SELECT * from allenamento where nome = '"+ PrimaryKey +"' ;";


        rs = stmt.executeQuery(query);
        List<Allenamento> allenamenti = new ArrayList<>();
        while (rs.next()) {
            Allenamento allenamento = new Allenamento();
            allenamento.setData(rs.getString("data"));
            allenamento.setDurata(rs.getInt("durata"));
            allenamento.setDescrizione(rs.getString("descrizione"));
            allenamenti.add(allenamento);
        }
        return allenamenti;
    }
}
