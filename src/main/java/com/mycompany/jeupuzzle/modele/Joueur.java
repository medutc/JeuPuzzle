/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.jeupuzzle.modele;

public class Joueur {
    private String nom;
    private int scoreFinal;

    public Joueur(String nom) {
        this.nom = nom;
        this.scoreFinal = 0;
    }

    public String getNom() { return nom; }
    public int getScoreFinal() { return scoreFinal; }
    public void setScoreFinal(int score) { this.scoreFinal = score; }
}
