package engineering.bean;

public class AllenamentoBean {
    private String data;
    private int durata;
    private String descrizione;


    public AllenamentoBean() {}


    public AllenamentoBean(String data, int durata) {
        setData(data);
        setDurata(durata);
        setDescrizione("");
    }


    public AllenamentoBean(String data, int durata, String descrizione) {
        setData(data);
        setDurata(durata);
        setDescrizione(descrizione);
    }

    public void setData(String data) {this.data = data;}
    public String getData() {return data;}


    public void setDurata(int durata) {this.durata = durata;}
    public int getDurata() {return durata;}


    public void setDescrizione(String descrizione) {this.descrizione = descrizione;}
    public String getDescrizione() {return descrizione;}

}