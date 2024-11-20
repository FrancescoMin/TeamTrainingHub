package engineering.bean;

import modelli.Allenamento;
import modelli.Squadra;

import java.util.List;


public abstract class GenericoBean {

        private String username;
        private String email;
        private List<Allenamento> allenamenti;
        protected boolean allenatore ;
        private List<Squadra> squadre;

        protected GenericoBean() {}

        protected GenericoBean(String username, String email, List<Allenamento> allenamenti, List<Squadra> squadre) {
            setUsername(username);
            setEmail(email);
            setAllenamenti(allenamenti);
            setSquadre(squadre);
        }


        public void setEmail(String email) {
            //implementare una classe che si occupi di controllare che la mail sia formattata in modo corretto
            this.email = email;
        }
        public String getEmail() {
            return email;
        }


        public List<Squadra> getSquadre() {return squadre;}
        public void setSquadre(List<Squadra> squadre) {this.squadre = squadre;}

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
