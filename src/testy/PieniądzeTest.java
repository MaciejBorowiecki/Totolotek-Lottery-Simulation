package testy;

import totolotek.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PieniądzeTest {

    @Test
    void testDane() {
        Pieniądze p = new Pieniądze(5, 75);
        assertEquals(5, p.dajZłotówki());
        assertEquals(75, p.dajGrosze());
    }

    @Test
    void testDodawanieIOdejmowanie() {
        Pieniądze p1 = new Pieniądze(10, 0);
        Pieniądze p2 = new Pieniądze(5, 50);
        p1.dodaj(p2);
        assertEquals(new Pieniądze(15, 50), p1);

        p1.odejmij(new Pieniądze(2, 50));
        assertEquals(new Pieniądze(13, 0), p1);
    }

    // W tym teście podatek ogranicza się do podatku przy zakupie kuponu (20%).
    @Test
    void testPodatekMnożenieDzielenie() {
        Pieniądze p = new Pieniądze(10, 0);
        assertEquals(new Pieniądze(2, 0), p.obliczPodatek());
        assertEquals(new Pieniądze(5, 0), p.dzielenie(2));
        assertEquals(new Pieniądze(30, 0), p.mnożenie(3));
    }

    @Test
    void testRówne() {
        Pieniądze p1 = new Pieniądze(7, 25);
        Pieniądze p2 = new Pieniądze(725);
        assertEquals(p1, p2);
        assertEquals(0, p1.compareTo(p2));
        assertEquals(p1.hashCode(), p2.hashCode());
    }

    @Test
    void testToString() {
        Pieniądze p = new Pieniądze(3, 45);
        assertEquals("3 zł 45 gr", p.toString());
        System.out.println("PieniądzeTest ===============================================\n");
        System.out.println(p);
    }
}
