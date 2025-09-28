package totolotek;

public class BudżetPaństwa {
    // ============================ Atrybuty ==============================

    private final Pieniądze pobranePodatki;
    private final Pieniądze wydaneSubwencje;

    // BudżetPaństwa będzie singletonem.
    private static BudżetPaństwa instance;

    // ========================== Konstruktory ============================

    private BudżetPaństwa(){
        this.pobranePodatki = new Pieniądze();
        this.wydaneSubwencje = new Pieniądze();
    }

    // Metoda potrzebna do uzyskiwania referencji do budżetu państwa.
    public static BudżetPaństwa dajInstancje(){
        if(instance == null){
            instance = new BudżetPaństwa();
        }
        return instance;
    }

    // ===================== Gettery do atrybutów =========================

    public Pieniądze pobranePodatki(){
        return this.pobranePodatki;
    }

    public Pieniądze wydaneSubwencje(){
        return this.wydaneSubwencje;
    }

    // ============ Metody modyfikacji elementów (set) ====================

    public void pobierzPodatek(Pieniądze kwota){
        this.pobranePodatki.dodaj(kwota);
    }

    public void wydajSubwencje(Pieniądze kwota){
        this.wydaneSubwencje.dodaj(kwota);
    }

    // ============================ toString ==============================

    @Override
    public String toString(){
        return " Zebrane Podatki : " + pobranePodatki + "\n Wydane Subwencje: "
                + wydaneSubwencje;
    }
}
