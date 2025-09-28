package totolotek.gracze;

import totolotek.Centrala;
import totolotek.Kolektura;
import totolotek.Pieniądze;
import wyjątki.ZłyIndeks;

import java.util.Random;

public class GraczLosowy extends Gracz {
    // ========================== Konstruktor ===========================

    public GraczLosowy(String imie, String nazwisko, String pesel,
                       Centrala centrala){
        super(imie, nazwisko, pesel, new Pieniądze(), centrala);
        Random rand  = new Random();

        // Losowo wybrana początkowo liczba pieniędzy mniejsza od miliona.
        this.przyjmijPieniądze(new Pieniądze(rand.nextLong(1_000_000),0));
    }

    // ============================== Losowanie ===============================

    @Override
    public void noweLosowanie() throws ZłyIndeks {
        // GraczLosowy ma taką samą rutynę przy każdym losowaniu.
        this.kupKupon();
    }

    // W losowo wybranej kolekturze gracz kupuje losową liczbę kuponów
    // z przedziału [1,100], na każdym losową liczbę zakładów i losowań.
    // Każdy zakład jest zaznaczony na chybił trafił.
    @Override
    public void kupKupon() throws ZłyIndeks {
        Random rand = new Random();
        int nrKolektury = rand.nextInt(this.dajWidokCentrali().dajLiczbeKolektur()) + 1;
        Kolektura kolektura = this.dajWidokCentrali().dajKolekture(nrKolektury);
        int ileKuponów = rand.nextInt(100) + 1;
        for(int i = 0; i < ileKuponów; i++){
            int liczbaZakładów = rand.nextInt(8) + 1;
            int liczbaLosowań = rand.nextInt(10) + 1;
            kolektura.sprzedajKuponChybiłTrafił(this, liczbaLosowań, liczbaZakładów);
        }
    }
}
