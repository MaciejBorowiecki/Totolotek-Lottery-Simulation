package totolotek;

import wyjątki.ZłyIndeks;

import java.time.Period;
import java.util.ArrayList;

public class Centrala implements WidokCentrali {
    // ============================ Atrybuty ==============================

    private final ArrayList<Kolektura> kolektury;
    private final ArrayList<Losowanie> losowania;
    private final Pieniądze kumulacja;
    private final Pieniądze budżet;
    private int nKuponów;

    // ========================== Konstruktor ============================

    public Centrala() {
        this.budżet = new Pieniądze();
        this.kolektury = new ArrayList<>();
        this.losowania = new ArrayList<>();
        this.kumulacja = new Pieniądze();
        this.nKuponów = 0;
    }

    // ======================= Gettery do atrybutów =======================

    public Kolektura dajKolekture(int i) throws ZłyIndeks{
        if(i < 1 || i > this.kolektury.size()) {
            throw new ZłyIndeks("Podano zły indeks kolektury.");
        }
        return kolektury.get(i - 1);
    }

    public Pieniądze dajBudżet() {
        return this.budżet;
    }

    public Pieniądze dajKumulacja() {
        return this.kumulacja;
    }

    public int dajLiczbeKuponów() {
        return nKuponów;
    }

    public int dajLiczbeKolektur() {
        return kolektury.size();
    }

    // ============ Metody modyfikacji elementów (set) ====================

    public void dodajKolekture() {
        kolektury.add(new Kolektura(this, kolektury.size() + 1));
    }

    public void przyjmijPieniądze(Pieniądze p) {
        this.budżet.dodaj(p);
    }

    public void dodajKupon() {
        nKuponów++;
    }

    // ============================== Losowanie ===============================

    // Metoda przeprowadzająca losowanie.
    public void przeprowadźLosowanie() {
        losowania.add(new Losowanie(losowania.size() + 1));
        obliczPuleNagród();
    }

    // Metoda nie jest częścią implementacji zadania. Metoda służy do przetestowania
    // mechanizmu przeprowadzania losowania ustawiając konkretne dane jako
    // wylosowane liczby
    public void przeprowadźFałszyweLosowanie(){
        losowania.add(new Losowanie());
        obliczPuleNagród();
    }

    public Losowanie dajOstatnieLosowanie() {
        return losowania.getLast();
    }

    // Metoda przekazująca pieniądze z budżetu.
    public Pieniądze wydajPieniądze(Pieniądze kwota) {
        budżet.odejmij(kwota);

        // Potencjalne skorzystanie z budżetu państwa.
        if (!budżet.czyWiększeRówne(new Pieniądze(0, 0))) {
            BudżetPaństwa budżetPaństwa = BudżetPaństwa.dajInstancje();
            budżetPaństwa.wydajSubwencje(budżet.mnożenie(-1));
            budżet.ustawWartość(0, 0);
        }
        return kwota;
    }

    // Metoda płacąca podatek do budżetu państwa.
    public void zapłaćPodatek(Pieniądze podatek) {
        BudżetPaństwa budżetPaństwa = BudżetPaństwa.dajInstancje();
        budżetPaństwa.pobierzPodatek(podatek);
        budżet.odejmij(podatek);

        // Potencjalne skorzystanie z budżetu pańśtwa.
        if (!budżet.czyWiększeRówne(new Pieniądze(0, 0))) {
            budżetPaństwa.wydajSubwencje(budżet.mnożenie(-1));
            budżet.ustawWartość(0, 0);
        }
    }

    public Losowanie dajLosowanie(int i) throws ZłyIndeks {
        if(i < 1 || i > losowania.size()) {
            throw new ZłyIndeks("Podano nieprawidłowy indeks.");
        }
        return losowania.get(i - 1);
    }

    public int dajNumerOstatniegoLosowania() {
        return losowania.size();
    }

    public int dajNumerNajbliższegoLosowania() {
        return losowania.size() + 1;
    }

    // Metoda pomocnicza do losowania - oblicza pule nagród, oraz liczby trafień.
    private void obliczPuleNagród() {
        // zyskZLosowania jest potrzebny jedynie do obliczania konkretnych pul
        // Nagród, podatek od kupionych kuponów został odprowadzony wcześniej.
        Pieniądze zyskZLosowania = new Pieniądze();

        // i-ty indeks reprezentuje liczbę trafień i+1 liczb.
        int[] trafienia = new int[Stałe.poprawnaIlośćZaznaczonych];

        for (Kolektura k : kolektury) {
            zyskZLosowania.dodaj(k.sumaPostawionychPieniędzy(losowania.size()));
            k.ileTrafień(losowania.getLast(), trafienia);
        }
        zyskZLosowania.odejmij(zyskZLosowania.obliczPodatek());

        // Oblicz poszczególne pule nagród.
        // 51% zysku z losowania jest przeznaczone na nagrody.
        zyskZLosowania = zyskZLosowania.mnożenie(51).dzielenie(100);

        // i-ty indeks reprezentuje i+1 stopień nagrody.
        Pieniądze[] nagrody = new Pieniądze[4];

        // 44% puli jest przeznaczone na nagrody 1 stopnia.
        Pieniądze pulaI = zyskZLosowania.mnożenie(44).dzielenie(100);

        // 8% jest przeznaczone na pule 2 stopnia.
        Pieniądze pulaII = zyskZLosowania.mnożenie(8).dzielenie(100);

        // 24pln * (liczba trafień 3 liczb jest przeznaczone na pule 4 stopnia.
        Pieniądze pulaIV = (new Pieniądze(24, 0)).mnożenie(trafienia[2]);

        // Pozostała liczba pieniędzy z puli jest potrzebna do obliczenia puli
        // dla 3 stopnia.
        Pieniądze Pozostałe = new Pieniądze(zyskZLosowania.dajZłotówki(),
                (int) zyskZLosowania.dajGrosze());
        Pozostałe.odejmij(pulaI);
        Pozostałe.odejmij(pulaII);
        Pozostałe.odejmij(pulaIV);

        // Pula 3 stopnia liczona na podstawie maksimum z 36pln za wygraną
        // oraz pozostałych pieniędzy w puli.
        Pieniądze pulaIII = Pozostałe.maksimum((new Pieniądze(36, 0).
                mnożenie(trafienia[3])));

        // Uzupełnianie puli nagród 1 stopnia do maksimum z 2 milionów i obsługa
        // ewentualnej kumulacji.
        nagrody[0] = pulaI.maksimum(new Pieniądze(2_000_000, 0));
        nagrody[0].dodaj(kumulacja);
        if (trafienia[5] == 0) {
            kumulacja.ustawWartość(nagrody[0]);
        } else {
            kumulacja.ustawWartość(0, 0);
        }
        nagrody[1] = pulaII;
        nagrody[2] = pulaIII;
        nagrody[3] = pulaIV;

        // Zapisywanie wyników do Losowania, aby można je było odtworzyć.
        losowania.getLast().ustawPuleNagród(nagrody);
        losowania.getLast().ustawTrafienia(trafienia);
    }
}
