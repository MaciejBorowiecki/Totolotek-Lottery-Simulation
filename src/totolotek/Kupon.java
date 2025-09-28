package totolotek;

import wyjątki.ZłyIndeks;

import java.util.ArrayList;
import java.util.Random;
import java.util.TreeSet;

public class Kupon {
    // ============================ Atrybuty ==============================

    private final int numerPorządkowy;
    private final int numerKolektury;
    private final long losowyZnacznik;
    private final int sumaKontrolna;
    private final int liczbaLosowań;
    private final Pieniądze cena;
    private final int pierwszeLosowanie;
    private final ArrayList<Zakład> zakłady;

    // ========================== Konstruktor =============================

    protected Kupon(int numerPorządkowy, Blankiet blankiet, int numerKolektury,
                 int pierwszeLosowanie) throws ZłyIndeks {
        this.numerPorządkowy = numerPorządkowy;
        this.numerKolektury = numerKolektury;
        this.zakłady = new ArrayList<>();
        dodajZakłady(blankiet);
        this.liczbaLosowań = blankiet.dajLiczbeLosowań();
        this.losowyZnacznik = stwórzLosowyZnacznik();
        this.cena = obliczCene();
        this.sumaKontrolna = obliczSumeKontrolna();
        this.pierwszeLosowanie = pierwszeLosowanie;
    }

    // ===================== Gettery do atrybutów =========================

    public Pieniądze dajCenę() {
        return this.cena;
    }

    public int dajNumerPorządkowy(){
        return this.numerPorządkowy;
    }

    public Pieniądze dajPodatek() {
        return this.cena.obliczPodatek();
    }

    public String dajIdentyfikator() {
        return numerPorządkowy + "-" + numerKolektury + "-" +
                losowyZnacznik + "-" + sumaKontrolna;
    }

    public int dajLiczbeZakładów() {
        return zakłady.size();
    }

    public int dajLiczbeLosowań() {
        return liczbaLosowań;
    }

    public int dajNumerKolektury(){
        return numerKolektury;
    }

    public Zakład dajZakład(int i) throws ZłyIndeks {
        if (i < 1 || i > this.dajLiczbeZakładów()) {
            throw new ZłyIndeks("Oczekiwano indeksu od 1 do "
                    + this.dajLiczbeZakładów() + " a otrzymano: " + i);
        }
        return this.zakłady.get(i - 1);
    }

    public int dajNumerOstatniegoLosowania() {
        return pierwszeLosowanie + liczbaLosowań - 1;
    }

    public int dajNumerPierwszegoLosowania(){
        return pierwszeLosowanie;
    }

    // =========================== Metody pomocnicze ==========================

    private long stwórzLosowyZnacznik() {
        Random rand = new Random();
        return rand.nextLong(900_000_000) + 100_000_000;
    }

    // Metoda pomocnicza przepisująca zakłady z blankietu na kupon.
    private void dodajZakłady(Blankiet blankiet) throws ZłyIndeks {
        for (int i = 1; i <= Stałe.maxIlośćZakładówNaBlankiecie; i++) {
            if (blankiet.dajZakład(i).czyWażny()) {
                zakłady.add(blankiet.dajZakład(i));
            }
        }
    }

    // Oblicza cene brutto Kuponu.
    private Pieniądze obliczCene() {
        return new Pieniądze((long) liczbaLosowań * zakłady.size() * 3, 0);
    }

    // Sprawdza ile trafień wylosowanych liczb znajduej się na posczególnych
    // zakładach kuponu.
    public void ileTrafień(int[] trafienia, Losowanie l) {
        TreeSet<Integer> wylosowaneLiczby = l.dajWylosowaneLiczby();
        for (Zakład z : zakłady) {
            int count = 0;
            TreeSet<Integer> zaznaczoneNumery = z.dajLiczby();
            for (int liczba : zaznaczoneNumery) {
                if (wylosowaneLiczby.contains(liczba)) {
                    count++;
                }
            }
            if(count > 0) trafienia[count-1]++;
        }
    }

    // Metoda pomocnicza licząca sumę cyfr.
    private int sumaCyfr(long liczba) {
        int suma = 0;
        while (liczba > 0) {
            suma += (int) (liczba % 10);
            liczba /= 10;
        }
        return suma;
    }

    private int obliczSumeKontrolna() {
        int suma = 0;
        suma += sumaCyfr(losowyZnacznik);
        suma += sumaCyfr(numerKolektury);
        suma += sumaCyfr(numerPorządkowy);

        return suma % 100;
    }

    // ============================ toString ==============================

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("KUPON NR ").append(this.dajIdentyfikator()).append("\n");
        for(int i = 0; i < zakłady.size(); i++){
            sb.append(i+1).append(": ");
            TreeSet<Integer> zaznaczoneLiczby = zakłady.get(i).dajLiczby();
            for(int liczba : zaznaczoneLiczby){
                sb.append((liczba < 10) ? " " + liczba : liczba).append(" ");
            }
            sb.append("\n");
        }
        sb.append("LICZBA LOSOWAŃ: ").append(liczbaLosowań).append("\n");
        sb.append("NUMERY LOSOWAŃ:\n");
        for(int i = liczbaLosowań - 1; i >= 0; i--){
            sb.append(" ").append(this.dajNumerOstatniegoLosowania() - i);
        }
        sb.append("\n").append("CENA: ").append(cena);
        return sb.toString();
    }

}
