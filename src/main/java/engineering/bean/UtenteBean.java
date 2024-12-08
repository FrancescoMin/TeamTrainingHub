package engineering.bean;

import modelli.Allenamento;
import modelli.Squadra;

import java.util.List;


public abstract class UtenteBean {

        private String username;
        private String email;
        private List<Allenamento> allenamenti;
        protected boolean allenatore ;
        private Squadra squadra;

        protected UtenteBean() {}

        protected UtenteBean(String username, String email, List<Allenamento> allenamenti, Squadra squadra) {
            setUsername(username);
            setEmail(email);
            setAllenamenti(allenamenti);
            setSquadra(squadra);
        }


        public void setEmail(String email) {
            //implementare una classe che si occupi di controllare che la mail sia formattata in modo corretto
            this.email = email;
        }
        public String getEmail() {
            return email;
        }


        public Squadra getSquadra() {return this.squadra;}
        public void setSquadra(Squadra squadra) {this.squadra = squadra;}

        public void setUsername(String nome) {
            this.username = nome;
        }
        public String getUsername() {
            return username;
        }


        public void setAllenamenti(List<Allenamento> allenamenti) {
            this.allenamenti = allenamenti;
        }
        public List<Allenamento> getAllenamenti() {
            return allenamenti;
        }

        public boolean getAllenatore() {
            return allenatore;
        }
}
