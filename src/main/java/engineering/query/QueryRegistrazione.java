package engineering.query;

import modelli.Login;
import modelli.Utente;

public class QueryRegistrazione {

    public void InserisciUtente(Login login) {
        String query = "insert into utenti(user, email, password, squadre, allenatore) values ('"+login.getUsername()+"','"+login.getEmail()+"','"+utente.ge+"',NULL,'false');";
    }
}
