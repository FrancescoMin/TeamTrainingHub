package engineering.bean;

public class AllenamentoBean {
    private String data;
    private String orarioInizio;
    private String orarioFine;
    private String descrizione;


    public AllenamentoBean() {}


    public AllenamentoBean(String data, String orarioInizio, String orarioFine) {
        setData(data);
        setOrarioInizio(orarioInizio);
        setOrarioFine(orarioFine);
        setDescrizione("");
    }


    public AllenamentoBean(String data, String orarioInizio, String orarioFine, String descrizione) {
        setData(data);
        setOrarioInizio(orarioInizio);
        setOrarioFine(orarioFine);
        setDescrizione(descrizione);
    }

    public void setData(String data) {this.data = data;}
    public String getData() {return data;}


    public void setOrarioInizio(String orarioInizio) {this.orarioInizio = orarioInizio;}
    public String getOrarioInizio() {return orarioInizio;}


    public void setOrarioFine(String orarioFine) {this.orarioFine = orarioFine;}
    public String getOrarioFine() {return orarioFine;}

    public void setDescrizione(String descrizione) {this.descrizione = descrizione;}
    public String getDescrizione() {return descrizione;}

}
