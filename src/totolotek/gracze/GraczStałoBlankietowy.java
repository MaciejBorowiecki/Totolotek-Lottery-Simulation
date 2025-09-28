package totolotek.gracze;

import totolotek.Blankiet;
import totolotek.Centrala;
import totolotek.Kolektura;
import totolotek.Pieniądze;
import wyjątki.ZłyIndeks;

import java.util.ArrayList;
import java.util.Random;

public class GraczStałoBlankietowy extends Gracz {
    // ============================ Atrybuty ==============================

    private final Blankiet ulubionyBlankiet;
    private final ArrayList<Integer> ulubioneKolektury;
    private final int częstotliwoścKupowania;
    private int któreLosowanie;
    private int ostatnioOdwiedzonaKolektura;

    // ========================== Konstruktor ============================

    public GraczStałoBlankietowy(String imie, String nazwisko, String pesel,
                                 Pieniądze pieniądze, Blankiet ulubionyBlankiet,
                                 Centrala centrala, ArrayList<Integer> ulubioneKolektury,
                                 int częstotliwośćKupowania) {
        super(imie, nazwisko, pesel, pieniądze, centrala);
        this.ulubionyBlankiet = ulubionyBlankiet;
        this.ulubioneKolektury = ulubioneKolektury;
        this.częstotliwoścKupowania = częstotliwośćKupowania;
        this.któreLosowanie = 0;
        this.ostatnioOdwiedzonaKolektura = 0;
    }

    // ============================== Losowanie ===============================

    @Override
    public void noweLosowanie() throws ZłyIndeks {
        // Gracz kupuje nowy kupon co ustaloną liczbę losowań.
        któreLosowanie++;
        if(któreLosowanie % częstotliwoścKupowania == 0) {
            this.kupKupon();
        }
    }

    // W wybranych przez siebie losowaniach gracz kupuje 1 kupon z ulubionym
    // uzupełnieniem blankietu w jednej ze swoich ulubionych kolektur.
    @Override
    public void kupKupon() throws ZłyIndeks {
        Random rand = new Random();
        int nrKolektury = ulubioneKolektury.get((ostatnioOdwiedzonaKolektura++)
                % ulubioneKolektury.size());
        Kolektura kolektura = this.dajWidokCentrali().dajKolekture(nrKolektury);
        kolektura.sprzedajKupon(this,ulubionyBlankiet);
    }
}
