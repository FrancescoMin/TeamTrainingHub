package modelli;

public class Allenamento{
    private String data;
    private String durata;
    private String descrizione;


    //dettagli implementativi dell'allenamento

    public Allenamento() {}

    public Allenamento(String data, String durata) {
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


    public void setDurata(String durata) {
        this.durata = durata;
    }
    public String getDurata() {
        return durata;
    }


    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }
    public String getDescrizione() {
        return descrizione;
    }
}