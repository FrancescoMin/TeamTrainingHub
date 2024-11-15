package engineering.eccezioni;

public class UtenteNonEsistenteEccezione extends RuntimeException {
    public UtenteNonEsistenteEccezione(String message)
    {
        super(message);
    }
}
