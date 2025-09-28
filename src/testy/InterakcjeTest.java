package testy;

import org.junit.jupiter.api.BeforeEach;
import totolotek.*;
import totolotek.gracze.*;
import org.junit.jupiter.api.Test;
import wyjątki.NielegalneZachowanie;
import wyjątki.NiepoprawnyNumer;
import wyjątki.ZłyIndeks;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class InterakcjeTest {
    Kolektura kol;
    Centrala c;
    Gracz g;

    @BeforeEach
    public void setUp() throws ZłyIndeks, NiepoprawnyNumer {
        c = new Centrala();
        c.dodajKolekture();
        kol = c.dajKolekture(1);
        TreeSet<Integer> ulubioneLiczby = new TreeSet<>();
        ulubioneLiczby.add(1);
        ulubioneLiczby.add(2);
        ulubioneLiczby.add(3);
        ulubioneLiczby.add(4);
        ulubioneLiczby.add(5);
        ulubioneLiczby.add(6);
        ArrayList<Integer> ulubioneKolektury = new ArrayList<>();
        ulubioneKolektury.add(1);
        Pieniądze p = new Pieniądze(120,0);
        g = new GraczStałoliczbowy("stało","liczbowy","3",p,
                ulubioneLiczby,c,ulubioneKolektury);
    }
    @Test
    void testPoprawnychInterakcji() throws ZłyIndeks, NiepoprawnyNumer {
        // Kupowanie kuponu
        g.kupKupon();
        assertNotNull(g.dajKupony());
        assertEquals(kol,c.dajKolekture(1));
        // Ustawione Losowanie, ze znanymi liczbami.
        c.przeprowadźFałszyweLosowanie();
        Losowanie l = c.dajOstatnieLosowanie();
        assertNotNull(l);

        // pulaNagród jest policzona ręcznie według schematu z treści zadania.
        Pieniądze[] pulaNagrod = new Pieniądze[4];
        pulaNagrod[0] = new Pieniądze(2_000_000,0);
        pulaNagrod[1] = new Pieniądze(0,9);
        pulaNagrod[2] = new Pieniądze(0,60);
        pulaNagrod[3] = new Pieniądze(0,0);

        assertArrayEquals(pulaNagrod, l.dajPuleNagród());

        // Jest tylko 1 zwycięzca - 6-o cyfrowy.
        assertEquals(new Pieniądze(2_000_000,0), l.dajNagrodaKStopnia(1));
        assertEquals(new Pieniądze(), l.dajNagrodaKStopnia(2));
        assertEquals(new Pieniądze(), l.dajNagrodaKStopnia(3));
        assertEquals(new Pieniądze(), l.dajNagrodaKStopnia(4));
        assertEquals(new Pieniądze(), c.dajKumulacja());

        // Testowanie odbierania nagrody i płacenia podatku.
        g.odbierzPozostałeKupony();
        assertEquals(new Pieniądze(1800090,0), g.dajIlośćPieniędzy());
        assertEquals(new Pieniądze(), c.dajBudżet());

        BudżetPaństwa bż = BudżetPaństwa.dajInstancje();
        assertEquals(new Pieniądze(200007,20),bż.pobranePodatki());
        assertEquals(new Pieniądze(1999976,0),bż.wydaneSubwencje());
    }


    @Test
    void testNiepoprawnychInterakcji() throws ZłyIndeks, NiepoprawnyNumer, NielegalneZachowanie {
        // Gracz próbuje kupić kupon bez wystarczających środków.
        g.przyjmijPieniądze(new Pieniądze(-120,0));
        assertEquals(new Pieniądze(), g.dajIlośćPieniędzy());

        g.kupKupon();
        assertEquals(new HashSet<Kupon>(), g.dajKupony());

        // Gracz próbuje odebrać kupon w nieswojej kolekturze
        // Resetujemy pieniądze gracza.
        g.przyjmijPieniądze(new Pieniądze(120,0));
        g.kupKupon();
        assertFalse(g.dajKupony().isEmpty());
        Kupon k = g.dajKupony().iterator().next();
        c.dodajKolekture();
        assertThrows(NielegalneZachowanie.class, () -> c.dajKolekture(2).wydajNagrodę(g,k,1));

        // Gracz próbuje odebrać kupon dwukrotnie
        c.przeprowadźFałszyweLosowanie();
        Kolektura k1 = c.dajKolekture(1);
        k1.wydajNagrodę(g,k,1);
        assertThrows(NielegalneZachowanie.class, () -> k1.wydajNagrodę(g,k,1));

    }

}