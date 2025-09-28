package testy;

import org.junit.jupiter.api.BeforeEach;
import totolotek.*;
import totolotek.gracze.*;
import org.junit.jupiter.api.Test;
import wyjątki.NiepoprawnyNumer;
import wyjątki.ZłyIndeks;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class KuponTest {
    Kolektura kol;
    Centrala c;
    Kupon kup;
    Gracz g;

    @BeforeEach
    public void setUp() throws ZłyIndeks, NiepoprawnyNumer {
        c = new Centrala();
        c.dodajKolekture();
        kol = c.dajKolekture(1);
        g = new GraczMinimalista("a","b","123", new Pieniądze(100,0),
                1,c);
        g.kupKupon();
        Set<Kupon> kupony = g.dajKupony();

        // Wiemy, że w kuponach jest tylko jeden kupon.
        for(Kupon k : kupony){
            kup = k;
        }
    }
    @Test
    void testDane() throws ZłyIndeks {
        assertEquals(1,kup.dajNumerPorządkowy());
        assertEquals(1,kup.dajNumerKolektury());
        assertEquals(1,kup.dajNumerOstatniegoLosowania());
        assertEquals(1,kup.dajNumerPierwszegoLosowania());
        assertEquals(1,kup.dajLiczbeZakładów());
        assertNotNull(kup.dajZakład(1));
        assertThrows(ZłyIndeks.class, () -> kup.dajZakład(0));
        assertEquals(new Pieniądze(3,0), kup.dajCenę());
        assertEquals(new Pieniądze(0,60), kup.dajPodatek());
    }


    @Test
    void testToString() {
        String result = kup.toString();
        assertNotNull(result);
        System.out.println("KuponTest ===============================================\n");
        System.out.println(kup);
    }

}