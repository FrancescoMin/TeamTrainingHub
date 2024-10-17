package engineering.bean;

public class LoginBean extends GenericoBean{

    private String password;
    private String email;
    protected boolean allenatore;

    public LoginBean(){}

    public LoginBean(String email, String password) {
        super(email,password);
        setPassword(password);
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

}
