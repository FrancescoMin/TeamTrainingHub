package engineering.bean;

public class LoginBean
{
   private String password;
     private String username;

     public LoginBean(){}

     public LoginBean(String username, String password) {
         setUsername(username);
         setPassword(password);
     }

     public void setUsername(String username) {
          this.username = this.username;
     }
     public String getUsername() {
     return this.username;
     }

     public void setPassword(String password) {
         this.password = password;
     }
     public String getPassword() {
         return this.password;
     }

 }
