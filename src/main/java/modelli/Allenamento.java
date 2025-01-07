package modelli;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Allenamento{
    @JsonProperty("data")
    private String data;
    @JsonProperty("durata")
    private int durata;
    @JsonProperty("descrizione")
    private String descrizione;


    public Allenamento() {}


    public Allenamento(String data, int durata) {
        setData(data);
        setDurata(durata);
        setDescrizione("");
    }


    public Allenamento(String data, int durata, String descrizione) {
        setData(data);
        setDurata(durata);
        setDescrizione(descrizione);
    }

    public void setData(String data) {
        this.data = data;
    }
    public String getData() {return data;}


    public void setDurata(int durata) {
        this.durata = durata;
    }
    public int getDurata() {
        return this.durata;
    }


    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }
    public String getDescrizione() {
        return this.descrizione;
    }
}