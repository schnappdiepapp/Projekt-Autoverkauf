package com.company;

public class Auto {

    // Klassenvariable - existiert unabhängig von Objekt (durch "static")
    private static int nextId = 0;

    // Instanzvariablen
    private int autoId;
    private String hersteller;
    private boolean automatik;
    private int ps;
    private double preis;

    // Konstruktor (initialisiert neues Objekt)
    public Auto(String hersteller, boolean automatik, int ps, double preis) {
        this.autoId = nextId++;
        this.hersteller = hersteller;
        this.automatik = automatik;
        this.ps = ps;
        this.preis = preis;
    }


    // Getter
    public int getAutoId() {
        return this.autoId;
    }

    public String getHersteller() {
        return this.hersteller;
    }

    public boolean getAutomatik() {
        return this.automatik;
    }

    public int getPs() {
        return this.ps;
    }

    public double getPreis() {
        return this.preis;
    }


    // Setter
    public void setAutoId(int autoId) {
        this.autoId = autoId;
    }

    public void setHersteller(String hersteller) {
        this.hersteller = hersteller;
    }

    public void setAutomatik(boolean automatik) {
        this.automatik = automatik;
    }

    public void setPs(int ps) {
        this.ps = ps;
    }

    public void setPreis(double preis) {
        this.preis = preis;
    }
}
