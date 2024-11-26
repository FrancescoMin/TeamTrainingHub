package engineering.bean;

public class LoginBean
{
    private String password;
    private String email;

    public LoginBean(){}

    //costruttore utilizzano nel login
    public LoginBean(String email, String password) {
        setEmail(email);
        setPassword(password);
    }


    public void setEmail(String email) {this.email = email;}
    public String getEmail() {return this.email;}

    public void setPassword(String password) {this.password = password;}
    public String getPassword() {return this.password;}

}
