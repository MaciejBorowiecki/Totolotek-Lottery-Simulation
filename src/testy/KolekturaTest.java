package testy;

import org.junit.jupiter.api.BeforeEach;
import totolotek.*;
import org.junit.jupiter.api.Test;
import wyjątki.ZłyIndeks;

import static org.junit.jupiter.api.Assertions.*;

public class KolekturaTest {
    Kolektura k;
    Centrala c;

    @BeforeEach
    public void setUp() throws ZłyIndeks {
        c = new Centrala();
        c.dodajKolekture();
        k = c.dajKolekture(1);
    }
    @Test
    void testDane() {
        assertEquals(1, k.dajNumer());
    }


    @Test
    void testToString() {
        String result = k.toString();
        assertNotNull(result);
        System.out.println("KolekturaTest ===============================================\n");
        System.out.println(k);
    }
}