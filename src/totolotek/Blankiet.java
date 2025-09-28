package totolotek;

import wyjątki.NiepoprawnyNumer;
import wyjątki.ZłyIndeks;

import java.util.Random;

public class Blankiet {
    // ============================ Atrybuty ==============================

    private final Zakład[] zakłady;
    private int liczbaLosowań;
    private final int liczbaZakładów;

    // ========================== Konstruktory ============================

    // Konstruktor służący do tworzenia blankietu gotowego do uzupełnienia
    // przez gracza.
    public Blankiet(int liczbaLosowań) {
        this.liczbaLosowań = liczbaLosowań;
        this.liczbaZakładów = Stałe.maxIlośćZakładówNaBlankiecie;
        this.zakłady = new Zakład[liczbaZakładów];
        for (int i = 0; i < liczbaZakładów; i++) {
            zakłady[i] = new Zakład();
        }
    }

    // Konstruktor służący do tworzenia blankietu na chybił trafił
    // uzupełniający zakłady losowo.
    public Blankiet(int liczbaLosowań, int liczbaZakładów){
        Random r = new Random();
        this.liczbaLosowań = liczbaLosowań;
        this.liczbaZakładów = liczbaZakładów;
        this.zakłady = new Zakład[Stałe.maxIlośćZakładówNaBlankiecie];
        for(int i = 0; i < Stałe.maxIlośćZakładówNaBlankiecie; i++){
            zakłady[i] = new Zakład();
        }
        this.losujBlankiet();
    }

    // ===================== Gettery do atrybutów =========================

    public int dajLiczbeLosowań(){
        return liczbaLosowań;
    }

    public Zakład dajZakład(int numer) throws ZłyIndeks {
        if (numer < 1 || numer > Stałe.maxIlośćZakładówNaBlankiecie) {
            throw new ZłyIndeks("Oczekiwano numeru od 1 do 8, a otrzymano: " + numer);
        }
        return zakłady[numer - 1];
    }

    // ============ Metody modyfikacji elementów (set) ====================

    public void anulujZakład(int zakład) {
        zakłady[zakład - 1].anuluj();
    }

    // Metoda pomocnicza do tworzenia blankietu chybił trafił.
    private void losujBlankiet(){
        Random rand = new Random();
        for(int i = 0; i < liczbaZakładów; i++){
            zakłady[i].losowyZakład();
        }
    }

    // Metoda do uzupełniania blankietu przez gracza.
    public void zaznaczNumer(int zakład, int numer) throws NiepoprawnyNumer {
        if (zakład > Stałe.maxIlośćZakładówNaBlankiecie || zakład < 1) {
            throw new NiepoprawnyNumer();
        }
        zakłady[zakład - 1].zaznaczNumer(numer);
    }

    // Metoda do uzupełniania blankietu przez gracza.
    public void zaznaczLiczbeLosowań(int liczbaLosowań){
        this.liczbaLosowań = liczbaLosowań;
    }

    // ============================ toString ==============================

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < Stałe.maxIlośćZakładówNaBlankiecie; i++) {
            sb.append(i + 1).append("\n").append(zakłady[i]).append("\n");
        }
        sb.append("Liczba losowań: ");
        for (int i = 1; i <= Stałe.maxilośćLosowańNaBlankiecie; i++) {
            sb.append((i == liczbaLosowań) ? " [ -- ] " : (" [ " + i + " ] "));
        }
        return sb.toString();
    }

}
