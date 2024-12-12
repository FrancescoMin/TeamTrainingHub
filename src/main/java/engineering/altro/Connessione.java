package engineering.altro;

import java.io.*;
import java.sql.*;
import java.util.*;

public class Connessione {

    private String jdbc;
    private String user;
    private String password;
    private static Connessione instance = null;
    private Connection conn = null;
    private static final String PATH = "src/main/resources/connection.properties";

    private Connessione() {
    }

    /*Singleton*/
    public static synchronized Connessione getInstance() {
        if (instance == null) {
            instance = new Connessione();
        }
        return instance;
    }

    public synchronized Connection getDBConnection() {
        if (this.conn == null) {
            getInfo();

            try{
                this.conn = DriverManager.getConnection(jdbc, user, password);


            //MODIFICARE LA GESTIONE DELL'ECCEZIONE IN MODO CORRETTO
            } catch (SQLException e){
                System.out.println(e.getMessage());
            }

        }
        return this.conn;
    }

    private void getInfo() {
        try(FileInputStream fileInputStream = new FileInputStream(PATH)) {

            // Load DB Connection info from Properties file
            Properties prop = new Properties() ;
            prop.load(fileInputStream);

            jdbc = prop.getProperty("JDBC_URL") ;
            user = prop.getProperty("USER") ;
            password = prop.getProperty("PASSWORD") ;


        //MODIFICARE LA GESTIONE DELL'ECCEZIONE IN MODO CORRETTO
        } catch (IOException e){
            System.out.println(e.getMessage());
        }
    }
}