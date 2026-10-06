package modelli;

import java.util.Objects;

public class Allenamento{

    private String data;
    private String descrizione;
    private String orarioInizio;
    private String orarioFine;


    public Allenamento() {}

    public Allenamento(String data, String orarioInizio, String orarioFine) {
        setData(data);
        setOrarioInizio(orarioInizio);
        setOrarioFine(orarioFine);
        setDescrizione("");
    }

    public Allenamento(String data, String orarioInizio, String orarioFine, String descrizione) {
        setData(data);
        setOrarioInizio(orarioInizio);
        setOrarioFine(orarioFine);
        setDescrizione(descrizione);
    }


    public void setData(String data) {this.data = data;}
    public String getData() {return data;}

    public void setDescrizione(String descrizione) {this.descrizione = descrizione;}
    public String getDescrizione() {return this.descrizione;}

    public void setOrarioInizio(String orarioInizio) {this.orarioInizio = orarioInizio;}
    public String getOrarioInizio() {return this.orarioInizio;}

    public void setOrarioFine(String orarioFine) {this.orarioFine = orarioFine;}
    public String getOrarioFine() {return this.orarioFine;}

    // Aggiungere in fondo a Allenamento.java:
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Allenamento that = (Allenamento) o;
        return Objects.equals(data, that.data) &&
                Objects.equals(orarioInizio, that.orarioInizio) &&
                Objects.equals(orarioFine, that.orarioFine);
    }

    @Override
    public int hashCode() {
        return Objects.hash(data, orarioInizio, orarioFine);
    }
}
