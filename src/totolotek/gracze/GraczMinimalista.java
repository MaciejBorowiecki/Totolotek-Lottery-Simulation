package totolotek.gracze;

import totolotek.Centrala;
import totolotek.Kolektura;
import totolotek.Pieniądze;
import wyjątki.ZłyIndeks;

import java.util.Random;

public class GraczMinimalista extends Gracz {
    // ============================ Atrybuty ==============================

    private final int nrUlubionejKolektury;
    private final int liczbaLosowań;    // Zapobieganie magic numbers.
    private final int liczbaZakładów;   // Zapobieganie magic numbers.

    // ========================== Konstruktor ============================

    public GraczMinimalista(String imie, String nazwisko, String pesel,
                            Pieniądze pieniądze, int ulubionaKolektura,
                            Centrala centrala) {
        super(imie, nazwisko, pesel, pieniądze, centrala);
        this.nrUlubionejKolektury = ulubionaKolektura;
        liczbaLosowań = 1;
        liczbaZakładów = 1;
    }

    // ============================== Losowanie ===============================

    @Override
    public void noweLosowanie() throws ZłyIndeks {
        // GraczMinimalista ma taką samą rutynę w każdym losowaniu.
        this.kupKupon();
    }

    // Przed każdym losowaniem gracz kupuje 1 kupon z 1 zakładem i obowiązujący
    // tylko i wyłącznie na następne losowanie. Kupon jest zaznaczony na chybił
    // trafił.
    @Override
    public void kupKupon() throws ZłyIndeks {
        Kolektura kolektura = dajWidokCentrali().dajKolekture(nrUlubionejKolektury);
        kolektura.sprzedajKuponChybiłTrafił(this, 1, 1);
    }
}