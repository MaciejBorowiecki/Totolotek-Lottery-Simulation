package testy;

import totolotek.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import wyjątki.NiepoprawnyNumer;
import wyjątki.ZłyIndeks;

import static org.junit.jupiter.api.Assertions.*;

class BlankietTest {
    private Blankiet blankiet;

    @BeforeEach
    void setUp() {
        blankiet = new Blankiet(3);
    }

    @Test
    void testDane() throws ZłyIndeks {
        assertEquals(3, blankiet.dajLiczbeLosowań());
        Zakład z = blankiet.dajZakład(1);
        assertNotNull(z);
    }

    @Test
    void testDajZakładZłyIndeks() {
        assertThrows(ZłyIndeks.class, () -> blankiet.dajZakład(0));
        assertThrows(ZłyIndeks.class, () -> blankiet.dajZakład(Stałe.maxIlośćZakładówNaBlankiecie + 1));
    }

    @Test
    void testZaznacz() throws NiepoprawnyNumer, ZłyIndeks {
        blankiet.zaznaczNumer(1, 5);
        assertTrue(blankiet.dajZakład(1).dajLiczby().contains(5));
        assertThrows(NiepoprawnyNumer.class, () -> blankiet.zaznaczNumer(0, 5));
        blankiet.zaznaczLiczbeLosowań(5);
        assertEquals(5, blankiet.dajLiczbeLosowań());
        blankiet.anulujZakład(1);
        assertFalse(blankiet.dajZakład(1).czyWażny());
    }

    @Test
    void testLosowyBlankietKonstruktor() {
        Blankiet b = new Blankiet(2, 5);
        assertEquals(2, b.dajLiczbeLosowań());
    }

    @Test
    void testToString() {
        String result = blankiet.toString();
        assertNotNull(result);
        System.out.println("BlankietTest ===============================================\n");
        System.out.println(blankiet);
    }
}