package engineering.eccezioni;

public class EccezionePasswordErrata extends RuntimeException {
    public EccezionePasswordErrata(String message) {
        super(message);
    }
}
