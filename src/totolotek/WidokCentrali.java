package totolotek;

import wyjątki.ZłyIndeks;

// Klasa pomocnicza ograniczająca dostęp do centrali z widoku gracza.
public interface WidokCentrali {
    Kolektura dajKolekture(int i) throws ZłyIndeks;

    int dajLiczbeKolektur();

    int dajNumerOstatniegoLosowania();

    int dajNumerNajbliższegoLosowania();
}
