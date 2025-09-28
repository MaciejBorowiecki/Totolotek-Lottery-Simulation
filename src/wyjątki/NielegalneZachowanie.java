package wyjątki;

public class NielegalneZachowanie extends Exception {
    public NielegalneZachowanie(String message) {
        super(message);
    }
}
