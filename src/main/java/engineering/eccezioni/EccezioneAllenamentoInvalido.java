package engineering.eccezioni;

public class EccezioneAllenamentoInvalido extends RuntimeException {
    public EccezioneAllenamentoInvalido(String message) {
        super(message);
    }
}
