package totolotek.gracze;

import totolotek.Blankiet;
import totolotek.Centrala;
import totolotek.Kolektura;
import totolotek.Pieniądze;
import wyjątki.NiepoprawnyNumer;
import wyjątki.ZłyIndeks;

import java.util.ArrayList;
import java.util.Random;
import java.util.TreeSet;

public class GraczStałoliczbowy extends Gracz {
    // ============================ Atrybuty ==============================

    private final TreeSet<Integer> ulubioneLiczby;
    private final ArrayList<Integer> ulubioneKolektury;
    private int ostatnioOdwiedzonaKolektura;

    // ========================== Konstruktor ============================

    public GraczStałoliczbowy(String imie, String nazwisko, String pesel,
                              Pieniądze pieniądze, TreeSet<Integer> ulubioneLiczby,
                              Centrala centrala, ArrayList<Integer> ulubioneKolektury){
        super(imie, nazwisko, pesel, pieniądze, centrala);
        this.ulubioneLiczby = ulubioneLiczby;
        this.ulubioneKolektury = ulubioneKolektury;
        this.ostatnioOdwiedzonaKolektura = 0;
    }

    // ============================== Losowanie ===============================

    @Override
    public void noweLosowanie() throws ZłyIndeks, NiepoprawnyNumer {
        // Gracz kupuje nowy kupon w momencie, gdy minęło ostatnie losowanie
        // z poprzednio posiadanego przez niego kuponu.
        if(!this.czyMaKupon()){
            this.kupKupon();
        }
    }

    // Gracz kupuje 1 kupon, wykorzystując do tego blankiet, gdzie na jednym
    // zakładzie typuje swoje ulubione liczby. Blankiet jest ważny na 10 losowań.
    @Override
    public void kupKupon() throws NiepoprawnyNumer, ZłyIndeks {
        Blankiet blankiet = new Blankiet(10);
        for(int liczba : ulubioneLiczby){
            blankiet.zaznaczNumer(1,liczba);
        }

        int nrKolektury = ulubioneKolektury.get((ostatnioOdwiedzonaKolektura++)
                % ulubioneKolektury.size());
        Kolektura kolektura = this.dajWidokCentrali().dajKolekture(nrKolektury);
        kolektura.sprzedajKupon(this,blankiet);
    }
}
