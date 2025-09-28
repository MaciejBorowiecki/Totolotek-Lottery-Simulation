package testy;

import org.junit.jupiter.api.BeforeEach;
import totolotek.*;
import totolotek.gracze.*;
import org.junit.jupiter.api.Test;
import wyjątki.NiepoprawnyNumer;
import wyjątki.ZłyIndeks;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class LosowanieTest {
    Losowanie losowanie;

    @BeforeEach
    public void setUp() throws ZłyIndeks, NiepoprawnyNumer {
        losowanie = new Losowanie(1);
    }
    @Test
    void testDane() throws ZłyIndeks {
        assertEquals(1,losowanie.dajNumer());
        assertNotNull(losowanie.dajWylosowaneLiczby());
        assertNull(losowanie.dajTrafienia());
    }

    @Test
    void ustawIDajUstawioneTest(){
        int[] trafienia = new int[6];
        for(int i = 0; i < 6; i++) {
            trafienia[i] = i;
        }
        losowanie.ustawTrafienia(trafienia);
        assertNotNull(losowanie.dajTrafienia());
        assertEquals(trafienia, losowanie.dajTrafienia());
        Pieniądze[] pulaNagród = new Pieniądze[4];
        for(int i = 0; i < 4; i++) {
            pulaNagród[i] = new Pieniądze(i,0);
        }
        losowanie.ustawPuleNagród(pulaNagród);
        assertNotNull(losowanie.dajPuleNagród());
        assertEquals(pulaNagród, losowanie.dajPuleNagród());
    }


    @Test
    void testToString() {
        System.out.println("LosowanieTest ===============================================\n");
        int[] trafienia = new int[6];
        for(int i = 0; i < 6; i++) {
            trafienia[i] = i;
        }
        losowanie.ustawTrafienia(trafienia);
        Pieniądze[] pulaNagród = new Pieniądze[4];
        for(int i = 0; i < 4; i++) {
            pulaNagród[i] = new Pieniądze(i,0);
        }
        losowanie.ustawPuleNagród(pulaNagród);
        assertNotNull(losowanie.toString());
        System.out.println(losowanie);
    }

}