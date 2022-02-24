package com.example.oishiweek_project;

public class Ingrediente {

    private String nome;
    private float quantita;
    private String unitaDiMisura;

    public Ingrediente(String nome, float quantita, String unitaDiMisura) {
        this.nome = nome;
        this.quantita = quantita;
        this.unitaDiMisura = unitaDiMisura;
    }

    public Ingrediente() {
        this.nome = "";
        this.quantita = 0;
        this.unitaDiMisura = "g";
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public float getQuantita() {
        return quantita;
    }

    public void setQuantita(float quantita) {
        this.quantita = quantita;
    }

    public String getUnitaDiMisura() {
        return unitaDiMisura;
    }

    public void setUnitaDiMisura(String unitaDiMisura) {
        this.unitaDiMisura = unitaDiMisura;
    }
}
