package modelli;

public class Login {

    private String email;
    private String password;

    //utilizziamo l'email solamente per la registrazione
    private String username;

    public Login(){}

    //costruttore utilizzato nel login
    public Login(String email, String password)
    {
        setEmail(email);
        setPassword(password);
    }

    //costruttore utilizzato nella registrazione
    public Login (String email, String password, String username)
    {
        setEmail(email);
        setPassword(password);
        setUsername(username);
    }

    public void setEmail(String username)
    {
        this.email = username;
    }
    public String getEmail()
    {
        return this.email;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }
    public String getPassword()
    {
        return this.password;
    }

    public void setUsername(String username) {this.username = username;}
    public String getUsername() {return this.username;}
}
