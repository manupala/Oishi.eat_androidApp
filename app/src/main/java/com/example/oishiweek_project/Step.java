package com.example.oishiweek_project;

import android.net.Uri;

import java.util.Timer;

public class Step {

    private String titolo;
    private String descrizione;
    private int tempo;  //in millisecondi

    public Step() {
        this.titolo = "";
        this.descrizione = "";
        this.tempo = 1000;
    }

    public Step(String titolo, String descrizione, int tempo) {
        this.titolo = titolo;
        this.descrizione = descrizione;
        this.tempo = tempo;
    }

    public String getTitolo() {
        return titolo;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public int getTempo() { return tempo; }

    public void setTempo(int tempo) { this.tempo = tempo; }
    
}
