package totolotek;

import java.util.Random;
import java.util.TreeSet;

public class Losowanie {
    // ============================ Atrybuty ==============================

    private final int numerPorządkowy;
    private final TreeSet<Integer> wylosowaneLiczby;
    private int[] trafienia;
    private Pieniądze[] pulaNagród;

    // ========================== Konstruktor =============================

    public Losowanie(int numerPorządkowy){
        this.numerPorządkowy = numerPorządkowy;
        this.wylosowaneLiczby = wylosujLiczby();
    }

    // Drugi konstruktor, który nie jest częścią implementacji zadania.
    // Służy on jedynie do przetestowania działania mechanizmu przeprowadzania
    // losowania, znając wylosowane liczby.
    public Losowanie(){
        this.wylosowaneLiczby = new TreeSet<>();
        wylosowaneLiczby.add(1);
        wylosowaneLiczby.add(2);
        wylosowaneLiczby.add(3);
        wylosowaneLiczby.add(4);
        wylosowaneLiczby.add(5);
        wylosowaneLiczby.add(6);
        this.numerPorządkowy = 1;
    }

    // ===================== Gettery do atrybutów =========================

    public TreeSet<Integer> dajWylosowaneLiczby(){
        return this.wylosowaneLiczby;
    }

    public int dajNumer(){
        return numerPorządkowy;
    }

    public Pieniądze[] dajPuleNagród(){
        return this.pulaNagród;
    }

    public Pieniądze dajNagrodaKStopnia(int stopień){
        if(trafienia[Stałe.poprawnaIlośćZaznaczonych-stopień] > 0)
            return pulaNagród[stopień - 1].dzielenie(
                    trafienia[Stałe.poprawnaIlośćZaznaczonych-stopień]);
        else
            return new Pieniądze();
    }

    public int[] dajTrafienia(){
        return this.trafienia;
    }

    // ============ Metody modyfikacji elementów (set) ====================

    public void ustawPuleNagród(Pieniądze[] nagrody){
        this.pulaNagród = nagrody;
    }

    public void ustawTrafienia(int[] trafienia){
        this.trafienia = trafienia;
    }

    // =========================== Metody pomocnicze ==========================

    private TreeSet<Integer> wylosujLiczby(){
        TreeSet<Integer> liczby = new TreeSet<>();
        Random rand = new Random();
        while(liczby.size() != Stałe.poprawnaIlośćZaznaczonych){
            liczby.add(rand.nextInt(Stałe.wszystkieNumeryZakładu) + 1);
        }
        return liczby;
    }

    // ============================ toString ==============================

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("=====================================================\n");
        sb.append("Losowanie nr ").append(numerPorządkowy).append("\n")
                .append("Wyniki: ");
        for(int liczby : dajWylosowaneLiczby()){
            sb.append((liczby < 10) ? " " + liczby : liczby).append(" ");
        }
        sb.append("\nPule Nagród (Pojedyncze Wygrane):\n");
        for(int i = 0; i < 4; i++){
            sb.append(" Stopień ").append(i+1).append(": ").append(pulaNagród[i])
                    .append(" (");
            if (trafienia[5 - i] == 0) {
                sb.append(0);
            } else {
                sb.append(pulaNagród[i].dzielenie(trafienia[5-i]));
            }
            sb.append(")\n");
        }
        sb.append("\nTrafione 1 numer: ").append(trafienia[0]);
        sb.append("\nTrafione 2 numery: ").append(trafienia[1]);
        sb.append("\nTrafione 3 numery: ").append(trafienia[2]);
        sb.append("\nTrafione 4 numery: ").append(trafienia[3]);
        sb.append("\nTrafione 5 numerów: ").append(trafienia[4]);
        sb.append("\nTrafione 6 numerów: ").append(trafienia[5]);
        sb.append("\n=====================================================");
        return sb.toString();
    }
}
