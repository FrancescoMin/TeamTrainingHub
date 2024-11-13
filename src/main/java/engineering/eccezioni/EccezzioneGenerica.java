package engineering.eccezioni;

public class EccezzioneGenerica extends RuntimeException
{
    public EccezzioneGenerica(String message)
    {
        super(message);
    }
}
