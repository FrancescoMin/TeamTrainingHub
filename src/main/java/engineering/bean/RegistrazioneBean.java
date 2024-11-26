package engineering.bean;

public class RegistrazioneBean extends LoginBean{

    //utilizzato nella registrazione
    private String email;
    private boolean alleantore;

    RegistrazioneBean(){}

    public RegistrazioneBean(String email, String password, String username, boolean alleantore) {
        super(email, password);
        setUsername(username);
        setAlleantore(alleantore);
    }

    public String getUsername() {return this.email;}
    public void setUsername(String username) {this.email = username;}

    public boolean getAlleantore() {return this.alleantore;}
    public void setAlleantore(boolean alleantore) {this.alleantore = alleantore;}
}
