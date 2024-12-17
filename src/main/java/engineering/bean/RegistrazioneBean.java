package engineering.bean;

public class RegistrazioneBean extends LoginBean{

    private String email;
    private boolean alleantore;

    RegistrazioneBean(){}

    public RegistrazioneBean(String username, String email, String password, boolean alleantore) {
        super(email, password);
        setUsername(username);
        setAlleantore(alleantore);
    }

    public String getUsername() {return this.email;}
    public void setUsername(String username) {this.email = username;}

    public boolean getAlleantore() {return this.alleantore;}
    public void setAlleantore(boolean alleantore) {this.alleantore = alleantore;}
}
