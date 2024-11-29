package modelli;

public class Login {

    private String email;
    private String password;

    public Login(){}

    //costruttore utilizzato nel login
    public Login(String email, String password)
    {
        setEmail(email);
        setPassword(password);
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

}
