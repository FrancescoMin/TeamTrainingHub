package engineering.eccezioni;

public class PasswordErrataEccezione extends RuntimeException {
    public PasswordErrataEccezione(String message) {
        super(message);
    }
}
