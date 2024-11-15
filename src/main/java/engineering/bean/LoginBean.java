package engineering.bean;

//Bean del Login

import java.util.List;

/*
public class LoginBean extends GenericoBean{

    private String password;
    private String email;
    private String username;
    private List<String> prenotazioni;
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
        return this.password;
    }

}
*/

public class LoginBean
{
   private String password;
     private String email;

     public LoginBean(){}

     public LoginBean(String email, String password) {
         setEmail(email);
         setPassword(password);
     }

     public void setEmail(String email) {
          this.email = email;
     }
     public String getEmail() {
     return this.email;
     }

     public void setPassword(String password) {
         this.password = password;
     }
     public String getPassword() {
         return this.password;
     }

 }
