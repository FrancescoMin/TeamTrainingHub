package engineering.query;

import engineering.eccezioni.EccezioneUtenteInvalido;
import modelli.Registrazione;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class QueryRegistrazione {

    private QueryRegistrazione() {
        // costruttore vuoto di default
    }

    public static int InserisciUtenteQuery(Connection connection, Registrazione registrazione) throws EccezioneUtenteInvalido {
        String sql = "INSERT INTO utenti(username, email, password, allenatore) VALUES (?, ?, ?, ?)";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, registrazione.getUsername());
            ps.setString(2, registrazione.getEmail());
            ps.setString(3, registrazione.getPassword());
            ps.setBoolean(4, registrazione.getAllenatore());
            return ps.executeUpdate();

        } catch (SQLException e) {
            throw new EccezioneUtenteInvalido("Errore nell'inserimento dell'utente");
        }
    }
}
