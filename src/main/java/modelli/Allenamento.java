package modelli;

public class Allenamento{
    private String data;
    private int durata;
    private String descrizione;


    public Allenamento() {}

    public Allenamento(String data, int durata) {
        setData(data);
        setDurata(durata);
        descrizione="";
    }


    public void setData(String data) {
        this.data = data;
    }
    public String getData() {
        return data;
    }


    public void setDurata(int durata) {
        this.durata = durata;
    }
    public int getDurata() {
        return durata;
    }


    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }
    public String getDescrizione() {
        return descrizione;
    }
}