/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.jeupuzzle.vue;

import javax.swing.*;
import java.awt.*;

/**
 * Fenêtre principale de l'application.
 */
public class FenetreJeu extends JFrame {
    
    public FenetreJeu() {
        setTitle("Jeu de Puzzle Glissant - Duel");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centre la fenêtre au milieu de l'écran
        setLayout(new BorderLayout());
    }

    /**
     * Affiche l'écran du Menu et efface le reste.
     */
    public void setPanneauMenu(PanneauMenu panneauMenu) {
        getContentPane().removeAll(); // 1. On vide complètement la fenêtre
        add(panneauMenu, BorderLayout.CENTER); // 2. On ajoute le menu
        revalidate(); // 3. On force Java à recalculer la structure
        repaint();    // 4. On nettoie l'écran des anciens pixels (évite la superposition)
    }

    /**
     * Affiche l'écran de la Grille et efface le reste.
     */
    public void setPanneauGrille(PanneauGrille panneauGrille) {
        getContentPane().removeAll(); // 1. On enlève le menu
        add(panneauGrille, BorderLayout.CENTER); // 2. On ajoute la grille
        revalidate(); // 3. Rafraîchissement
        repaint();    // 4. Nettoyage
    }
}