package testy;

import totolotek.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import wyjątki.NiepoprawnyNumer;

import java.util.TreeSet;

import static org.junit.jupiter.api.Assertions.*;

public class ZakładTest {
    private Zakład zakład;

    @BeforeEach
    void setUp() {
        zakład = new Zakład();
    }

    @Test
    void testZaznaczNumerPoprawny() throws NiepoprawnyNumer {
        zakład.zaznaczNumer(5);
        assertTrue(zakład.dajLiczby().contains(5));
    }

    @Test
    void testZaznaczNumerNiepoprawny() {
        assertThrows(NiepoprawnyNumer.class, () -> zakład.zaznaczNumer(0));
        assertThrows(NiepoprawnyNumer.class, () -> zakład.zaznaczNumer(Stałe.wszystkieNumeryZakładu + 1));
    }

    @Test
    void testCzyWażnyZakład() throws NiepoprawnyNumer {
        for (int i = 1; i <= Stałe.poprawnaIlośćZaznaczonych; i++) {
            zakład.zaznaczNumer(i);
        }
        assertTrue(zakład.czyWażny());
    }

    @Test
    void testCzyWażnyZbytMałoNumerów() throws NiepoprawnyNumer {
        zakład.zaznaczNumer(1);
        assertFalse(zakład.czyWażny());
    }

    @Test
    void testAnulujZakład() {
        zakład.anuluj();
        assertFalse(zakład.czyWażny());
    }

    @Test
    void testLosowyZakład() {
        zakład.losowyZakład();
        TreeSet<Integer> liczby = zakład.dajLiczby();
        assertEquals(Stałe.poprawnaIlośćZaznaczonych, liczby.size());
    }

    @Test
    void testToString() {
        String result = zakład.toString();
        assertNotNull(result);
        System.out.println("ZakładTest ===============================================\n");
        System.out.println(zakład);
    }
}