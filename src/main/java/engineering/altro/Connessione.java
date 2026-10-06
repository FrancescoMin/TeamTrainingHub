package engineering.altro;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Connessione {

    private String jdbc;
    private String user;
    private String password;
    private static Connessione instance = null;
    private Connection conn = null;

    private Connessione() {
        // Costruttore privato singleton
    }

    public static synchronized Connessione getInstance() {
        if (instance == null) {
            instance = new Connessione();
        }
        return instance;
    }

    public synchronized Connection getDBConnection() {
        try {
            // Controlla se la connessione è null o se è stata precedentemente chiusa
            if (this.conn == null || this.conn.isClosed()) {
                getInfo();
                this.conn = DriverManager.getConnection(jdbc, user, password);
            }
        } catch (SQLException e) {
            System.err.println("Errore durante la connessione al database: " + e.getMessage());
        }
        return this.conn;
    }

    private void getInfo() {
        // Caricamento portabile dal classpath (come in DAOFactory)
        try (InputStream input = Connessione.class.getClassLoader().getResourceAsStream("connection.properties")) {
            if (input == null) {
                System.err.println("File connection.properties non trovato nel classpath!");
                return;
            }

            Properties prop = new Properties();
            prop.load(input);

            this.jdbc = prop.getProperty("JDBC_URL");
            this.user = prop.getProperty("USER");
            this.password = prop.getProperty("PASSWORD");

        } catch (IOException e) {
            System.err.println("Errore durante la lettura di connection.properties: " + e.getMessage());
        }
    }
}