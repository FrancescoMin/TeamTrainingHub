package engineering.bean;

import java.util.List;


public abstract class GenericoBean {

        private String username;
        private String email;
        private List<String> prenotazioni;
        protected boolean allenatore ;

        protected GenericoBean() {}

        protected GenericoBean(String username, String email) {
            setUsername(username);
            setEmail(email);
            this.allenatore = false;
            this.prenotazioni = null;
        }


        public void setEmail(String email) {
            //implementare una classe che si occupi di controllare che la mail sia formattata in modo corretto
            this.email = email;
        }
        public String getEmail() {
            return email;
        }

        public void setUsername(String nome) {
            this.username = nome;
        }
        public String getUsername() {
            return username;
        }


        public void setPrenotazioni(List<String> prenotazioni) {
            this.prenotazioni = prenotazioni;
        }
        public List<String> getPrenotazioni() {
            return prenotazioni;
        }

        public boolean getAllenatore() {
            return allenatore;
        }
}
