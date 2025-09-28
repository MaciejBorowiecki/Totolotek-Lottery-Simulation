package testy;

import org.junit.jupiter.api.BeforeEach;
import totolotek.*;
import totolotek.gracze.*;
import org.junit.jupiter.api.Test;
import wyjątki.NiepoprawnyNumer;
import wyjątki.ZłyIndeks;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class CentralaTest {
    Centrala centrala;

    @BeforeEach
    public void setUp() throws ZłyIndeks, NiepoprawnyNumer {
        centrala = new Centrala();
    }

    @Test
    void testDane() throws ZłyIndeks {
        assertEquals(0, centrala.dajLiczbeKolektur());
        assertEquals(0, centrala.dajLiczbeKuponów());
        assertEquals(0, centrala.dajNumerOstatniegoLosowania());
        assertEquals(1, centrala.dajNumerNajbliższegoLosowania());
        assertEquals(new Pieniądze(), centrala.dajKumulacja());
        assertEquals(new Pieniądze(), centrala.dajBudżet());
        assertThrows(ZłyIndeks.class, () -> centrala.dajKolekture(1));
        assertThrows(ZłyIndeks.class, () -> centrala.dajLosowanie(1));
    }

    @Test
    void testUstaw() {
        centrala.dodajKolekture();
        assertEquals(1, centrala.dajLiczbeKolektur());
        Pieniądze nowe = new Pieniądze(2, 0);
        centrala.przyjmijPieniądze(nowe);
        assertEquals(nowe, centrala.dajBudżet());
        centrala.wydajPieniądze(nowe);
        assertEquals(new Pieniądze(), centrala.dajBudżet());
        centrala.przeprowadźLosowanie();
        assertEquals(2,centrala.dajNumerNajbliższegoLosowania());
        assertEquals(1,centrala.dajNumerOstatniegoLosowania());
        assertEquals(new Pieniądze(2_000_000,0), centrala.dajKumulacja());
    }
}