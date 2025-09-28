package testy;

import org.junit.jupiter.api.BeforeEach;
import totolotek.*;
import totolotek.gracze.*;
import org.junit.jupiter.api.Test;
import wyjątki.NiepoprawnyNumer;
import wyjątki.ZłyIndeks;

import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;

import static org.junit.jupiter.api.Assertions.*;

public class GraczTest {
    GraczMinimalista minimalista;
    GraczLosowy losowy;
    GraczStałoliczbowy stałoliczbowy;
    GraczStałoBlankietowy stałoBlankietowy;
    Centrala c;

    @BeforeEach
    public void setUp() throws ZłyIndeks, NiepoprawnyNumer {
        Pieniądze p = new Pieniądze(120, 0);
        c = new Centrala();
        c.dodajKolekture();
        TreeSet<Integer> ulubioneLiczby = new TreeSet<>();
        ulubioneLiczby.add(1);
        ulubioneLiczby.add(2);
        ulubioneLiczby.add(3);
        ulubioneLiczby.add(4);
        ulubioneLiczby.add(5);
        ulubioneLiczby.add(6);
        ArrayList<Integer> ulubioneKolektury = new ArrayList<>();
        ulubioneKolektury.add(1);
        Blankiet b = new Blankiet(1,1); // losowy blankiet

        minimalista = new GraczMinimalista("mini","malista", "1", p, 1,c);
        losowy = new GraczLosowy("lo","sowy", "2",c);
        stałoliczbowy = new GraczStałoliczbowy("stało","liczbowy","3",p,
                ulubioneLiczby,c,ulubioneKolektury);
        stałoBlankietowy = new GraczStałoBlankietowy("stało","blankietowy","4",p,b,
                c,ulubioneKolektury,2);
    }

    @Test
    void testDaneMinimalista() throws ZłyIndeks {
        assertEquals("mini", minimalista.dajImie());
        assertEquals("malista", minimalista.dajNazwisko());
        assertEquals(new Pieniądze(120,0), minimalista.dajIlośćPieniędzy());
        assertEquals("1", minimalista.dajPesel());
        assertFalse(minimalista.czyMaKupon());
    }

    @Test
    void testDaneLosowy() {
        assertEquals("lo", losowy.dajImie());
        assertEquals("sowy", losowy.dajNazwisko());
        assertEquals("2", losowy.dajPesel());
        assertNotNull(losowy.dajIlośćPieniędzy());
        assertFalse(losowy.czyMaKupon());
    }

    @Test
    void testDaneStałoliczbowy() {
        assertEquals("stało", stałoliczbowy.dajImie());
        assertEquals("liczbowy", stałoliczbowy.dajNazwisko());
        assertEquals("3", stałoliczbowy.dajPesel());
        assertEquals(new Pieniądze(120,0), stałoliczbowy.dajIlośćPieniędzy());
        assertFalse(stałoliczbowy.czyMaKupon());
    }

    @Test
    void testDaneStałoBlankietowy() {
        assertEquals("stało", stałoBlankietowy.dajImie());
        assertEquals("blankietowy", stałoBlankietowy.dajNazwisko());
        assertEquals("4", stałoBlankietowy.dajPesel());
        assertEquals(new Pieniądze(120,0), stałoBlankietowy.dajIlośćPieniędzy());
        assertFalse(stałoBlankietowy.czyMaKupon());
    }

    @Test
    void testString(){
        assertNotNull(minimalista.toString());
        assertNotNull(stałoliczbowy.toString());
        assertNotNull(losowy.toString());
        assertNotNull(stałoBlankietowy.toString());
        System.out.println("GraczeTest ===============================================\n");
        System.out.println(minimalista);
        System.out.println(losowy);
        System.out.println(stałoliczbowy);
        System.out.println(stałoBlankietowy);
    }
}