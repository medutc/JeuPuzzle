/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.jeupuzzle.vue;

import javax.swing.*;
import java.awt.*;
import com.mycompany.jeupuzzle.modele.Grille;
import com.mycompany.jeupuzzle.controleur.ControleurJeu;
import java.awt.event.ActionListener;

/**
 * Vue affichant les boutons de la grille.
 */
public class PanneauGrille extends JPanel {
    
    private JButton[][] boutons;
    private int taille;

    public PanneauGrille(int taille, ControleurJeu controleur) {
        this.taille = taille;
        this.boutons = new JButton[taille][taille];
        
        setLayout(new GridLayout(taille, taille, 5, 5));

        for (int i = 0; i < taille; i++) {
            for (int j = 0; j < taille; j++) {
                JButton btn = new JButton();
                btn.setFont(new Font("Arial", Font.BOLD, 40));
                btn.setFocusPainted(false);
                btn.addActionListener((ActionListener) controleur);
                btn.setActionCommand(i + "," + j); // Stocke les coordonnées
                
                boutons[i][j] = btn;
                add(btn);
            }
        }
    }

    public void mettreAJour(Grille grille) {
        for (int i = 0; i < taille; i++) {
            for (int j = 0; j < taille; j++) {
                int valeur = grille.getValeur(i, j);
                if (valeur == 0) {
                    boutons[i][j].setText("");
                    boutons[i][j].setBackground(Color.LIGHT_GRAY);
                } else {
                    boutons[i][j].setText(String.valueOf(valeur));
                    boutons[i][j].setBackground(Color.WHITE);
                }
            }
        }
    }
}