/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.jeupuzzle.modele;

import java.util.Random;

/**
 * Modèle gérant la logique du jeu de puzzle glissant.
 */
public class Grille {
    private int[][] plateauInitial;
    private int initVideX;
    private int initVideY;
    private int[][] plateau;
    private int taille;
    private int videX;
    private int videY;

    public Grille(int taille) {
        this.taille = taille;
        this.plateau = new int[taille][taille];
        initialiser();
    }

    public void initialiser() {
        int compteur = 1;
        for (int i = 0; i < taille; i++) {
            for (int j = 0; j < taille; j++) {
                if (i == taille - 1 && j == taille - 1) {
                    plateau[i][j] = 0; // 0 représente la case vide
                    videX = i;
                    videY = j;
                } else {
                    plateau[i][j] = compteur;
                    compteur++;
                }
            }
        }
    }

    public boolean deplacer(int x, int y) {
        // Vérifie si la case est adjacente à la case vide
        if ((Math.abs(videX - x) == 1 && videY == y) || 
            (Math.abs(videY - y) == 1 && videX == x)) {
            
            // Échange des valeurs
            plateau[videX][videY] = plateau[x][y];
            plateau[x][y] = 0;
            
            videX = x;
            videY = y;
            return true;
        }
        return false;
    }

    public void melanger(int nbMouvements) {
        Random rand = new Random();
        int mouvementsFaits = 0;
        int[] dx = {-1, 1, 0, 0};
        int[] dy = {0, 0, -1, 1};

        while (mouvementsFaits < nbMouvements) {
            int direction = rand.nextInt(4);
            int nx = videX + dx[direction];
            int ny = videY + dy[direction];

            if (nx >= 0 && nx < taille && ny >= 0 && ny < taille) {
                deplacer(nx, ny);
                mouvementsFaits++;
            }
        }
        plateauInitial = new int[taille][taille];
    for (int i = 0; i < taille; i++) {
        System.arraycopy(plateau[i], 0, plateauInitial[i], 0, taille);
    }
    initVideX = videX;
    initVideY = videY;
    }

    public boolean estResolu() {
        int compteur = 1;
        for (int i = 0; i < taille; i++) {
            for (int j = 0; j < taille; j++) {
                if (i == taille - 1 && j == taille - 1) {
                    return plateau[i][j] == 0;
                }
                if (plateau[i][j] != compteur) {
                    return false;
                }
                compteur++;
            }
        }
        return true;
    }
    public void rechargerPlateauInitial() {
    for (int i = 0; i < taille; i++) {
        System.arraycopy(plateauInitial[i], 0, plateau[i], 0, taille);
    }
    videX = initVideX;
    videY = initVideY;
}

    public int getTaille() { return taille; }
    public int getValeur(int x, int y) { return plateau[x][y]; }
}