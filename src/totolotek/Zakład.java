package totolotek;

import wyjątki.NiepoprawnyNumer;

import java.util.Random;
import java.util.TreeSet;

public class Zakład {
    // ============================ Atrybuty ==============================

    private boolean czyAnulowane;
    private final TreeSet<Integer> numery;
    private final int poprawnaLiczbaNumerów;

    // ========================== Konstruktor =============================

    public Zakład() {
        this.czyAnulowane = false;
        this.numery = new TreeSet<>();
        this.poprawnaLiczbaNumerów = Stałe.poprawnaIlośćZaznaczonych;
    }

    // ===================== Gettery do atrybutów =========================

    // Metoda czyWażny sprawdza, czy została zaznaczona odpowiednia liczba
    // numerów oraz, czy zakład został anulowany.
    public boolean czyWażny() {
        return !czyAnulowane && (numery.size() == poprawnaLiczbaNumerów);
    }

    public TreeSet<Integer> dajLiczby(){
        return numery;
    }

    // ============ Metody modyfikacji elementów (set) ====================

    // Metoda pozwalająca na uzupełnienie zakładu.
    public void zaznaczNumer(int numer) throws NiepoprawnyNumer {
        if(numer > Stałe.wszystkieNumeryZakładu || numer < 1){
            throw new NiepoprawnyNumer();
        }
        numery.add(numer);
    }

    // Metoda pozwalająca na uzupełnienie zakładu.
    public void anuluj() {
        this.czyAnulowane = true;
    }

    public void losowyZakład(){
        Random rand = new Random();
        while(numery.size() != Stałe.poprawnaIlośćZaznaczonych){
            numery.add(rand.nextInt(Stałe.wszystkieNumeryZakładu) + 1);
        }
    }

    // ============================ toString ==============================

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i <= Stałe.wszystkieNumeryZakładu; i++) {
            if (numery.contains(i)) {
                sb.append(" [ -- ] ");
            } else {
                sb.append(" [ ");
                if(i < 10){
                    sb.append(" ");
                }
                sb.append(i).append(" ] ");
            }
            if (i % 10 == 0) {
                sb.append("\n");
            }
        }
        sb.append("\n");
        sb.append((czyAnulowane) ? " [ -- ] " : " [    ] ").append("anuluj");
        return sb.toString();
    }
}
