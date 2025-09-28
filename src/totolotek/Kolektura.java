package totolotek;

import totolotek.gracze.Gracz;
import wyjątki.NielegalneZachowanie;
import wyjątki.ZłyIndeks;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

public class Kolektura {
    // ============================ Atrybuty ==============================

    private final Centrala centrala;
    private final int numer;
    private final Set<Kupon> nieodebraneKupony;
    private final Set<Kupon> odebraneKupony;

    // ========================== Konstruktor ============================

    protected Kolektura(Centrala centrala, int numer) {
        this.centrala = centrala;
        this.numer = numer;
        this.nieodebraneKupony = new HashSet<>();
        this.odebraneKupony = new HashSet<>();
    }

    // ======================= Gettery do atrybutów =======================

    public int dajNumer() {
        return numer;
    }

    // ========================= Metody pomocnicze ========================

    // Kolektura sprzedaje kupon oparty o losowo wygenerowany blankiet,
    // z wybraną liczbą zakładów i losowań.
    public void sprzedajKuponChybiłTrafił(Gracz gracz, int liczbaLosowań,
                                          int liczbaZakładów) throws ZłyIndeks {
        Blankiet blankiet = new Blankiet(liczbaLosowań, liczbaZakładów);
        this.sprzedajKupon(gracz, blankiet);
    }

    // Metoda sprzedająca Kupon na podstawie Blankietu.
    public void sprzedajKupon(Gracz gracz, Blankiet blankiet) throws ZłyIndeks {
        Kupon k = new Kupon(centrala.dajLiczbeKuponów() + 1, blankiet,
                this.numer, centrala.dajNumerNajbliższegoLosowania());

        // Kupon jest sprzedawany, jedynie jeżeli gracza na niego stać.
        if (gracz.dajIlośćPieniędzy().czyWiększeRówne(k.dajCenę())) {
            gracz.weźKupon(k);  // Gracz odbiera kupon.

            // kupon trafia na liste kuponów, z których nie została odebrana
            // nagroda.
            nieodebraneKupony.add(k);

            // Centrala przyjmuje zysk z kuponu i odprowadza podatek.
            centrala.przyjmijPieniądze(k.dajCenę());
            centrala.zapłaćPodatek(k.dajPodatek());
            centrala.dodajKupon();
        }
    }

    // Metoda licząca sumę pieniędzy zarobionych z zakładów postawionych na
    // konkretne losowanie.
    public Pieniądze sumaPostawionychPieniędzy(int nrLosowania) {
        Pieniądze suma = new Pieniądze();

        // Istotne są zarówno odebrane jak i nieodebrane kupony.
        for (Kupon k : nieodebraneKupony) {
            if (k.dajNumerOstatniegoLosowania() >= nrLosowania
                    && k.dajNumerPierwszegoLosowania() <= nrLosowania) {
                suma.dodaj(k.dajCenę().dzielenie(k.dajLiczbeLosowań()));
            }
        }
        for (Kupon k : odebraneKupony) {
            if (k.dajNumerOstatniegoLosowania() >= nrLosowania
                    && k.dajNumerPierwszegoLosowania() <= nrLosowania) {
                suma.dodaj(k.dajCenę().dzielenie(k.dajLiczbeLosowań()));
            }
        }
        return suma;
    }

    // Metoda ile trafień numerów znajduje się na kuponach.
    public void ileTrafień(Losowanie l, int[] trafienia) {
        // Istotne są zarówno odebrane jak i nieodebrane kupony
        for (Kupon k : nieodebraneKupony) {
            if (k.dajNumerOstatniegoLosowania() >= l.dajNumer()
                    && k.dajNumerPierwszegoLosowania() <= l.dajNumer()) {
                k.ileTrafień(trafienia, l);
            }
        }
        for (Kupon k : odebraneKupony) {
            if (k.dajNumerOstatniegoLosowania() >= l.dajNumer()
                    && k.dajNumerPierwszegoLosowania() <= l.dajNumer()) {
                k.ileTrafień(trafienia, l);
            }
        }
    }

    // Metoda pomocnicza licząca ilość trafionych numerów z pojedynczego zakładu
    // potrzebna do wyznaczania odpowiedniej wygranej za kupon.
    private int ileTrafień(Zakład z, TreeSet<Integer> wygrywająceLiczby) {
        int count = 0;
        for (int i : z.dajLiczby()) {
            if (wygrywająceLiczby.contains(i)) {
                count++;
            }
        }
        return count;
    }

    // Metoda wydająca nagrodę graczowi za okazany kuopn.
    public void wydajNagrodę(Gracz gracz, Kupon kupon, int nrLosowania)
            throws ZłyIndeks, NielegalneZachowanie {
        // Kupon możemy wydać jedynie jeżeli nie został on wcześniej odebrany.
        if (nieodebraneKupony.contains(kupon)) {
            // Kupon z bazy nieodebranych trafia do odebranych.
            nieodebraneKupony.remove(kupon);
            odebraneKupony.add(kupon);

            // Obliczanie nagród przypadających dla każðego zakładu i losowania z kuponu.
            for (int i = kupon.dajNumerPierwszegoLosowania(); i <= nrLosowania; i++) {
                Losowanie losowanie = centrala.dajLosowanie(i);
                TreeSet<Integer> wygrywająceLiczby = losowanie.dajWylosowaneLiczby();
                for (int j = 1; j <= kupon.dajLiczbeZakładów(); j++) {
                    int liczbaTrafień = ileTrafień(kupon.dajZakład(j),
                            wygrywająceLiczby);
                    if (liczbaTrafień >= 3) {
                        Pieniądze wygrana = losowanie.dajNagrodaKStopnia(
                                Stałe.poprawnaIlośćZaznaczonych - liczbaTrafień + 1);

                        // Odprowadzanie podatku przy dużych wygranych.
                        if (wygrana.czyWiększeRówne(new Pieniądze(2280, 0))) {
                            Pieniądze podatek = wygrana.dzielenie(10);
                            wygrana.odejmij(podatek);
                            centrala.zapłaćPodatek(podatek);
                        }
                        gracz.przyjmijPieniądze(centrala.wydajPieniądze(wygrana));
                    }
                }
            }
        } else if(odebraneKupony.contains(kupon)) {
            throw new NielegalneZachowanie("Nie można 2 razy odebrać tego samego" +
                    "kuponu.");
        } else{
            throw new NielegalneZachowanie("Kupon nie został zakupiony w tej" +
                    "kolekturze.");
        }
    }

    // ============================ toString ==============================
    @Override
    public String toString() {
        return "Kolektura nr: " + numer + "\nLiczba przechowywanych kuponów: " +
                (odebraneKupony.size() + nieodebraneKupony.size());
    }
}
