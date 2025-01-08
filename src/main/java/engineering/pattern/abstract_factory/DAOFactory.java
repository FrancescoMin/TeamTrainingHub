package engineering.pattern.abstract_factory;
import engineering.dao.*;
import engineering.pattern.Singleton;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public abstract class DAOFactory {

    private static DAOFactory me = null;

    protected DAOFactory(){
    }

    /** Recupera dal file config.properties il tipo di persistenza utilizzata,
     * se non è possibile come default viene utilizzato MYSQL */
    public static synchronized DAOFactory getDAOFactory(){
        if ( me == null ){

            Properties properties = new Properties();

            try(InputStream input = DAOFactory.class.getClassLoader().getResourceAsStream("demo.properties");) {

                properties.load(input);
                // Accesso alla chiave del file demo
                String parameter = properties.getProperty("mode.type", "false");

                //lettura del valore dal file properties
                Boolean boo = Boolean.valueOf(parameter);

                //assegno il valore alla variabile demo del singleton per semplicità
                Singleton istanza = Singleton.getInstance();
                istanza.setDemo(boo);
            }
            catch (Exception e) {
                System.out.println("MainApplication: Errore durante la lettura del file di configurazione %s" + e.getMessage());
            }

            try (InputStream input = DAOFactory.class.getClassLoader().getResourceAsStream("config.properties")) {
                properties.load(input);
            }

            catch (IOException e){
                System.out.println(e.getMessage());
            }

            String persistenceType = properties.getProperty("persistence.type", "MYSQL");
            var anEnum = FactorySettings.valueOf(persistenceType);

            if (anEnum == FactorySettings.MYSQL) {
                System.out.println("Inizializzazione della factory con persistenza MySQL");
                me = new MySQLDAOFactory();
            } else if (anEnum == FactorySettings.JSON) {
                System.out.println("Inizializzazione della factory con persistenza JSON");
                me = new JsonDAOFactory();
            }
        }
        return me;
    }

    public abstract UtenteDAO createUtenteDAO();
    public abstract AllenamentoDAO createAllenamentoDAO();
    public abstract SquadraDAO createSquadraDAO();

}

