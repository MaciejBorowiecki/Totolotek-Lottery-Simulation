package wyjątki;

public class NiepoprawnyNumer extends Exception{
    public NiepoprawnyNumer(){super("Podano niepoprawny numer.");}

    public NiepoprawnyNumer(String message) {
        super(message);
    }
}
