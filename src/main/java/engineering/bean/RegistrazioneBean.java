package engineering.bean;

public class RegistrazioneBean extends LoginBean {

    private String email;
    private boolean allenatore;


    public RegistrazioneBean(String username, String email, String password, boolean allenatore) {
        super(email, password);
        setUsername(username);
        setAllenatore(allenatore);
    }

    public String getUsername() {return this.email;}
    public void setUsername(String username) {this.email = username;}

    public boolean getAllenatore() {return this.allenatore;}
    public void setAllenatore(boolean allenatore) {this.allenatore = allenatore;}
}
