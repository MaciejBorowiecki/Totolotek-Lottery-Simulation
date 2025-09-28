package totolotek;

import totolotek.gracze.*;

import java.util.*;

public class Prezentacja {
    private static final int ileLosowań = 20;
    private static final int ilośćKolektur = 10;
    private static final int ilośćGraczyKażdegoRodzaju = 200;

    // Metoda pomocnicza do tworzenia graczy.
    private static TreeSet<Integer> losujUlubioneLiczby(){
        TreeSet<Integer> wynik = new TreeSet<>();
        Random rand = new Random();
        while(wynik.size() != Stałe.poprawnaIlośćZaznaczonych){
            wynik.add(rand.nextInt(Stałe.wszystkieNumeryZakładu) + 1);
        }
        return wynik;
    }

    // Metoda pomocnicza do tworzenia graczy.
    private static ArrayList<Integer> losujUlubioneKolektury(){
        ArrayList<Integer> wynik = new ArrayList<>();
        Random rand = new Random();
        wynik.add(rand.nextInt(ilośćKolektur)+1);
        for(int i = 1; i <= ilośćKolektur; i++){
            if(i != wynik.get(0) && rand.nextBoolean()){
                wynik.add(i);
            }
        }
        return wynik;
    }

    // Główna metoda prezentująca działąnia Totolotka.
    public static void main(String[] args) throws Exception {
        Centrala centrala = new Centrala(); // Utworzenie centrali

        Random rand = new Random();

        // Przydzielenie stałych losowych kwot mniejszych od miliona reprezentujących
        // początkowe ilości pieniędzy poszczególnych rodzajów graczy.
        Pieniądze kwotaMinimalista = new Pieniądze(rand.nextLong(1_000_000), 0);
        Pieniądze kwotaStałoliczbowy = new Pieniądze(rand.nextLong(1_000_000), 0);
        Pieniądze kwotaStałoBlankietowy = new Pieniądze(rand.nextLong(1_000_000), 0);

        // Tworzenie graczy.
        ArrayList<Gracz> gracze = new ArrayList<>(); // Lista z wszystkimi graczami.
        for (int i = 0; i < ilośćGraczyKażdegoRodzaju; i++) {
            gracze.add(new GraczLosowy("Jan", "Niejan", "1234567810",
                    centrala));
            gracze.add(new GraczMinimalista("Adam", "Ewa", "1234567810",
                    kwotaMinimalista,rand.nextInt(ilośćKolektur) + 1, centrala));
            gracze.add(new GraczStałoliczbowy("Grzegorz", "Krych","1234567810",
                    kwotaStałoliczbowy, losujUlubioneLiczby(), centrala, losujUlubioneKolektury()));
            gracze.add(new GraczStałoBlankietowy("Blanka","Stal", "1234567810",
                    kwotaStałoBlankietowy, new Blankiet(rand.nextInt(
                            Stałe.maxilośćLosowańNaBlankiecie) + 1,rand.nextInt(8)+ 1),
                    centrala, losujUlubioneKolektury(),rand.nextInt(14)+1));
        }

        // Tworzenie kolektur.
        for (int i = 0; i < ilośćKolektur; i++) {
            centrala.dodajKolekture();
        }

        // Pętla symulująca losowanie.
        for (int i = 0; i < ileLosowań; i++) {
            // Przed każdym losowaniem gracze kupują kupony według swojej rutyny.
            for (Gracz gracz : gracze) {
                gracz.noweLosowanie();
            }
            // Centrala przeprowadza losowanie.
            centrala.przeprowadźLosowanie();
            // Po każdym losowaniu gracze odbierają kupony kończące się w tym
            // losowaniu.
            for (Gracz gracz : gracze) {
                gracz.odbierzSkończoneKupony();
            }
            System.out.println(centrala.dajOstatnieLosowanie());
            System.out.println("\n");
        }
        // Po wszystkich losowaniach gracze odbierają kupony, które jeszcze
        // się nie skończyły, a są nieodebrane.
        for (Gracz gracz : gracze) {
            gracz.odbierzPozostałeKupony();
        }

        // Wypisywanie podstawowych informacji po wszystkich losowaniach.
        System.out.println("Budżet centrali: " + centrala.dajBudżet());
        System.out.println("Sprzedana liczba kuponów: " +
                centrala.dajLiczbeKuponów());
        System.out.println("Budżet Państwa:\n" + BudżetPaństwa.dajInstancje());

    }
}
