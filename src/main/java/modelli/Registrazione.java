package modelli;

public class Registrazione extends Login{

    private String username;
    private Boolean allenatore;

    Registrazione(){}

    public Registrazione(String email, String password, String username, Boolean allenatore) {
        super(email, password);
        setUsername(username);
        setAllenatore(allenatore);
    }

    public void setUsername(String username) {this.username = username;}
    public String getUsername() {return this.username;}

    public void setAllenatore(Boolean allenatore) {this.allenatore = allenatore;}
    public Boolean getAllenatore() {return this.allenatore;}

}