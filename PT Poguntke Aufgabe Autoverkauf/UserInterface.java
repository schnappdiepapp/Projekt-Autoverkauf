package com.company;

import java.util.Arrays;
import java.util.Objects;
import java.util.Scanner;

public class UserInterface {

    com.company.Datenbank db;

    public UserInterface() {
        this.db = new com.company.Datenbank();
    }


    static final String HAUPTMENUE =  // durch "final" kann die Konstante nicht überschrieben werden
            "\nSylvie's Auto-Datenbank\n"
                    + "***********************\n"
                    + "        _______\n" +
                    "       //  ||\\ \\\n" +
                    " _____//___||_\\ \\___\n" +
                    " )  _          _    \\\n" +
                    " |_/ \\________/ \\___|\n" +
                    "___\\_/________\\_/______\n\n"
                    + "HAUPTMENÜ\n"
                    + "*********\n"
                    + "Bitte wählen Sie aus:\n"
                    + "1. Rückgabe aller Datensätze\n"
                    + "2. Hinzufügen eines neuen Fahrzeugdatensatzes\n"
                    + "3. Suche eines Datensatzes \n"
                    + "4. Löschen eines Datensatzes \n"
                    + "5. Sortieren nach Hersteller\n"
                    + "6. Beenden\n";

    public void start() {
        do {
            System.out.println(HAUPTMENUE);
            switch (new Scanner(System.in).next()) {
                case "1":
                    druckeDatensaetze(db.gibDatensaetze());
                    break;
                case "2":
                    neuerDatensatz();
                    break;
                case "3":
                    sucheDatensatz();
                    break;
                case "4":
                    loescheDatensatz();
                    break;
                case "5":
                    druckeDatensaetze(db.gibAutosSortiert());
                    break;
                case "6":
                    System.out.println("**************************\n Vielen Dank und Tschüss!\n**************************");
                    System.exit(0);
                    break;
                default:
                    druckeFalscheingabe();
                    break;
            }

        } while (true);
    }


    private void loescheDatensatz() {
        druckeDatensaetze(db.gibDatensaetze());
        System.out.println("Gib Auto-ID ein zum Löschen des Datensatzes!");
        try {
            db.loescheDatensatz(gibZahlEin());
            System.out.println("Das Auto wurde gelöscht.");
        } catch (NumberFormatException e) {
            System.out.println(("Falsche Eingabe! Auto-ID existiert nicht.")); // Programm läuft weiter
        }
    }


    private Integer gibZahlEin() {
        Scanner sc = new Scanner(System.in);
        return Integer.parseInt(sc.next());
    }


    static final String EIGENSCHAFTEN_MENUE = "Wähle:\n"
            + "1. Auto-ID\n"
            + "2. Hersteller\n"
            + "3. Automatik \n"
            + "4. PS \n"
            + "5. Preis\n"
            + "6. Zurück ins Hauptmenü\n";

    private void sucheDatensatz() {
        druckeDatensaetze(db.gibDatensaetze());
        System.out.println("Suche nach welcher Eigenschaft?");
        System.out.println(EIGENSCHAFTEN_MENUE);
        com.company.Auto[] autos = null;
        try {
            switch (new Scanner(System.in).next()) {
                case "1":
                    System.out.println("Bitte geben Sie die Auto-ID ein.");
                    autos = db.sucheNachAutoId(gibZahlEin());
                    break;
                case "2":
                    System.out.println("Bitte geben Sie den Hersteller ein.");
                    autos = db.sucheNachHersteller(new Scanner(System.in).next());
                    break;
                case "3":
                    System.out.println("Hat das Auto Automatik? J/N");
                    Boolean automatik = parseJaNeinToBoolean(new Scanner(System.in).next());
                    autos = db.sucheNachAutomatik(automatik);
                    break;
                case "4":
                    System.out.println("Bitte geben Sie die minimale PS-Zahl ein.");
                    autos = db.sucheNachPs(gibZahlEin());
                    break;
                case "5":
                    System.out.println("Bitte geben Sie den maximalen Preis (zB. 39999.99) ein.");
                    autos = db.sucheNachPreis(Double.parseDouble(new Scanner(System.in).next()));
                    break;
                case "6":
                    // Zurück ins Hauptmenü
                    return;
                default:
                    druckeFalscheingabe();
                    return;
            }
        } catch (IllegalArgumentException e) { // NumberFormatException ist auch eine IllegalArgumentException
            druckeFalscheingabe();
            return;
        }
        if (Arrays.stream(autos).allMatch(Objects::isNull)) {
            System.out.println("Trifft auf kein Auto in der Datenbank zu.");
        } else {
            druckeDatensaetze(autos);
        }
    }


    private void druckeFalscheingabe() {
        System.out.println("Falsche Eingabe!");
    }


    private void neuerDatensatz() {
        System.out.println("Gib ein Auto nach folgenden Kriterien ein: ");
        System.out.println("Hersteller, Automatik? J/N, PS, Preis");
        System.out.println("Beispiel:");
        System.out.println("Opel, J, 100, 11000.40");

        String eingabe = new Scanner(System.in).nextLine();
        String[] attribute = eingabe.split(", ");
        try {
            if (attribute.length != 4) {
                throw new IllegalArgumentException("Auflistung der Attribute falsch!");
            }
            String hersteller = attribute[0];
            boolean automatik = parseJaNeinToBoolean(attribute[1]);
            int ps = Integer.parseInt(attribute[2]);
            double preis = Double.parseDouble(attribute[3]);

            db.addDatensatz(new com.company.Auto(hersteller, automatik, ps, preis));
            System.out.println("Neues Auto wurde angelegt.");
        } catch (IllegalArgumentException e) { // NumberFormatException ist auch eine IllegalArgumentException
            druckeFalscheingabe();
            System.out.println(e.getMessage());
        }
    }


    private boolean parseJaNeinToBoolean(String eingabe) {
        switch (eingabe.toUpperCase()) {
            case "J":
                return true;
            case "N":
                return false;
            default:
                throw new IllegalArgumentException("Nur j/J für Ja und n/N für Nein ist zulässig.");
        }
    }


    private void druckeDatensaetze(com.company.Auto[] autos) {
        System.out.println(String.format("%-10s%-20s%-20s%-10s%-10s", "AutoId", "Hersteller", "Automatik", "PS", "Preis"));
        for (int i = 0; i < autos.length; i++) {
            if (autos[i] != null) {
                com.company.Auto auto = autos[i];
                System.out.println(String.format("%-10d%-20s%-20b%-10d%-10.2f", auto.getAutoId(), auto.getHersteller(),
                        auto.getAutomatik(), auto.getPs(), auto.getPreis()));

            }
        }
    }


}
