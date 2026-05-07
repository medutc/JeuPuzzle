/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.jeupuzzle.modele;

public class SessionJeu {
    private Joueur joueur1;
    private Joueur joueur2;
    private Joueur joueurActuel;
    private boolean estTourJ1;
    private int nbToursTermines;

    public SessionJeu(String nom1, String nom2) {
        this.joueur1 = new Joueur(nom1);
        this.joueur2 = new Joueur(nom2);
        this.nbToursTermines = 0;
    }

    public void determinerPremierJoueur(boolean pileOuFace) {
        this.estTourJ1 = pileOuFace;
        this.joueurActuel = estTourJ1 ? joueur1 : joueur2;
    }

    public void changerJoueur() {
        estTourJ1 = !estTourJ1;
        joueurActuel = estTourJ1 ? joueur1 : joueur2;
    }

    public Joueur getJoueurActuel() { return joueurActuel; }
    public Joueur getJoueur1() { return joueur1; }
    public Joueur getJoueur2() { return joueur2; }
    
    public int getNbToursTermines() { return nbToursTermines; }
    public void incrementerToursTermines() { nbToursTermines++; }
}
