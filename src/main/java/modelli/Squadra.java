package modelli;

import java.util.ArrayList;
import java.util.List;

public class Squadra {

    private String nome;
    private String allenatore;
    private List<Utente> richiesteIngresso;

    public Squadra() {
        setNome("");
        setAllenatore("");
        setRichiesteIngresso(new ArrayList<>());
    }

    public Squadra(String nome, String allenatore) {
        setNome(nome);
        setAllenatore(allenatore);
        setRichiesteIngresso(new ArrayList<>());
    }

    public Squadra(String nome, String allenatore, List<Utente> richiesteIngresso) {
        setNome(nome);
        setAllenatore(allenatore);
        setRichiesteIngresso(richiesteIngresso);
    }


    public String getNome() {return nome;}
    public void setNome(String nome){this.nome = nome;}

    public String getAllenatore() {return allenatore;}
    public void setAllenatore(String allenatore) {this.allenatore = allenatore;}

    public List<Utente> getRichiesteIngresso() {return richiesteIngresso;}
    public void setRichiesteIngresso(List<Utente> richiesteIngresso) {this.richiesteIngresso = richiesteIngresso;}

}
