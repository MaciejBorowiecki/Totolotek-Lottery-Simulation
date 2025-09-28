package totolotek.gracze;

import totolotek.*;
import wyjątki.NielegalneZachowanie;
import wyjątki.NiepoprawnyNumer;
import wyjątki.ZłyIndeks;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public abstract class Gracz {
    // ============================ Atrybuty ===============================

    private final String imie;
    private final String nazwisko;
    private final String pesel;
    private final Pieniądze pieniądze;
    private final Set<Kupon> kupony;
    private final WidokCentrali widokCentrali;

    // ========================== Konstruktor ============================

    public Gracz(String imie, String nazwisko, String pesel, Pieniądze pieniądze,
                 Centrala centrala) {
        this.imie = imie;
        this.nazwisko = nazwisko;
        this.pesel = pesel;
        this.pieniądze = pieniądze;
        this.kupony = new HashSet<>();
        this.widokCentrali = centrala;
    }

    // ======================= Gettery do atrybutów =======================

    public Pieniądze dajIlośćPieniędzy() {
        return this.pieniądze;
    }

    public boolean czyMaKupon() {
        return !this.kupony.isEmpty();
    }

    protected WidokCentrali dajWidokCentrali() {
        return this.widokCentrali;
    }

    public String dajImie() {
        return this.imie;
    }

    public String dajNazwisko() {
        return this.nazwisko;
    }

    public String dajPesel() {
        return this.pesel;
    }

    // Metoda do testów.
    public Set<Kupon> dajKupony() {
        return this.kupony;
    }

    // ============ Metody modyfikacji elementów (set) ====================

    public void przyjmijPieniądze(Pieniądze p) {
        this.pieniądze.dodaj(p);
    }

    // ============================== Losowanie ===============================

    abstract public void kupKupon() throws ZłyIndeks, NiepoprawnyNumer;

    // Metoda odbierająca kupon z kolektury.
    public void weźKupon(Kupon kupon) {
        kupony.add(kupon);
        pieniądze.odejmij(kupon.dajCenę());
    }

    // Metoda aktualizująca gracza według jego rutyny przy nowym losowaniu.
    public abstract void noweLosowanie() throws Exception;

    // Metoda odbiera kupony, na których wszystkie losowania już się odbyły.
    public void odbierzSkończoneKupony() throws ZłyIndeks {
        // Zmienna pomocnicza, aby móc usunąć z atrybutów gracza wszystkie
        // odebrane kupony.
        ArrayList<Kupon> odebraneKupony = new ArrayList<>();
        for (Kupon k : kupony) {
            if (k.dajNumerOstatniegoLosowania() == widokCentrali.
                    dajNumerOstatniegoLosowania()) {
                odebraneKupony.add(k);
                // Kupon odbiera się w kolekturze, w której był zakupiony.
                Kolektura kolektura = widokCentrali.dajKolekture(
                        k.dajNumerKolektury());

                // Kolektura sprawdza poprawność kuponu i wydaje nagrodę.
                try {
                    kolektura.wydajNagrodę(this, k,
                            widokCentrali.dajNumerOstatniegoLosowania());
                } catch (NielegalneZachowanie e) {
                    System.err.println("Błąd: " + e.getMessage());
                }
            }
        }
        // Usuwanie odebranych kuponów.
        for (int i = 0; i < odebraneKupony.size(); i++) {
            kupony.remove(odebraneKupony.get(i));
        }
    }

    // Metoda pozwalająca na odebranie kuponów po ostatnim losowaniu.
    // Działa analogicznie do poprzedniej.
    public void odbierzPozostałeKupony() throws ZłyIndeks {
        ArrayList<Kupon> odebraneKupony = new ArrayList<>();
        for (Kupon k : kupony) {
            odebraneKupony.add(k);
            Kolektura kolektura = widokCentrali.dajKolekture(
                    k.dajNumerKolektury());
            try {
                kolektura.wydajNagrodę(this, k,
                        widokCentrali.dajNumerOstatniegoLosowania());
            } catch (NielegalneZachowanie e) {
                System.err.println("Błąd: " + e.getMessage());
            }
        }
        for (int i = 0; i < odebraneKupony.size(); i++) {
            kupony.remove(odebraneKupony.get(i));
        }
    }

    // ============================ toString ==============================

    @Override
    public String toString() {
        return ("Imie: " + imie + "\n" +
                "Nazwisko: " + nazwisko + "\n" +
                "Pesel: " + pesel + "\n" +
                "Pieniądze: " + pieniądze + "\n" +
                "Aktualnie posiadana liczba kuponów: " + kupony.size());
    }
}
