package engineering.altri;

import java.io.*;
import java.sql.*;
import java.util.*;

public class Connect {

    private String jdbc;
    private String user;
    private String password;
    private static Connect instance = null;
    private Connection conn = null;
    private static final String PATH = "src/main/resources/connection.properties";
}
