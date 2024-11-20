package engineering.dao;

import engineering.query.QueriesLogin.*;
import engineering.eccezioni.UtenteNonEsistenteEccezione;
import modelli.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static engineering.query.QueriesLogin.RecuperaUtentePerLogin;
import static engineering.query.QueriesLogin.recuperaAllenamentiPerData;

public class UtenteDAOMySQL implements UtenteDAO {

    public void inserisciUtente(Utente utente)
    {}//throws EmailAlreadyInUseException, UsernameAlreadyInUseException;

    public Utente caricaUtente(String string)
    {return null;}//throws UserDoesNotExistException;


    public class MySQLConnection
    {
        private static final String URL = "jdbc:mysql://localhost:3306/database";
        private static final String USER = "root";
        private static final String PASSWORD = "root";

        public static Connection getConnection()
        {
            Connection connection = null;
            try
            {
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
            } catch (SQLException e)
            {
                e.printStackTrace();
            }
            return connection;
        }
    }




    public Utente recuperaUtenteDaLogin(Login login) throws  UtenteNonEsistenteEccezione {
        Statement stmt = null, stmtSquad = null, stmtAll = null;
        Connection conn;
        ResultSet rs = null, rsSquad = null, rsAll = null;
        Utente utente;

        //apriamo la connessione con il DB
        conn = MySQLConnection.getConnection();
        if (conn != null) {
            try {
                //creaiamo lo statement per il caso
                stmt = conn.createStatement();

                //invocazione del metodo per la ricerca dell'utente in funzione della variabile di ricerca
                rs = RecuperaUtentePerLogin(stmt, login);


                while (rs.next()) {
                    System.out.println("username: " + rs.getString("username"));
                    System.out.println("password: " + rs.getInt("password"));

                    if(rs.getBoolean("allenatore"))
                    {
                        System.out.println("Utente allenatore");
                        utente= new Allenatore(rs.getString("username"),rs.getString("email"));
                    }
                    else {
                        System.out.println("Utente non allenatore");
                        utente = new Giocatore(rs.getString("username"), rs.getString("email"));
                    }

                    //controllo se un utente ha degli allenamenti
                    if (rs.getString("allenamento") != null) {
                        try
                        {
                            stmtAll =conn.createStatement();
                            try
                            {
                                //creazione della variabile dove salviamo gli allenamenti del DB
                                List<Allenamento> allenamenti = new ArrayList<>();

                                //recupero gli allenamenti dal database
                                allenamenti=recuperaAllenamentiPerData(stmtAll, rs.getString("allenamento"));

                                //aggiungo all'utente gli allenamenti
                                utente.setAllenamenti(allenamenti);

                            } catch (SQLException e) {throw new RuntimeException(e);}

                        } catch (SQLException e) {throw new RuntimeException(e);}
                    }
                }


            } catch (SQLException e) {
                System.out.println("Prima eccezione rilevata");
                e.printStackTrace();

            } finally
            {
                try {
                    if (conn != null) conn.close();
                } catch (SQLException e) {
                    System.out.println("Seconda eccezione rilevata");
                }
                //return null;
            }
        }/* throws UserDoesNotExistException;*/
        return null;
    }

    public void handleDAOException(Exception e) {}

}
