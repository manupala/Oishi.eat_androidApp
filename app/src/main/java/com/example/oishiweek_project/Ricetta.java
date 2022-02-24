package com.example.oishiweek_project;

import android.net.Uri;

import java.io.Serializable;
import java.util.ArrayList;

public class Ricetta implements Serializable {

    private String nome;
    private String tempo;
    private int kCal;
    private Uri immagine;    //nel database viene memorizzata la stringa corrispondente (immagine.toString();)
    private ArrayList<Ingrediente> ingredienti;
    private ArrayList<String> strumenti;
    private ArrayList<Step> preparazione;   //contiene i vari step per la preparazione della ricetta

    public Ricetta() {
        this.nome = "";
        this.tempo = "";
        this.kCal = 0;
        this.immagine = null;
        this.ingredienti = null;
        this.strumenti = null;
        this.preparazione = null;
    }

    public Ricetta(String nome, String tempo, int kCal, Uri immagine, ArrayList<Ingrediente> ingredienti, ArrayList<String> strumenti, ArrayList<Step> preparazione) {
        this.nome = nome;
        this.tempo = tempo;
        this.kCal = kCal;
        this.immagine = immagine;
        this.ingredienti = ingredienti;
        this.strumenti = strumenti;
        this.preparazione = preparazione;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTempo() {
        return tempo;
    }

    public void setTempo(String tempo) {
        this.tempo = tempo;
    }

    public int getkCal() {
        return kCal;
    }

    public void setkCal(int kCal) {
        this.kCal = kCal;
    }

    public Uri getImmagine() {
        return immagine;
    }

    public void setImmagine(Uri immagine) {
        this.immagine = immagine;
    }

    public ArrayList<Ingrediente> getIngredienti() {
        return ingredienti;
    }

    public void setIngredienti(ArrayList<Ingrediente> ingredienti) {
        this.ingredienti = ingredienti;
    }

    public void addIngrediente(Ingrediente ingrediente) {
        this.ingredienti.add(ingrediente);
    }

    public ArrayList<String> getStrumenti() {
        return strumenti;
    }

    public void setStrumenti(ArrayList<String> strumenti) {
        this.strumenti = strumenti;
    }

    public void addStrumento(String strumento) {
        this.strumenti.add(strumento);
    }

    public ArrayList<Step> getPreparazione() {
        return preparazione;
    }

    public void setPreparazione(ArrayList<Step> preparazione) {
        this.preparazione = preparazione;
    }

    public void addStep(Step step) {
        this.preparazione.add(step);
    }

}
