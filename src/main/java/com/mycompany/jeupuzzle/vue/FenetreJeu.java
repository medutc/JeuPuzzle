/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.jeupuzzle.vue;

import javax.swing.*;
import java.awt.*;

/**
 * Fenêtre principale (JFrame) contenant les panneaux.
 */
public class FenetreJeu extends JFrame { // La correction "extends JFrame" est ici !
    
    private PanneauGrille panneauGrille;

    public FenetreJeu() {
        setTitle("Jeu de Puzzle Glissant");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centre la fenêtre
        setLayout(new BorderLayout());
    }

    public void setPanneauGrille(PanneauGrille panneauGrille) {
        if (this.panneauGrille != null) {
            remove(this.panneauGrille);
        }
        this.panneauGrille = panneauGrille;
        add(this.panneauGrille, BorderLayout.CENTER);
        revalidate();
        repaint();
    }
    public void setPanneauMenu(PanneauMenu panneauMenu) {
        // Si la grille est affichée, on la retire
        if (this.panneauGrille != null) {
            remove(this.panneauGrille);
        }
        add(panneauMenu, BorderLayout.CENTER);
        revalidate();
        repaint();
    }
}