package totolotek;

public class Pieniądze implements Comparable<Pieniądze> {
    // ============================ Atrybuty ==============================

    // Pieniądze są trzymane jako grosze;
    private long grosze;

    // ========================== Konstruktory ===========================

    public Pieniądze(long złotówki, int grosze) {
        this.grosze = 100 * złotówki + grosze;
    }

    public Pieniądze() {
        this.grosze = 0;
    }

    public Pieniądze(long grosze) {
        this.grosze = grosze;
    }

    // ======================= Gettery do atrybutów =======================

    public long dajZłotówki() {
        return grosze / 100;
    }

    public long dajGrosze() {
        return grosze % 100;
    }

    // ============ Metody modyfikacji elementów (set) ====================

    public void ustawWartość(long złotówki, int grosze) {
        this.grosze = 100 * złotówki + grosze;
    }

    public void ustawWartość(Pieniądze pieniądze) {
        this.grosze = pieniądze.dajZłotówki() * 100 + pieniądze.dajGrosze();
    }

    public void dodaj(long grosze) {
        this.grosze += grosze;
    }

    public void dodaj(Pieniądze p) {
        this.grosze += p.dajZłotówki() * 100 + p.dajGrosze();
    }

    public void odejmij(Pieniądze p) {
        this.grosze -= (p.dajZłotówki() * 100 + p.dajGrosze());
    }

    // ========================= Metody pomocnicze ========================

    // Metoda obliczająca najczęściej występujący podatek, występujący
    // przy zakupie każdego kuponu.
    public Pieniądze obliczPodatek() {
        return new Pieniądze(this.grosze / 5);
    }

    public Pieniądze dzielenie(int k) {
        return new Pieniądze(this.grosze / k);
    }

    public Pieniądze mnożenie(int k) {
        return new Pieniądze(this.grosze * k);
    }

    @Override
    public int compareTo(Pieniądze other) {
        return Long.compare(this.grosze, ((other.dajZłotówki() * 100) + other.dajGrosze()));
    }

    public boolean czyWiększeRówne(Pieniądze other) {
        return this.compareTo(other) >= 0;
    }

    public Pieniądze maksimum(Pieniądze p) {
        return (this.compareTo(p) >= 0) ? this : p;
    }

    // ============================ toString ==============================

    @Override
    public String toString() {
        return this.dajZłotówki() + " zł " + this.dajGrosze() + " gr";
    }

    // ===================== Metody do testów JUnit =======================

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true; // ten sam obiekt
        if (!(obj instanceof Pieniądze)) return false;

        return this.compareTo((Pieniądze) obj) == 0;
    }

    @Override
    public int hashCode() {
        return Long.hashCode(this.grosze);
    }
}

