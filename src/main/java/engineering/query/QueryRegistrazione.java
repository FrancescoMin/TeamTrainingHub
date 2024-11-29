package engineering.query;

import engineering.eccezioni.EccezzioneGenerica;
import modelli.Login;
import modelli.Registrazione;
import modelli.Utente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class QueryRegistrazione {

    public static int InserisciUtenteQuery(Connection conn, Registrazione registrazione) throws EccezzioneGenerica {
        String sql = "INSERT INTO utenti(user, email, password, allenatore) VALUES (?, ?, ?, ?)";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, registrazione.getUsername());
            ps.setString(2, registrazione.getEmail());
            ps.setString(3, registrazione.getPassword());
            ps.setBoolean(4, registrazione.getAllenatore());
            return ps.executeUpdate();

        } catch (SQLException e) {
            throw new EccezzioneGenerica("Errrore nell'inserimento dell'utente");
        }
    }
}
