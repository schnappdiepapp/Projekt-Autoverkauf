package com.company;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;

public class Datenbank {

    // mit "final" eine unveränderbare Konstante festgelegt
    private final int ARRAY_GROESSE = 50;

    // Instanzvariable vom Typ Array für Autos
    private com.company.Auto[] autoDatensaetze;

    //Konstruktor
    public Datenbank() {
        this.autoDatensaetze = new com.company.Auto[ARRAY_GROESSE];
        this.autoDatensaetze[0] = new com.company.Auto("Mercedes", true, 200, 140.44);
        this.autoDatensaetze[1] = new com.company.Auto("Ford", false, 80, 15000.10);
        this.autoDatensaetze[2] = new com.company.Auto("VW", true, 150, 11.50);
        this.autoDatensaetze[10] = new com.company.Auto("Mercedes", false, 50, 70000);
        this.autoDatensaetze[12] = new com.company.Auto("Opel", true, 500, 222);
    }


    // Rückgabe aller Datensätze
    public com.company.Auto[] gibDatensaetze() {
        return this.autoDatensaetze;
    }

    // Datensatz hinzufügen
    public void addDatensatz(com.company.Auto auto) {
        for (int i = 0; i < autoDatensaetze.length; i++) {
            if (autoDatensaetze[i] == null) {
                ea(auto);
                this.autoDatensaetze[i] = auto;
                return;
            }
        }
        System.out.println("Datenbank voll! Auto konnte nicht gespeichert werden!");
    }

    private void ea(com.company.Auto auto) {
        if (auto.getHersteller().equals("Easter Egg")) {
            System.out.println("Das Auto wurde leider gestohlen und konnte nicht in der Datenbank gespeichert werden!!!");
            System.out.println("Wollen Sie Anzeige erstatten? J/N");
            new Scanner(System.in).next();
            System.out.println("Die Polizei hat Ihr Auto gefunden! Hurra! Es wurde in der Datenbank gespeichert.");
        }
    }

    // Suche nach Attributen
    public com.company.Auto[] sucheNachBedingung(SuchBedingung bedingung) {
        com.company.Auto[] ergebnisse = new com.company.Auto[autoDatensaetze.length];
        for (int i = 0; i < autoDatensaetze.length; i++) {
            com.company.Auto aktuellesAuto = this.autoDatensaetze[i];
            if (aktuellesAuto == null) continue; // Überspringe "Löcher" im Array
            if (bedingung.wirdErfuelltVon(aktuellesAuto)) {
                ergebnisse[i] = aktuellesAuto;
            }
        }
        return ergebnisse;
    }


    public com.company.Auto[] sucheNachHersteller(String hersteller) {
        return sucheNachBedingung((com.company.Auto auto) -> auto.getHersteller().equals(hersteller));
    }

    public com.company.Auto[] sucheNachAutoId(int id) {
        return sucheNachBedingung((com.company.Auto auto) -> auto.getAutoId() == id);
    }

    public com.company.Auto[] sucheNachPreis(double preis) {
        return sucheNachBedingung((com.company.Auto auto) -> auto.getPreis() <= preis);
    }

    public com.company.Auto[] sucheNachPs(int ps) {
        return sucheNachBedingung((com.company.Auto auto) -> auto.getPs() >= ps);
    }

    public com.company.Auto[] sucheNachAutomatik(Boolean automatik) {
        return sucheNachBedingung((com.company.Auto auto) -> auto.getAutomatik() == automatik);
    }

    // Löschen eines Datensatzes, falls vorhanden
    public void loescheDatensatz(int autoId) {
        for (int i = 0; i < autoDatensaetze.length; i++) {
            com.company.Auto aktuellesAuto = this.autoDatensaetze[i];
            if (aktuellesAuto == null) {
                continue;
            }
            if (aktuellesAuto.getAutoId() == autoId) {
                this.autoDatensaetze[i] = null;
            }
        }
    }

    // Autos im Array nach Hersteller sortieren
    public com.company.Auto[] gibAutosSortiert() {
        com.company.Auto[] autos = Arrays.copyOf(this.autoDatensaetze, this.autoDatensaetze.length);
        Arrays.sort(autos, new Comparator<com.company.Auto>() {
            // Methode ""compare" vergleicht zwei übergebene Objekte
            public int compare(com.company.Auto auto1, com.company.Auto auto2) {
                if (auto1 == null && auto2 == null) {
                    return 0;
                }
                if (auto1 == null) {
                    return 1;
                }
                if (auto2 == null) {
                    return -1;
                }
                return auto1.getHersteller().compareTo(auto2.getHersteller());
            }

        });
        return autos;
    }
}