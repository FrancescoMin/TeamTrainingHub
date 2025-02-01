package engineering.eccezioni;

public class EccezioneUtenteInvalido extends RuntimeException {
    public EccezioneUtenteInvalido(String message) {
        super(message);
    }
}
